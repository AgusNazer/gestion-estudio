<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.estudio.model.Usuario" %>
<%
    // Verificación de sesión: si no está logueado, vuelve al login
    Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuarioLogueado == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Crear Grupo de Estudio - RF2</title>
</head>
<body>

    <h2>[PROVISORIO] Crear Grupo de Estudio</h2>

    <p>Creador: <strong><%= usuarioLogueado.getNombre() %> <%= usuarioLogueado.getApellido() %></strong></p>

    <!-- Visualización de errores devueltos por el Servlet -->
    <% 
        String error = (String) request.getAttribute("error");
        if (error != null) { 
    %>
        <p style="color: red;"><strong>Error:</strong> <%= error %></p>
    <% } %>

    <form action="grupos" method="post">
        <!-- Campo oculto para que el servlet identifique la acción -->
        <input type="hidden" name="accion" value="crear">

        <div>
            <label for="nombre">Nombre del grupo:</label><br>
            <input type="text" id="nombre" name="nombre" placeholder="Ej: Grupo Prog 2 - Turno Noche" required>
        </div>
        <br>

        <div>
            <label for="materia">Materia:</label><br>
            <input type="text" id="materia" name="materia" placeholder="Ej: Programación II" required>
        </div>
        <br>

        <div>
            <label for="horarios">Días y Horarios disponibles:</label><br>
            <input type="text" id="horarios" name="horarios" placeholder="Ej: Martes y Jueves 18 a 20 hs" required>
        </div>
        <br>

        <div>
            <label for="modalidad">Modalidad:</label><br>
            <select id="modalidad" name="modalidad">
                <option value="Presencial">Presencial</option>
                <option value="Virtual">Virtual</option>
                <option value="Hibrida">Híbrida</option>
            </select>
        </div>
        <br>

        <div>
            <label for="cupoMaximo">Cupo Máximo de integrantes:</label><br>
            <input type="number" id="cupoMaximo" name="cupoMaximo" min="2" max="10" value="4" required>
        </div>
        <br>

        <div>
            <label for="objetivo">Objetivo del grupo:</label><br>
            <textarea id="objetivo" name="objetivo" rows="3" cols="30" placeholder="Ej: Preparar TP integrador y finales"></textarea>
        </div>
        <br>

        <div>
            <label>
                <input type="checkbox" name="espacioAccesible" value="true">
                Requiere / Garantiza espacio accesible
            </label>
        </div>
        <br>

        <button type="submit">Crear Grupo</button>
        <a href="dashboard.jsp">Volver al Dashboard</a>
    </form>

</body>
</html>