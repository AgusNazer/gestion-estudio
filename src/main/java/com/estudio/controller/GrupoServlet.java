package com.estudio.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estudio.model.Grupo;
import com.estudio.model.Usuario;
import com.estudio.service.GrupoService;

@WebServlet("/grupos")
public class GrupoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private GrupoService grupoService;

    @Override
    public void init() {
        this.grupoService = new GrupoService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirige al formulario de creación si acceden por GET
        response.sendRedirect("crear-grupo.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        // 1. Verificar sesion activa
        HttpSession session = request.getSession(false);
        Usuario usuarioLogueado = (session != null) ? (Usuario) session.getAttribute("usuarioLogueado") : null;
        
        if (usuarioLogueado == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 2. Obtener parmetros del formulario
        String accion = request.getParameter("accion");
        
        if ("crear".equalsIgnoreCase(accion)) {
            procesarCreacion(request, response, usuarioLogueado);
        } else {
            response.sendRedirect("dashboard.jsp");
        }
    }

    private void procesarCreacion(HttpServletRequest request, HttpServletResponse response, Usuario usuarioLogueado) 
            throws ServletException, IOException {
        try {
            String nombre = request.getParameter("nombre");
            String materia = request.getParameter("materia");
            String horarios = request.getParameter("horarios");
            String modalidad = request.getParameter("modalidad");
            int cupoMaximo = Integer.parseInt(request.getParameter("cupoMaximo"));
            String objetivo = request.getParameter("objetivo");
            boolean espacioAccesible = request.getParameter("espacioAccesible") != null;

            // Construir el objeto con los datos y el ID del usuario en sesion
            Grupo grupo = new Grupo(nombre, materia, horarios, modalidad, cupoMaximo, objetivo, espacioAccesible, usuarioLogueado.getIdUsuario());

            boolean creado = grupoService.crearGrupo(grupo);

            if (creado) {
                request.setAttribute("mensajeExito", "¡Grupo creado con éxito!");
                request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "No se pudo registrar el grupo en la base de datos.");
                request.getRequestDispatcher("crear-grupo.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "El cupo debe ser un valor numérico válido.");
            request.getRequestDispatcher("crear-grupo.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("crear-grupo.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error inesperado al crear el grupo.");
            request.getRequestDispatcher("crear-grupo.jsp").forward(request, response);
        }
    }
}