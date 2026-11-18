package com.estudio.dao;

import com.estudio.conexion.Conexion;
import com.estudio.model.Reunion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

public class ReunionDAO {

    // Crear una reunión
    public boolean crear(Reunion reunion) {

        String sql = "INSERT INTO reuniones (id_grupo, fecha, temas_tratados) "
                   + "VALUES (?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, reunion.getIdGrupo());
            ps.setDate(2, reunion.getFecha());
            ps.setString(3, reunion.getTemasTratados());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Listar reuniones de un grupo
    public List<Reunion> listarPorGrupo(int idGrupo) {

        List<Reunion> reuniones = new ArrayList<>();

        String sql = "SELECT r.*, "
                + "(SELECT GROUP_CONCAT(CONCAT(u.nombre, ' ', u.apellido) ORDER BY u.nombre SEPARATOR ', ') "
                + "FROM asistencias_reunion a INNER JOIN usuarios u ON a.id_usuario = u.id_usuario "
                + "WHERE a.id_reunion = r.id_reunion) AS asistentes "
                + "FROM reuniones r WHERE r.id_grupo = ? ORDER BY r.fecha";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idGrupo);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Reunion reunion = new Reunion();

                    reunion.setIdReunion(rs.getInt("id_reunion"));
                    reunion.setIdGrupo(rs.getInt("id_grupo"));
                    reunion.setFecha(rs.getDate("fecha"));
                    reunion.setTemasTratados(rs.getString("temas_tratados"));
                    reunion.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                    reunion.setAsistentes(rs.getString("asistentes"));

                    reuniones.add(reunion);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reuniones;
    }
    
    // Crear una reunión realizada junto con sus asistentes (todo o nada)
    public boolean crearConAsistentes(Reunion reunion, List<Integer> idsAsistentes) {

        String sqlReunion = "INSERT INTO reuniones (id_grupo, fecha, temas_tratados) VALUES (?, ?, ?)";
        String sqlAsistencia = "INSERT INTO asistencias_reunion (id_reunion, id_usuario) VALUES (?, ?)";

        try (Connection con = Conexion.getConnection()) {

            con.setAutoCommit(false);

            try (PreparedStatement psReunion = con.prepareStatement(sqlReunion, Statement.RETURN_GENERATED_KEYS)) {

                psReunion.setInt(1, reunion.getIdGrupo());
                psReunion.setDate(2, reunion.getFecha());
                psReunion.setString(3, reunion.getTemasTratados());
                psReunion.executeUpdate();

                int idReunion;
                try (ResultSet rs = psReunion.getGeneratedKeys()) {
                    if (!rs.next()) {
                        throw new SQLException("No se pudo obtener el id de la reunión");
                    }
                    idReunion = rs.getInt(1);
                }

                try (PreparedStatement psAsistencia = con.prepareStatement(sqlAsistencia)) {
                    for (int idUsuario : idsAsistentes) {
                        psAsistencia.setInt(1, idReunion);
                        psAsistencia.setInt(2, idUsuario);
                        psAsistencia.addBatch();
                    }
                    psAsistencia.executeBatch();
                }

                con.commit();
                return true;

            } catch (SQLException e) {
                con.rollback();
                throw e;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}