<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("usuarioLogueado") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<html>
<head>
    <title>Buscar Grupos de Estudio</title>
</head>
<body>
    <h2>[PROVISORIO] Listado de Grupos Disponibles</h2>

    <p>Acá el estudiante puede visualizar los grupos creados y enviar su solicitud para aplicar el sistema de matching.</p>

    <!-- Tabla o listado de ejemplo -->
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
            <tr>
                <th>ID Grupo</th>
                <th>Nombre</th>
                <th>Materia</th>
                <th>Modalidad</th>
                <th>Cupo</th>
                <th>Acción</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>2</td>
                <td>Grupo prog 3</td>
                <td>Programacion 3</td>
                <td>Híbrida</td>
                <td>6</td>
                <td>
                    <form action="solicitudes" method="POST">
                        <input type="hidden" name="idGrupo" value="2">
                        <button type="submit">Solicitar Ingreso</button>
                    </form>
                </td>
            </tr>
        </tbody>
    </table>

    <br>
    <p><a href="dashboard.jsp">Volver al Dashboard</a></p>
</body>
</html>