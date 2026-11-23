<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <h2>Registro de Usuario</h2>

    <% String error = (String) request.getAttribute("error"); %>
    <% if (error != null) { %>
        <p class="mensaje-error" role="alert"><%= error %></p>
    <% } %>

    <form action="auth" method="post">
        <input type="hidden" name="accion" value="registro">
        
        <p>
            <label>Nombre: <input type="text" name="nombre" required></label>
        </p>
        <p>
            <label>Apellido: <input type="text" name="apellido" required></label>
        </p>
        <p>
            <label>Carrera: <input type="text" name="carrera" required></label>
        </p>
        <p>
            <label>Email: <input type="email" name="email" required></label>
        </p>
        <p>
            <label>Contraseña: <input type="password" name="password" required></label>
        </p>

        <fieldset>
            <legend>Accesibilidad / Preferencias</legend>
            <p>
                <label>Preferencia de comunicación:
                    <select name="prefComunicacion">
                        <option value="Indistinto">Indistinto</option>
                        <option value="Escrita">Escrita</option>
                        <option value="Voz">Voz</option>
                    </select>
                </label>
            </p>
            <p>
                <label>
                    <input type="checkbox" name="requiereEspacioAccesible" value="true">
                    Requiere espacio accesible
                </label>
            </p>
            <p>
                <label>
                    <input type="checkbox" name="prefMaterialDigital" value="true">
                    Preferencia por material digital
                </label>
            </p>
        </fieldset>
        <br>
        <button type="submit">Registrar</button>
    </form>

    <p><a href="login.jsp">Ir al Login</a></p>
</body>
</html>