package com.estudio.model;

import java.sql.Timestamp;

public class Mensaje {

	private int idMensaje;
	private int idGrupo;
	private int idUsuario;
	private String mensaje;
	private Timestamp fechaHora;

	public Mensaje() {

	}

	public Mensaje(int idGrupo, int idUsuario, String mensaje) {
		this.idGrupo = idGrupo;
		this.idUsuario = idUsuario;
		this.mensaje = mensaje;
	}

	public int getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(int idMensaje) {
		this.idMensaje = idMensaje;
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

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Timestamp getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Timestamp fechaHora) {
		this.fechaHora = fechaHora;
	}

}
