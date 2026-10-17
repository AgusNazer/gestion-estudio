package com.estudio.model;

import java.sql.Timestamp;

public class Solicitud {
	
	private int idSolicitud;
	private int idGrupo;
	private int idUsuario;
	private String mensajePresentacion;
	private String estado;
	private Timestamp fechaSolicitud;
	
	public int getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(int idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public int getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(int idGrupo) {
		this.idGrupo = idGrupo;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getMensajePresentacion() {
		return mensajePresentacion;
	}

	public void setMensajePresentacion(String mensajePresentacion) {
		this.mensajePresentacion = mensajePresentacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Timestamp fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	
	public Solicitud() {
		
	}
	
	public Solicitud (int idGrupo, int idUsuario, String mensajePresentacion) {
		this.idGrupo = idGrupo;
		this.idUsuario = idUsuario;
		this.mensajePresentacion = mensajePresentacion;
		this.estado ="Pendiente";
	}
}
