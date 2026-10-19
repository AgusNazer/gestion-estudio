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
    <p><strong>ID Usuario:</strong> <%= usuario.getIdUsuario() %></p>
    <p><strong>Nombre:</strong> <%= usuario.getNombre() %> <%= usuario.getApellido() %></p>
    <p><strong>Carrera:</strong> <%= usuario.getCarrera() %></p>
    <p><strong>Email:</strong> <%= usuario.getEmail() %></p>
    <p><strong>Pref. Comunicación:</strong> <%= usuario.getPrefComunicacion() %></p>
    <p><strong>Espacio Accesible:</strong> <%= usuario.isRequiereEspacioAccesible() %></p>
    <p><strong>Material Digital:</strong> <%= usuario.isPrefMaterialDigital() %></p>

    <p><a href="auth?accion=logout">Cerrar Sesión</a></p>
</body>
</html>