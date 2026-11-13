package com.estudio.dao;

import com.estudio.conexion.Conexion;
import com.estudio.model.Mensaje;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MensajeDAO {

	public boolean crearMensaje(Mensaje m) {
		String sql = "INSERT INTO mensajes_chat (id_grupo, id_usuario, mensaje) VALUES (?, ?, ?)";

		try (Connection con = Conexion.getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, m.getIdGrupo());
			ps.setInt(2, m.getIdUsuario());
			ps.setString(3, m.getMensaje());

			int filasAfectadas = ps.executeUpdate();
			return filasAfectadas > 0;

		} catch (SQLException e) {
			System.err.println("Error al guardar el mensaje en la BD.");
			e.printStackTrace();
			return false;
		}
	}

	public List<Mensaje> listarPorGrupo(int idGrupo) {
		List<Mensaje> mensajes = new ArrayList<>();
		String sql = "SELECT m.*, u.nombre, u.apellido FROM mensajes_chat m " +
				"INNER JOIN usuarios u ON m.id_usuario = u.id_usuario " +
				"WHERE m.id_grupo = ? ORDER BY m.fecha_hora ASC";

		try (Connection con = Conexion.getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, idGrupo);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Mensaje m = new Mensaje();
					m.setIdMensaje(rs.getInt("id_mensaje"));
					m.setIdGrupo(rs.getInt("id_grupo"));
					m.setIdUsuario(rs.getInt("id_usuario"));
					m.setMensaje(rs.getString("mensaje"));
					m.setFechaHora(rs.getTimestamp("fecha_hora"));
					m.setNombreUsuario(rs.getString("nombre") + " " + rs.getString("apellido"));

					mensajes.add(m);
				}
			}
		} catch (SQLException e) {
			System.err.println("Error al listar los mensajes de la BD.");
			e.printStackTrace();
		}
		return mensajes;
	}
}