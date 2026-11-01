package com.estudio.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Reunion {

    private int idReunion;
    private int idGrupo;
    private Date fecha;
    private String temasTratados;
    private Timestamp fechaRegistro;

    public Reunion() {
    }

    public Reunion(int idGrupo, Date fecha, String temasTratados) {
        this.idGrupo = idGrupo;
        this.fecha = fecha;
        this.temasTratados = temasTratados;
    }

    public int getIdReunion() {
        return idReunion;
    }

    public void setIdReunion(int idReunion) {
        this.idReunion = idReunion;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTemasTratados() {
        return temasTratados;
    }

    public void setTemasTratados(String temasTratados) {
        this.temasTratados = temasTratados;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}