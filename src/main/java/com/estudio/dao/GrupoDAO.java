package com.estudio.dao;

import com.estudio.conexion.Conexion;
import com.estudio.model.Grupo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                    grupo.setIdGrupo(rs.getInt("id_grupo"));
                    grupo.setNombre(rs.getString("nombre"));
                    grupo.setMateria(rs.getString("materia"));
                    grupo.setHorarios(rs.getString("horarios"));
                    grupo.setModalidad(rs.getString("modalidad"));
                    grupo.setCupoMaximo(rs.getInt("cupo_maximo"));
                    grupo.setObjetivo(rs.getString("objetivo"));
                    grupo.setEspacioAccesible(rs.getBoolean("espacio_accesible"));
                    grupo.setIdCreador(rs.getInt("id_creador"));
                    grupo.setEstado(rs.getString("estado"));
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

    public List<Grupo> listarTodos() {
        List<Grupo> grupos = new ArrayList<>();
        String sql = "SELECT * FROM grupos";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Grupo g = new Grupo();
                g.setIdGrupo(rs.getInt("id_grupo"));
                g.setNombre(rs.getString("nombre"));
                g.setMateria(rs.getString("materia"));
                g.setHorarios(rs.getString("horarios"));
                g.setModalidad(rs.getString("modalidad"));
                g.setCupoMaximo(rs.getInt("cupo_maximo"));
                g.setObjetivo(rs.getString("objetivo"));
                g.setEspacioAccesible(rs.getBoolean("espacio_accesible"));
                g.setIdCreador(rs.getInt("id_creador"));
                g.setEstado(rs.getString("estado"));
                grupos.add(g);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los grupos de la BD.");
            e.printStackTrace();
        }
        return grupos;
    }
}