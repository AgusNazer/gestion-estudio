package com.estudio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.estudio.conexion.Conexion;
import com.estudio.model.Mensaje;

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
}