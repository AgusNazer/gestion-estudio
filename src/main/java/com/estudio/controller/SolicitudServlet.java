package com.estudio.controller;

import com.estudio.dao.SolicitudDAO;
import com.estudio.model.Solicitud;
import com.estudio.model.Usuario;
import com.estudio.service.MatchService;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/solicitudes")
public class SolicitudServlet extends HttpServlet {

    private final SolicitudDAO solicitudDAO = new SolicitudDAO();
    private final MatchService matchService = new MatchService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int idGrupo = Integer.parseInt(request.getParameter("idGrupo"));
            int idUsuario = usuarioLogueado.getIdUsuario();

            // Solicitud en estado "Pendiente"
            Solicitud nuevaSolicitud = new Solicitud(idGrupo, idUsuario, "Solicitud automática de ingreso");
            nuevaSolicitud.setEstado("Pendiente");

            //  captura su ID
            int idSolicitud = solicitudDAO.crearSolicitudYObtenerId(nuevaSolicitud);

            if (idSolicitud != -1) {
                // motor de Matching Automático
                String resultadoMatch = matchService.procesarSolicitud(idSolicitud);

                //dashboard mandando el texto del resultado
                String mensajeCodificado = java.net.URLEncoder.encode(resultadoMatch, "UTF-8");
                response.sendRedirect("dashboard.jsp?resultadoMatch=" + mensajeCodificado);
            } else {
                response.sendRedirect("buscar-grupos.jsp?error=No se pudo procesar la solicitud");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("buscar-grupos.jsp?error=Error interno en el servidor");
        }
    }
}