package com.estudio.model;

import java.sql.Timestamp;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String carrera;
    private String prefComunicacion;
    private boolean requiereEspacioAccesible;
    private boolean prefMaterialDigital;
    private Timestamp fechaRegistro;

    public Usuario() {
    }

    // Constructor para nuevos registros
    public Usuario(String nombre, String apellido, String email, String password, String carrera,
                   String prefComunicacion, boolean requiereEspacioAccesible, boolean prefMaterialDigital) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.carrera = carrera;
        this.prefComunicacion = prefComunicacion;
        this.requiereEspacioAccesible = requiereEspacioAccesible;
        this.prefMaterialDigital = prefMaterialDigital;
    }

    // Constructor completo (lectura de BD)
    public Usuario(int idUsuario, String nombre, String apellido, String email, String password,
                   String carrera, String prefComunicacion, boolean requiereEspacioAccesible,
                   boolean prefMaterialDigital, Timestamp fechaRegistro) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.carrera = carrera;
        this.prefComunicacion = prefComunicacion;
        this.requiereEspacioAccesible = requiereEspacioAccesible;
        this.prefMaterialDigital = prefMaterialDigital;
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }

    public String getPrefComunicacion() { return prefComunicacion; }
    public void setPrefComunicacion(String prefComunicacion) { this.prefComunicacion = prefComunicacion; }

    public boolean isRequiereEspacioAccesible() { return requiereEspacioAccesible; }
    public void setRequiereEspacioAccesible(boolean requiereEspacioAccesible) { this.requiereEspacioAccesible = requiereEspacioAccesible; }

    public boolean isPrefMaterialDigital() { return prefMaterialDigital; }
    public void setPrefMaterialDigital(boolean prefMaterialDigital) { this.prefMaterialDigital = prefMaterialDigital; }

    public Timestamp getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Timestamp fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}