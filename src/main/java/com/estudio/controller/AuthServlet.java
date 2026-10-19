package com.estudio.controller;

import com.estudio.model.Usuario;
import com.estudio.service.UsuarioService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*
 * TODO: MIGRACION A REACT / API REST
 * -----------------------------------------------------------------------------------------
 * Actualmente este servlet funciona bajo arquitectura MVC tradicional (JSP + Form UrlEncoded):
 *  - Lee parámetros vía request.getParameter() desde formularios HTML.
 *  - Responde con request.getRequestDispatcher().forward() y response.sendRedirect().
 *
 * Pasos a adaptar cuando se integre el frontend en React:
 *  1. Entrada de datos:
 *     - Leer el payload JSON del cuerpo de la petición (request.getReader()) usando una
 *       librería como Gson o Jackson en vez de request.getParameter().
 *  2. Salida y respuestas:
 *     - Configurar response.setContentType("application/json") y response.setCharacterEncoding("UTF-8").
 *     - Retornar códigos de estado HTTP (200 OK, 400 Bad Request, 401 Unauthorized).
 *     - Serializar los objetos o mensajes de error a JSON (out.print(jsonResponse)).
 *  3. CORS (Cross-Origin Resource Sharing):
 *     - Habilitar cabeceras CORS (Access-Control-Allow-Origin, Headers, Methods) si React
 *       corre en un puerto/dominio distinto (ej. Vite en localhost:5173).
 * -----------------------------------------------------------------------------------------
 */

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioService usuarioService;

    @Override
    public void init() {
        this.usuarioService = new UsuarioService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) accion = "";

        if ("logout".equalsIgnoreCase(accion)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect("login.jsp");
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");

        if ("login".equalsIgnoreCase(accion)) {
            procesarLogin(request, response);
        } else if ("registro".equalsIgnoreCase(accion)) {
            procesarRegistro(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    private void procesarLogin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Usuario usuario = usuarioService.login(email, password);
            
            if (usuario != null) {
                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogueado", usuario);
                response.sendRedirect("dashboard.jsp");
            } else {
                request.setAttribute("error", "Usuario o contraseña incorrectos.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private void procesarRegistro(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String carrera = request.getParameter("carrera");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String prefComunicacion = request.getParameter("prefComunicacion");
        boolean requiereEspacio = request.getParameter("requiereEspacioAccesible") != null;
        boolean prefMaterial = request.getParameter("prefMaterialDigital") != null;

        Usuario nuevo = new Usuario(
            nombre,
            apellido,
            email,
            password,
            carrera,
            prefComunicacion,
            requiereEspacio,
            prefMaterial
        );

        try {
            boolean registrado = usuarioService.registrar(nuevo);
            if (registrado) {
                response.sendRedirect("login.jsp?registro=ok");
            } else {
                request.setAttribute("error", "Ocurrió un error al registrar el usuario en el sistema.");
                request.getRequestDispatcher("registro.jsp").forward(request, response);
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("registro.jsp").forward(request, response);
        }
    }
}