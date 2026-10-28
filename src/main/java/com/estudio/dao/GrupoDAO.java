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
    public Grupo obtenerPorId(int idGrupo) {
        Grupo grupo = null;
        String sql = "SELECT * FROM grupos WHERE id_grupo = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idGrupo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    grupo = new Grupo();
                    grupo.setNombre(rs.getString("nombre"));
                    grupo.setMateria(rs.getString("materia"));
                    grupo.setHorarios(rs.getString("horarios"));
                    grupo.setModalidad(rs.getString("modalidad"));
                    grupo.setCupoMaximo(rs.getInt("cupo_maximo"));
                    grupo.setObjetivo(rs.getString("objetivo"));
                    grupo.setEspacioAccesible(rs.getBoolean("espacio_accesible"));
                    // idCreador y estado
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el grupo por ID en la BD.");
            e.printStackTrace();
        }
        return grupo;
    }
    public int contarMiembros(int idGrupo) {
        int cantidad = 0;
        String sql = "SELECT COUNT(*) FROM miembros_grupo WHERE id_grupo = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idGrupo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cantidad = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al contar los miembros del grupo en la BD.");
            e.printStackTrace();
        }
        return cantidad;
    }
    public boolean agregarMiembro(int idGrupo, int idUsuario, String rol) {
        String sql = "INSERT INTO miembros_grupo (id_grupo, id_usuario) VALUES (?, ?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idGrupo);
            ps.setInt(2, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error SQL al insertar miembro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public boolean esMiembro(int idGrupo, int idUsuario) {
        String sql = "SELECT COUNT(*) FROM miembros_grupo WHERE id_grupo = ? AND id_usuario = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idGrupo);
            ps.setInt(2, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}