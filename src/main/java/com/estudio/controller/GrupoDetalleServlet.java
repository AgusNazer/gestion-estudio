package com.estudio.controller;

import com.estudio.model.Mensaje;
import com.estudio.model.Reunion;
import com.estudio.model.Usuario;
import com.estudio.service.MensajeService;
import com.estudio.service.ReunionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/grupo-detalle")
public class GrupoDetalleServlet extends HttpServlet {

    private final MensajeService mensajeService = new MensajeService();
    private final ReunionService reunionService = new ReunionService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuario = obtenerUsuarioLogueado(request);
        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int idGrupo;
        try {
            idGrupo = Integer.parseInt(request.getParameter("idGrupo"));
        } catch (NumberFormatException e) {
            response.sendRedirect("dashboard.jsp");
            return;
        }

        if (!esIntegrante(idGrupo, usuario.getIdUsuario())) {
            response.sendRedirect("dashboard.jsp");
            return;
        }

        try {
            List<Reunion> reuniones = reunionService.listarReuniones(idGrupo);
            List<Mensaje> mensajes = mensajeService.listarMensajes(idGrupo);

            request.setAttribute("reuniones", reuniones);
            request.setAttribute("mensajes", mensajes);
            request.setAttribute("integrantes", reunionService.listarIntegrantes(idGrupo));
            request.setAttribute("idGrupo", idGrupo);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }

        request.getRequestDispatcher("grupo-detalle.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Usuario usuario = obtenerUsuarioLogueado(request);
        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int idUsuario = usuario.getIdUsuario();
        int idGrupo;
        try {
            idGrupo = Integer.parseInt(request.getParameter("idGrupo"));
        } catch (NumberFormatException e) {
            response.sendRedirect("dashboard.jsp");
            return;
        }

        if (!esIntegrante(idGrupo, idUsuario)) {
            response.sendRedirect("dashboard.jsp");
            return;
        }

        String accion = request.getParameter("accion");

        try {
            if ("mensaje".equals(accion)) {
                String texto = request.getParameter("mensaje");
                Mensaje nuevoMensaje = new Mensaje(idGrupo, idUsuario, texto);
                mensajeService.crearMensaje(nuevoMensaje);
                request.getSession().setAttribute("mensajeExito", "Mensaje enviado correctamente.");
            } else if ("reunion".equals(accion)) {
                Date fecha = Date.valueOf(request.getParameter("fecha"));
                String temas = request.getParameter("temasTratados");
                reunionService.crearReunion(idGrupo, fecha, temas);
                request.getSession().setAttribute("mensajeExito", "Reunión registrada exitosamente.");
            } else if ("reunionRealizada".equals(accion)) {
                Date fecha = Date.valueOf(request.getParameter("fecha"));
                String temas = request.getParameter("temasTratados");
                List<Integer> asistentes = new ArrayList<>();
                String[] idsAsistentes = request.getParameterValues("asistentes");
                if (idsAsistentes != null) {
                    for (String id : idsAsistentes) {
                        asistentes.add(Integer.parseInt(id));
                    }
                }
                reunionService.registrarReunionRealizada(idGrupo, fecha, temas, asistentes);
                request.getSession().setAttribute("mensajeExito", "Reunión realizada registrada exitosamente.");
            }
        } catch (IllegalArgumentException e) {
            request.getSession().setAttribute("mensajeError", e.getMessage());
        } catch (Exception e) {
            request.getSession().setAttribute("mensajeError", "Ocurrió un error al procesar la solicitud.");
        }

        response.sendRedirect("grupo-detalle?idGrupo=" + idGrupo);
    }

    // Usuario que inició sesión, o null si no hay sesión
    private Usuario obtenerUsuarioLogueado(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            usuario = (Usuario) session.getAttribute("usuario");
        }
        return usuario;
    }

    // El creador y los miembros aceptados son integrantes del grupo
    private boolean esIntegrante(int idGrupo, int idUsuario) {
        if (idGrupo <= 0) {
            return false;
        }
        for (Usuario u : reunionService.listarIntegrantes(idGrupo)) {
            if (u.getIdUsuario() == idUsuario) {
                return true;
            }
        }
        return false;
    }
}