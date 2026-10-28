<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.estudio.model.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>[TEST] Dashboard</title>
</head>
<body>
    <h2>Sesión Iniciada con Éxito</h2>
    <%
            String resultadoMatch = request.getParameter("resultadoMatch");
            if (resultadoMatch != null) {
                boolean esAprobada = resultadoMatch.toLowerCase().contains("aprobada");
                String colorTexto = esAprobada ? "#155724" : "#721c24";
                String colorFondo = esAprobada ? "#d4edda" : "#f8d7da";
                String colorBorde = esAprobada ? "#c3e6cb" : "#f5c6cb";
        %>
            <div style="background-color: <%= colorFondo %>; color: <%= colorTexto %>; border: 1px solid <%= colorBorde %>; padding: 12px; margin-bottom: 20px; border-radius: 4px;">
                <strong>Resultado del Matching (RF4):</strong> <%= resultadoMatch %>
            </div>
        <% } %>
    <p><strong>ID Usuario:</strong> <%= usuario.getIdUsuario() %></p>
    <p><strong>Nombre:</strong> <%= usuario.getNombre() %> <%= usuario.getApellido() %></p>
    <p><strong>Carrera:</strong> <%= usuario.getCarrera() %></p>
    <p><strong>Email:</strong> <%= usuario.getEmail() %></p>
    <p><strong>Pref. Comunicación:</strong> <%= usuario.getPrefComunicacion() %></p>
    <p><strong>Espacio Accesible:</strong> <%= usuario.isRequiereEspacioAccesible() %></p>
    <p><strong>Material Digital:</strong> <%= usuario.isPrefMaterialDigital() %></p>
    <hr>
<h3>Acciones</h3>
<p>
    <a href="crear-grupo.jsp"><button type="button">Crear Nuevo Grupo de Estudio</button></a>
    <p><a href="buscar-grupos.jsp">Buscar Grupos de Estudio</a></p>
</p>

    <p><a href="auth?accion=logout">Cerrar Sesión</a></p>
</body>
</html>