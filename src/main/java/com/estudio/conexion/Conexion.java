package com.estudio.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // parametros de la conexion local
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_estudio_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASS = "root";

    // Driver moderno de MySQL
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver de MySQL en las dependencias.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error al intentar conectar a la base de datos MySQL.");
            e.printStackTrace();
        }
        return con;
    }

    // Método main de prueba inmediata sin necesidad de levantar Tomcat
    public static void main(String[] args) {
        System.out.println("Intentando conectar con MySQL...");
        Connection con = getConnection();

        if (con != null) {
            System.out.println("----------------------------------------------");
            System.out.println(">>> ¡CONEXIÓN EXITOSA A gestion_estudio_db! <<<");
            System.out.println("----------------------------------------------");
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("----------------------------------------------");
            System.err.println(">>> ERROR: NO SE PUDO ESTABLECER LA CONEXIÓN <<<");
            System.err.println("----------------------------------------------");
        }
    }
}