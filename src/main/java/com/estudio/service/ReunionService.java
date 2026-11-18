package com.estudio.service;

import com.estudio.dao.ReunionDAO;
import com.estudio.model.Reunion;
import com.estudio.dao.GrupoDAO;
import com.estudio.model.Usuario;
import java.util.ArrayList;

import java.sql.Date;
import java.util.List;

public class ReunionService {

    private final ReunionDAO reunionDAO = new ReunionDAO();
    private final GrupoDAO grupoDAO = new GrupoDAO();

    public boolean crearReunion(int idGrupo, Date fecha, String temasTratados) {

        if (idGrupo <= 0) {
            throw new IllegalArgumentException("El grupo no es válido");
        }

        if (fecha == null) {
            throw new IllegalArgumentException("La fecha es obligatoria");
        }

        if (temasTratados == null || temasTratados.trim().isEmpty()) {
            throw new IllegalArgumentException("Los temas de la reunión son obligatorios");
        }

        if (temasTratados.trim().length() > 255) {
            throw new IllegalArgumentException("Los temas no pueden superar los 255 caracteres");
        }

        if (fecha == null) {
            throw new IllegalArgumentException("La fecha es obligatoria");
        }
        if (fecha.toLocalDate().isBefore(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("No se puede agendar una reunión en una fecha pasada");
        }

        Reunion reunion = new Reunion(idGrupo, fecha, temasTratados.trim());

        return reunionDAO.crear(reunion);

    }



    public List<Reunion> listarReuniones(int idGrupo) {

        if (idGrupo <= 0) {
            throw new IllegalArgumentException("El grupo no es válido");
        }

        return reunionDAO.listarPorGrupo(idGrupo);
    }
    
    // RF6: registrar una reunión realizada, con sus asistentes
    public boolean registrarReunionRealizada(int idGrupo, Date fecha, String temasTratados, List<Integer> idsAsistentes) {

        if (idGrupo <= 0) {
            throw new IllegalArgumentException("El grupo no es válido");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha es obligatoria");
        }
        if (fecha.toLocalDate().isAfter(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("No se pueden registrar reuniones con fecha posterior a la actual.");
        }
        if (temasTratados == null || temasTratados.trim().isEmpty()) {
            throw new IllegalArgumentException("Los temas de la reunión son obligatorios");
        }
        if (temasTratados.trim().length() > 255) {
            throw new IllegalArgumentException("Los temas no pueden superar los 255 caracteres");
        }
        if (idsAsistentes == null || idsAsistentes.isEmpty()) {
            throw new IllegalArgumentException("Debe seleccionar al menos un participante presente.");
        }

        // Los asistentes tienen que ser integrantes del grupo
        List<Integer> idsIntegrantes = new ArrayList<>();
        for (Usuario u : grupoDAO.listarIntegrantes(idGrupo)) {
            idsIntegrantes.add(u.getIdUsuario());
        }
        for (int idUsuario : idsAsistentes) {
            if (!idsIntegrantes.contains(idUsuario)) {
                throw new IllegalArgumentException("Hay asistentes que no pertenecen al grupo");
            }
        }

        Reunion reunion = new Reunion(idGrupo, fecha, temasTratados.trim());
        return reunionDAO.crearConAsistentes(reunion, idsAsistentes);
    }
    
    public List<Usuario> listarIntegrantes(int idGrupo) {
        if (idGrupo <= 0) {
            throw new IllegalArgumentException("El grupo no es válido");
        }
        return grupoDAO.listarIntegrantes(idGrupo);
    }
}