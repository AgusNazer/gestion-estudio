<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.estudio.model.Reunion" %>
<%@ page import="com.estudio.model.Mensaje" %>
<%@ page import="com.estudio.model.Usuario" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuario == null) {
        usuario = (Usuario) session.getAttribute("usuario");
    }
    int idUsuarioActual = (usuario != null) ? usuario.getIdUsuario() : 1;
    int idGrupo = (request.getAttribute("idGrupo") != null) ? (Integer) request.getAttribute("idGrupo") : 1;
    List<Reunion> reuniones = (List<Reunion>) request.getAttribute("reuniones");
    List<Mensaje> mensajes = (List<Mensaje>) request.getAttribute("mensajes");
    List<Usuario> integrantes = (List<Usuario>) request.getAttribute("integrantes");
    
    java.time.LocalDate hoy = java.time.LocalDate.now();
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    SimpleDateFormat formatoDia = new SimpleDateFormat("dd/MM/yyyy");
    List<Reunion> proximas = new ArrayList<>();
    List<Reunion> realizadas = new ArrayList<>();
    if (reuniones != null) {
        for (Reunion r : reuniones) {
            if (r.getFecha().toLocalDate().isAfter(hoy)) {
                proximas.add(r);
            } else {
                realizadas.add(r);
            }
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Grupo de Estudio #<%= idGrupo %></title>
</head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
<body>
    <h2>Espacio del Grupo #<%= idGrupo %></h2>

    <% if (session.getAttribute("mensajeExito") != null) { %>
        <div style="background-color: #d4edda; color: #155724; padding: 10px; margin-bottom: 15px; border: 1px solid #c3e6cb;">
            <strong>Éxito:</strong> <%= session.getAttribute("mensajeExito") %>
        </div>
        <% session.removeAttribute("mensajeExito"); %>
    <% } %>

    <% if (session.getAttribute("mensajeError") != null) { %>
        <div style="background-color: #f8d7da; color: #721c24; padding: 10px; margin-bottom: 15px; border: 1px solid #f5c6cb;">
            <strong>Error:</strong> <%= session.getAttribute("mensajeError") %>
        </div>
        <% session.removeAttribute("mensajeError"); %>
    <% } %>

    <hr>
        <h3>Próximas reuniones</h3>
    <table border="1" cellpadding="5" cellspacing="0">
        <tr>
            <th>ID</th>
            <th>Fecha</th>
            <th>Temas a tratar</th>
        </tr>
        <% if (!proximas.isEmpty()) {
            for (Reunion r : proximas) { %>
            <tr>
                <td><%= r.getIdReunion() %></td>
                <td><%= formatoDia.format(r.getFecha()) %></td>
                <td><%= r.getTemasTratados() %></td>
            </tr>
        <%  }
           } else { %>
            <tr>
                <td colspan="3">No hay reuniones próximas agendadas.</td>
            </tr>
        <% } %>
    </table>

    <h3>Reuniones realizadas</h3>
    <table border="1" cellpadding="5" cellspacing="0">
        <tr>
            <th>ID</th>
            <th>Fecha</th>
            <th>Temas tratados</th>
            <th>Asistentes</th>
        </tr>
        <% if (!realizadas.isEmpty()) {
            for (Reunion r : realizadas) { %>
            <tr>
                <td><%= r.getIdReunion() %></td>
                <td><%= formatoDia.format(r.getFecha()) %></td>
                <td><%= r.getTemasTratados() %></td>
                <td><%= (r.getAsistentes() != null) ? r.getAsistentes() : "Sin asistencia registrada" %></td>
            </tr>
        <%  }
           } else { %>
            <tr>
                <td colspan="4">Todavía no hay reuniones realizadas registradas.</td>
            </tr>
        <% } %>
    </table>

    <h4>Agendar Nueva Reunión</h4>
    <form action="grupo-detalle" method="post">
        <input type="hidden" name="accion" value="reunion">
        <input type="hidden" name="idGrupo" value="<%= idGrupo %>">
        <input type="hidden" name="idUsuario" value="<%= idUsuarioActual %>">
        <label>Fecha:</label>
        <input type="date" name="fecha" required>
        <label>Temas:</label>
        <input type="text" name="temasTratados" placeholder="Ej: Repaso general" required>
        <button type="submit">Guardar Reunión</button>
    </form>
    
        <h4>Registrar Reunión Realizada</h4>
    <form action="grupo-detalle" method="post">
        <input type="hidden" name="accion" value="reunionRealizada">
        <input type="hidden" name="idGrupo" value="<%= idGrupo %>">
        <input type="hidden" name="idUsuario" value="<%= idUsuarioActual %>">
        <label for="fechaRealizada">Fecha:</label>
        <input type="date" id="fechaRealizada" name="fecha" max="<%= java.time.LocalDate.now() %>" required>
        <label for="temasRealizada">Temas tratados:</label>
        <input type="text" id="temasRealizada" name="temasTratados" placeholder="Ej: Repaso de listas" required>
        <fieldset>
            <legend>Participantes presentes:</legend>
            <% if (integrantes != null) {
                for (Usuario u : integrantes) { %>
                <label>
                    <input type="checkbox" name="asistentes" value="<%= u.getIdUsuario() %>">
                    <%= u.getNombre() %> <%= u.getApellido() %>
                </label><br>
            <%  }
               } %>
        </fieldset>
        <button type="submit">Registrar Reunión</button>
    </form>

    <hr>
    <h3>Chat del Grupo (RF5)</h3>
    <div style="border: 1px solid #999; padding: 10px; width: 500px; height: 200px; overflow-y: scroll; margin-bottom: 10px;">
        <% if (mensajes != null && !mensajes.isEmpty()) {
            for (Mensaje m : mensajes) { %>
                <p style="margin: 4px 0;">
                <strong><%= m.getNombreUsuario() %></strong>
                <small>[<%= formatoFecha.format(m.getFechaHora()) %>]</small>:
                <%= m.getMensaje() %>
            </p>
        <%  }
           } else { %>
            <p>Todavía no hay mensajes en este grupo.</p>
        <% } %>
    </div>

    <form action="grupo-detalle" method="post">
        <input type="hidden" name="accion" value="mensaje">
        <input type="hidden" name="idGrupo" value="<%= idGrupo %>">
        <input type="hidden" name="idUsuario" value="<%= idUsuarioActual %>">
        <input type="text" name="mensaje" placeholder="Escribir un mensaje..." style="width: 380px;" required>
        <button type="submit">Enviar</button>
    </form>

    <hr>
    <p><a href="dashboard.jsp">Volver al Dashboard</a></p>
</body>
</html>