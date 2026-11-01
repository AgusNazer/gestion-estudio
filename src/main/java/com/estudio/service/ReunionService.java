package com.estudio.service;

import com.estudio.dao.ReunionDAO;
import com.estudio.model.Reunion;

import java.sql.Date;
import java.util.List;

public class ReunionService {

    private final ReunionDAO reunionDAO = new ReunionDAO();

    public boolean crearReunion(int idGrupo, Date fecha, String temasTratados) {

        if (idGrupo <= 0) {
            throw new IllegalArgumentException("El grupo no es válido");
        }

        if (fecha == null) {
            throw new IllegalArgumentException("La fecha es obligatoria");
        }

        if (temasTratados == null || temasTratados.trim().isEmpty()) {
            throw new IllegalArgumentException("Los temas de la reunión son obligatorios");
        }

        if (temasTratados.trim().length() > 255) {
            throw new IllegalArgumentException("Los temas no pueden superar los 255 caracteres");
        }

        Reunion reunion = new Reunion(idGrupo, fecha, temasTratados.trim());

        return reunionDAO.crear(reunion);
    }

    public List<Reunion> listarReuniones(int idGrupo) {

        if (idGrupo <= 0) {
            throw new IllegalArgumentException("El grupo no es válido");
        }

        return reunionDAO.listarPorGrupo(idGrupo);
    }
}