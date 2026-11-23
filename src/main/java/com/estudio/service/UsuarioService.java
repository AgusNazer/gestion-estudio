package com.estudio.service;

import com.estudio.dao.UsuarioDAO;
import com.estudio.model.Usuario;

public class UsuarioService {
	private final UsuarioDAO usuarioDAO = new UsuarioDAO();
	
	public boolean registrar(Usuario u) {
		if (u == null) {
			throw new IllegalArgumentException("Los datos del usuario son obligatorios");
		}
		String faltantes = "Debe completar todos los datos requeridos para el alta.";
		validarCampo(u.getNombre(), faltantes);
		validarCampo(u.getApellido(), faltantes);
		validarCampo(u.getEmail(), faltantes);
		validarCampo(u.getCarrera(), faltantes);
		validarCampo(u.getPassword(), faltantes);
		
		u.setNombre(u.getNombre().trim());
		u.setApellido(u.getApellido().trim());
		u.setEmail(u.getEmail().trim());
		u.setCarrera(u.getCarrera().trim());
		
		// Si la preferencia vino vacia, se deja por defecto
		if (u.getPrefComunicacion() == null || u.getPrefComunicacion().trim().isEmpty()) {
			u.setPrefComunicacion("Indistinto");
		}
		
		
		if (!u.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
			throw new IllegalArgumentException("El email no tiene un formato válido");
		}
		if (u.getPassword().length() < 8
		        || !u.getPassword().matches(".*[A-Za-z].*")
		        || !u.getPassword().matches(".*[0-9].*")) {
		    throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres e incluir letras y números.");
		}
		if (usuarioDAO.existeEmail(u.getEmail())) {
			throw new IllegalArgumentException("El correo electrónico ya se encuentra registrado.");
		}
		return usuarioDAO.registrar(u);
	}
	public Usuario login(String email, String password) {
		validarCampo(email, "El email es obligatorio");
		validarCampo(password, "La contraseña es obligatoria");
		
		return usuarioDAO.login(email, password);
	}
	
	private void validarCampo(String campo, String mensajeError) {
		if (campo == null || campo.trim().isEmpty()) {
			throw new IllegalArgumentException(mensajeError);
		}
	}
}
