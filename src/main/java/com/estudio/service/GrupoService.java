package com.estudio.service;

import com.estudio.dao.GrupoDAO;
import com.estudio.model.Grupo;

public class GrupoService {

    private final GrupoDAO grupoDAO = new GrupoDAO();

    public boolean crearGrupo(Grupo g) {

        if (g.getNombre() == null || g.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del grupo es obligatorio.");
        }
        if (g.getCupoMaximo() < 2 || g.getCupoMaximo() > 6) {
            throw new IllegalArgumentException("Datos de horario o cupo inválidos.");
        }
        if (g.getObjetivo() == null
                || !(g.getObjetivo().equals("Preparar parcial") || g.getObjetivo().equals("Estudio regular"))) {
            throw new IllegalArgumentException("Debe seleccionar un objetivo válido.");
        }

        // evita crear grupos duplicaos
        if (grupoDAO.existeGrupoPorNombreYCreador(g.getNombre(), g.getIdCreador())) {
            throw new IllegalArgumentException("Ya tenés un grupo activo con ese nombre.");
        }

        return grupoDAO.crearGrupo(g);
    }
}