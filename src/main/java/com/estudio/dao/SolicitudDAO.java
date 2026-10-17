package com.estudio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.estudio.conexion.Conexion;
import com.estudio.model.Solicitud;

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
}
