package com.estudio.dao;

import com.estudio.conexion.Conexion;
import com.estudio.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public boolean registrar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, apellido, email, password, carrera, " +
                     "pref_comunicacion, requiere_espacio_accesible, pref_material_digital) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getPassword());
            ps.setString(5, usuario.getCarrera());
            ps.setString(6, usuario.getPrefComunicacion() != null ? usuario.getPrefComunicacion() : "Indistinto");
            ps.setBoolean(7, usuario.isRequiereEspacioAccesible());
            ps.setBoolean(8, usuario.isPrefMaterialDigital());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Usuario login(String email, String password) {
        String sql = "SELECT id_usuario, nombre, apellido, email, password, carrera, " +
                     "pref_comunicacion, requiere_espacio_accesible, pref_material_digital, fecha_registro " +
                     "FROM usuarios WHERE email = ? AND password = ?";
        Usuario usuario = null;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setPassword(rs.getString("password"));
                    usuario.setCarrera(rs.getString("carrera"));
                    usuario.setPrefComunicacion(rs.getString("pref_comunicacion"));
                    usuario.setRequiereEspacioAccesible(rs.getBoolean("requiere_espacio_accesible"));
                    usuario.setPrefMaterialDigital(rs.getBoolean("pref_material_digital"));
                    usuario.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error en login: " + e.getMessage());
            e.printStackTrace();
        }

        return usuario;
    }

    public boolean existeEmail(String email) {
        String sql = "SELECT id_usuario FROM usuarios WHERE email = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.err.println("Error al validar email: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Main para verificar contra MySQL
    public static void main(String[] args) {
        UsuarioDAO dao = new UsuarioDAO();

        // 1. Probar registro con campos de accesibilidad
        Usuario nuevo = new Usuario("Test", "Accesible", "test_acc@estudio.com", "pass123", "Programación", "Voz", true, false);
        boolean registrado = dao.registrar(nuevo);
        System.out.println("¿Usuario registrado con accesibilidad?: " + registrado);

        // 2. Probar login recuperando las preferencias
        Usuario usuarioLogueado = dao.login("test_acc@estudio.com", "pass123");
        if (usuarioLogueado != null) {
            System.out.println("Login OK: " + usuarioLogueado.getNombre() + 
                               " | Requiere espacio: " + usuarioLogueado.isRequiereEspacioAccesible() +
                               " | Pref comunicación: " + usuarioLogueado.getPrefComunicacion());
        } else {
            System.err.println("Error al loguear.");
        }
    }
}