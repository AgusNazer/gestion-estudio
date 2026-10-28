package com.estudio.service;

import com.estudio.dao.GrupoDAO;
import com.estudio.dao.SolicitudDAO;
import com.estudio.dao.UsuarioDAO;
import com.estudio.model.Grupo;
import com.estudio.model.Solicitud;
import com.estudio.model.Usuario;

public class MatchService {

    private final SolicitudDAO solicitudDAO = new SolicitudDAO();
    private final GrupoDAO grupoDAO = new GrupoDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public String procesarSolicitud(int idSolicitud) {
        //Instanciar los objetos
        Solicitud solicitud = solicitudDAO.obtenerPorId(idSolicitud);
        Grupo grupo = grupoDAO.obtenerPorId(solicitud.getIdGrupo());
        Usuario usuario = usuarioDAO.obtenerPorId(solicitud.getIdUsuario());

        // Validar que no sea el creador ni sea ya un miembro del grupo
        if (grupo.getIdCreador() == usuario.getIdUsuario() || grupoDAO.esMiembro(solicitud.getIdGrupo(), usuario.getIdUsuario())) {
            solicitud.setEstado("Rechazada");
            solicitudDAO.actualizarEstado(solicitud);
            return "Solicitud rechazada: ya formas parte o eres el creador de este grupo.";
        }

        int miembrosActuales = grupoDAO.contarMiembros(solicitud.getIdGrupo());
        if (miembrosActuales >= grupo.getCupoMaximo()) {
            solicitud.setEstado("Rechazada");
            solicitudDAO.actualizarEstado(solicitud);
            return "El grupo no cuenta con vacantes disponibles.";
        }

        // Si el usuario requiere accesibilidad, el grupo DEBE tenerla. Si no la requiere, da igual (true).
        boolean compatibilidadAccesibilidad = !usuario.isRequiereEspacioAccesible() || grupo.isEspacioAccesible();

        // TODO: Agregar validación de horarios (ej. cruce de Strings de días) y modalidad
        boolean esCompatible = compatibilidadAccesibilidad;

        if (!esCompatible) {
            solicitud.setEstado("Rechazada");
            solicitudDAO.actualizarEstado(solicitud);
            return "Solicitud desestimada: no cumple con los criterios de compatibilidad del grupo.";
        }

        //Aprobación Final
        solicitud.setEstado("Aprobada");
        solicitudDAO.actualizarEstado(solicitud);

        // Insertar usuario
        grupoDAO.agregarMiembro(solicitud.getIdGrupo(), solicitud.getIdUsuario(), "Miembro");

        return "Solicitud aprobada exitosamente.";
    }
}