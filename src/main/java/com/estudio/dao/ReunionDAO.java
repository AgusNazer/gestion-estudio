package com.estudio.dao;

import com.estudio.conexion.Conexion;
import com.estudio.model.Reunion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

        String sql = "SELECT * FROM reuniones WHERE id_grupo = ? ORDER BY fecha";

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

                    reuniones.add(reunion);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reuniones;
    }
}