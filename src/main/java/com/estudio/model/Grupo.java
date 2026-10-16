package com.estudio.model;
public class Grupo {
    private int idGrupo;
    private String nombre;
    private String materia;
    private String horarios;
    private String modalidad;
    private int cupoMaximo;
    private String objetivo;
    private boolean espacioAccesible;
    private int idCreador;
    private String estado;

    public Grupo() {}

    // Constructor con los datos
    public Grupo(String nombre, String materia, String horarios, String modalidad, int cupoMaximo, String objetivo, boolean espacioAccesible, int idCreador) {
        this.nombre = nombre;
        this.materia = materia;
        this.horarios = horarios;
        this.modalidad = modalidad;
        this.cupoMaximo = cupoMaximo;
        this.objetivo = objetivo;
        this.espacioAccesible = espacioAccesible;
        this.idCreador = idCreador;
        this.estado = "Abierto"; // Por defecto arranca abierto
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getHorarios() {
        return horarios;
    }

    public void setHorarios(String horarios) {
        this.horarios = horarios;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public boolean isEspacioAccesible() {
        return espacioAccesible;
    }

    public void setEspacioAccesible(boolean espacioAccesible) {
        this.espacioAccesible = espacioAccesible;
    }

    public int getIdCreador() {
        return idCreador;
    }

    public void setIdCreador(int idCreador) {
        this.idCreador = idCreador;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}