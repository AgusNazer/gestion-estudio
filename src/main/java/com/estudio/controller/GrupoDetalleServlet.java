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
import java.util.List;

@WebServlet("/grupo-detalle")
public class GrupoDetalleServlet extends HttpServlet {

    private final MensajeService mensajeService = new MensajeService();
    private final ReunionService reunionService = new ReunionService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idGrupoParam = request.getParameter("idGrupo");
        int idGrupo = (idGrupoParam != null && !idGrupoParam.isEmpty()) ? Integer.parseInt(idGrupoParam) : 1;

        try {
            List<Reunion> reuniones = reunionService.listarReuniones(idGrupo);
            List<Mensaje> mensajes = mensajeService.listarMensajes(idGrupo);

            request.setAttribute("reuniones", reuniones);
            request.setAttribute("mensajes", mensajes);
            request.setAttribute("idGrupo", idGrupo);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }

        request.getRequestDispatcher("grupo-detalle.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        Usuario usuario = null;
        if (session != null) {
            usuario = (Usuario) session.getAttribute("usuarioLogueado");
            if (usuario == null) {
                usuario = (Usuario) session.getAttribute("usuario");
            }
        }

        int idUsuario = (usuario != null) ? usuario.getIdUsuario() : Integer.parseInt(request.getParameter("idUsuario"));
        int idGrupo = Integer.parseInt(request.getParameter("idGrupo"));
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
            }
        } catch (IllegalArgumentException e) {
            request.getSession().setAttribute("mensajeError", e.getMessage());
        } catch (Exception e) {
            request.getSession().setAttribute("mensajeError", "Ocurrió un error al procesar la solicitud.");
        }

        response.sendRedirect("grupo-detalle?idGrupo=" + idGrupo);
    }
}