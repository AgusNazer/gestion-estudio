package com.estudio.dao;

import com.estudio.conexion.Conexion;
import com.estudio.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    // Metodo para registrar un nuevo estudiante
    public boolean registrar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, apellido, email, password, carrera) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getPassword()); 
            ps.setString(5, usuario.getCarrera());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Metodo para validar credenciales en el login
    public Usuario login(String email, String password) {
        String sql = "SELECT id_usuario, nombre, apellido, email, password, carrera, fecha_registro " +
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
                    usuario.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error en login: " + e.getMessage());
            e.printStackTrace();
        }

        return usuario;
    }

    // Metodo opcional para verificar si el email ya existe antes de registrar
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

    // Main para verificar las operaciones directamente contra MySQL
    public static void main(String[] args) {
        UsuarioDAO dao = new UsuarioDAO();

        // 1. Probar agregar usuario
        Usuario nuevo = new Usuario("Agustín", "Nazer", "test@estudio.com", "123456", "Programación");
        boolean insertado = dao.registrar(nuevo);
        System.out.println("¿Usuario registrado con éxito?: " + insertado);

        // 2. Probar login 
        Usuario logueado = dao.login("test@estudio.com", "123456");
        if (logueado != null) {
            System.out.println("Login exitoso. Bienvenido " + logueado.getNombre() + " (ID: " + logueado.getIdUsuario() + ")");
        } else {
            System.out.println("Credenciales incorrectas.");
        }
    }
}