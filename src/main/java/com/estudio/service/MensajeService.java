package com.estudio.service;

import com.estudio.dao.GrupoDAO;
import com.estudio.dao.MensajeDAO;
import com.estudio.model.Grupo;
import com.estudio.model.Mensaje;

public class MensajeService {

	private final MensajeDAO mensajeDAO = new MensajeDAO();
	private final GrupoDAO grupoDAO = new GrupoDAO();

	public boolean crearMensaje(Mensaje m) {
		if (m == null) {
			throw new IllegalArgumentException("Los datos del mensaje son obligatorios");
		}
		if (m.getMensaje() == null || m.getMensaje().trim().isEmpty()) {
			throw new IllegalArgumentException("El mensaje no puede estar vacío");
		}
		if (m.getMensaje().length() > 500) {
			throw new IllegalArgumentException("El mensaje supera el límite de caracteres permitidos");
		}

		Grupo grupo = grupoDAO.obtenerPorId(m.getIdGrupo());
		if (grupo == null) {
			throw new IllegalArgumentException("El grupo no existe");
		}

		boolean esCreador = grupo.getIdCreador() == m.getIdUsuario();
		if (!esCreador && !grupoDAO.esMiembro(m.getIdGrupo(), m.getIdUsuario())) {
			throw new IllegalArgumentException("No tienes permiso para enviar mensajes en este grupo");
		}

		return mensajeDAO.crearMensaje(m);
	}
}