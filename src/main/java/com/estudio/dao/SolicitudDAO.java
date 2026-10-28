package com.estudio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.estudio.conexion.Conexion;
import com.estudio.model.Solicitud;
import java.sql.ResultSet;


public class SolicitudDAO {
		
	public boolean crearSolicitud(Solicitud s) {
		String sql = "INSERT INTO solicitudes (id_grupo, id_usuario, mensaje_presentacion, estado) VALUES (?, ?, ?, ?)";
		
		try (Connection con = Conexion.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {
			
			ps.setInt(1, s.getIdGrupo());
			ps.setInt(2, s.getIdUsuario());
			ps.setString(3, s.getMensajePresentacion());
			ps.setString(4, s.getEstado());
			
			int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0; // Si insertó al menos 1 fila, devuelve true			
		}		
		
		catch (SQLException e) {
            System.err.println("Error al guardar la solicitud en la BD.");
            e.printStackTrace();
            return false;
        }
	}

	public Solicitud obtenerPorId(int idSolicitud) {
		Solicitud solicitud = null;
		String sql = "SELECT * FROM solicitudes WHERE id_solicitud = ?";

		try (Connection con = Conexion.getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, idSolicitud);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					solicitud = new Solicitud();
					solicitud.setIdSolicitud(rs.getInt("id_solicitud"));
					solicitud.setIdGrupo(rs.getInt("id_grupo"));
					solicitud.setIdUsuario(rs.getInt("id_usuario"));
					solicitud.setMensajePresentacion(rs.getString("mensaje_presentacion"));
					solicitud.setEstado(rs.getString("estado"));
					solicitud.setFechaSolicitud(rs.getTimestamp("fecha_solicitud"));
				}
			}
		} catch (SQLException e) {
			System.err.println("Error al obtener la solicitud por ID en la BD.");
			e.printStackTrace();
		}
		return solicitud;
	}

	public boolean actualizarEstado(Solicitud solicitud) {
		String sql = "UPDATE solicitudes SET estado = ? WHERE id_solicitud = ?";

		try (Connection con = Conexion.getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, solicitud.getEstado());
			ps.setInt(2, solicitud.getIdSolicitud());

			int filasAfectadas = ps.executeUpdate();
			return filasAfectadas > 0;

		} catch (SQLException e) {
			System.err.println("Error al actualizar el estado de la solicitud en la BD.");
			e.printStackTrace();
			return false;
		}
	}
	public int crearSolicitudYObtenerId(Solicitud s) {
		String sql = "INSERT INTO solicitudes (id_grupo, id_usuario, mensaje_presentacion, estado) VALUES (?, ?, ?, ?)";
		int idGenerado = -1;

		// ID autoincremental de MySQL
		try (Connection con = Conexion.getConnection();
			 PreparedStatement ps = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

			ps.setInt(1, s.getIdGrupo());
			ps.setInt(2, s.getIdUsuario());
			ps.setString(3, s.getMensajePresentacion());
			ps.setString(4, s.getEstado());

			int filasAfectadas = ps.executeUpdate();
			if (filasAfectadas > 0) {
				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next()) {
						idGenerado = rs.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("Error al guardar la solicitud y obtener ID en la BD.");
			e.printStackTrace();
		}
		return idGenerado;
	}
}
