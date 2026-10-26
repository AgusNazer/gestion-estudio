package com.estudio.service;

import com.estudio.dao.GrupoDAO;
import com.estudio.model.Grupo;

public class GrupoService {

    private final GrupoDAO grupoDAO = new GrupoDAO();

    public boolean crearGrupo(Grupo g) {

        if (g.getNombre() == null || g.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del grupo es obligatorio.");
        }
        if (g.getCupoMaximo() < 2) {
            throw new IllegalArgumentException("El cupo mínimo debe ser de 2 personas.");
        }

        // New check to prevent duplicate groups
        if (grupoDAO.existeGrupoPorNombreYCreador(g.getNombre(), g.getIdCreador())) {
            throw new IllegalArgumentException("Ya tenés un grupo activo con ese nombre.");
        }

        return grupoDAO.crearGrupo(g);
    }
}