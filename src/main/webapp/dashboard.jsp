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
    <title>Panel principal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <h2>Sesión Iniciada con Éxito</h2>
    <%
        String resultadoMatch = request.getParameter("resultadoMatch");
        if (resultadoMatch != null) {
            boolean esAprobada = resultadoMatch.toLowerCase().contains("aprobada");
    %>
        <div class="<%= esAprobada ? "mensaje-exito" : "mensaje-error" %>" role="<%= esAprobada ? "status" : "alert" %>">
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
    <h3>Mis Grupos de Estudio (Chat y Reuniones)</h3>
        <ul>
        <%
            com.estudio.dao.GrupoDAO gDao = new com.estudio.dao.GrupoDAO();
            java.util.List<com.estudio.model.Grupo> todos = gDao.listarTodos();
            boolean tieneGrupos = false;
            for (com.estudio.model.Grupo g : todos) {
                if (g.getIdCreador() == usuario.getIdUsuario() || gDao.esMiembro(g.getIdGrupo(), usuario.getIdUsuario())) {
                    tieneGrupos = true;
        %>
                    <li>
                        <strong><%= g.getNombre() %></strong> (<%= g.getMateria() %>) -
                        <a href="grupo-detalle?idGrupo=<%= g.getIdGrupo() %>">Ir al Chat y Reuniones</a>
                    </li>
        <%
                }
            }
            if (!tieneGrupos) {
        %>
                <li>Todavía no estás inscripto en ningún grupo.</li>
        <%  } %>
        </ul>
</p>

    <p><a href="auth?accion=logout">Cerrar Sesión</a></p>

</body>
</html>