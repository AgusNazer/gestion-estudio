<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Iniciar sesión</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <h2>Iniciar Sesión</h2>

    <% String error = (String) request.getAttribute("error"); %>
    <% if (error != null) { %>
        <p class="mensaje-error" role="alert"><%= error %></p>
    <% } %>

    <% if (request.getParameter("registro") != null) { %>
        <p class="mensaje-exito" role="status">Registro exitoso. Ahora podés iniciar sesión.</p>
    <% } %>

    <form action="auth" method="post">
        <input type="hidden" name="accion" value="login">
        <p>
            <label>Email: <input type="email" name="email" required></label>
        </p>
        <p>
            <label>Contraseña: <input type="password" name="password" required></label>
        </p>
        <button type="submit">Ingresar</button>
    </form>

    <p><a href="registro.jsp">Ir al Registro</a></p>
</body>
</html>