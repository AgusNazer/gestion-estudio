package com.estudio.dao;

import com.estudio.conexion.Conexion;
import com.estudio.model.Grupo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GrupoDAO {

    public boolean crearGrupo(Grupo g) {
        String sql = "INSERT INTO grupos (nombre, materia, horarios, modalidad, cupo_maximo, objetivo, espacio_accesible, id_creador, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, g.getNombre());
            ps.setString(2, g.getMateria());
            ps.setString(3, g.getHorarios());
            ps.setString(4, g.getModalidad());
            ps.setInt(5, g.getCupoMaximo());
            ps.setString(6, g.getObjetivo());
            ps.setBoolean(7, g.isEspacioAccesible());
            ps.setInt(8, g.getIdCreador());
            ps.setString(9, g.getEstado());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al guardar el grupo en la BD.");
            e.printStackTrace();
            return false;
        }
    }

    public boolean existeGrupoPorNombreYCreador(String nombre, int idCreador) {
        String sql = "SELECT COUNT(*) FROM grupos WHERE LOWER(TRIM(nombre)) = LOWER(TRIM(?)) AND id_creador = ? AND estado != 'Cerrado'";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setInt(2, idCreador);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al validar existencia de grupo en la BD: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}