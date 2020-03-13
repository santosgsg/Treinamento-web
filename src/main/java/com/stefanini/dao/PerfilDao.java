package com.stefanini.dao;

import com.stefanini.dao.abstracao.GenericDao;
import com.stefanini.model.Perfil;

public class PerfilDao extends GenericDao<Perfil, Long> {

    public PerfilDao() {
        super(Perfil.class);
    }
}
