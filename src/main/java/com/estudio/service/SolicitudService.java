package com.estudio.service;

import com.estudio.dao.SolicitudDAO;
import com.estudio.model.Solicitud;

public class SolicitudService {
	
	   private final SolicitudDAO solicitudDAO = new SolicitudDAO();
	   
	   public boolean crearSolicitud(Solicitud s) {
	       if (s == null) {
	           throw new IllegalArgumentException("Los datos de la solicitud son obligatorios");
	       }
	       if (s.getIdGrupo() <= 0) {
	    	    throw new IllegalArgumentException("Debe indicar un grupo válido");
	    	}
	       if (s.getIdUsuario() <= 0) {
	    	    throw new IllegalArgumentException("Debe indicar un usuario válido");
	    	}
	       if (s.getMensajePresentacion() != null && s.getMensajePresentacion().length() > 255) {
	    	   throw new IllegalArgumentException("El mensaje no puede superar los 255 caracteres");
	       }	       
	       return solicitudDAO.crearSolicitud(s);
	   }
}
