<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.estudio.model.Grupo" %>
<%@ page import="com.estudio.model.Usuario" %>
<%@ page import="com.estudio.dao.GrupoDAO" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuario == null) {
        usuario = (Usuario) session.getAttribute("usuario");
    }
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    GrupoDAO grupoDAO = new GrupoDAO();
    List<Grupo> listaGrupos = grupoDAO.listarTodos();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listado de Grupos Disponibles</title>
</head>
<body>
    <h2>Listado de Grupos de Estudio</h2>
    <p>Usuario conectado: <strong><%= usuario.getNombre() %> <%= usuario.getApellido() %></strong></p>

    <table border="1" cellpadding="6" cellspacing="0">
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Materia</th>
            <th>Horario</th>
            <th>Modalidad</th>
            <th>Cupo</th>
            <th>Acciones</th>
        </tr>
        <% if (listaGrupos != null && !listaGrupos.isEmpty()) {
            for (Grupo g : listaGrupos) {
                boolean pertenece = (g.getIdCreador() == usuario.getIdUsuario()) ||
                                    grupoDAO.esMiembro(g.getIdGrupo(), usuario.getIdUsuario());
        %>
        <tr>
         <tr>
                     <td><%= g.getIdGrupo() %></td>
                     <td><%= g.getNombre() %></td>
                     <td><%= g.getMateria() %></td>
                     <td><%= g.getHorarios() %></td>
                     <td><%= g.getModalidad() %></td>
                     <td><%= g.getCupoMaximo() %></td>
                     <td>
                <% if (pertenece) { %>
                    <a href="grupo-detalle?idGrupo=<%= g.getIdGrupo() %>">
                        <button type="button">Entrar al Grupo (Chat y Reuniones)</button>
                    </a>
                <% } else { %>
                    <form action="solicitudes" method="post" style="display:inline;">
                        <input type="hidden" name="idGrupo" value="<%= g.getIdGrupo() %>">
                        <input type="hidden" name="idUsuario" value="<%= usuario.getIdUsuario() %>">
                        <button type="submit">Solicitar Ingreso</button>
                    </form>
                <% } %>
            </td>
        </tr>
        <%  }
           } else { %>
        <tr>
            <td colspan="7">No hay grupos creados todavía. ¡Creá el primero desde el Dashboard!</td>
        </tr>
        <% } %>
    </table>

    <p><a href="dashboard.jsp">Volver al Dashboard</a></p>
</body>
</html>