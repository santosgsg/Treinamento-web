package com.stefanini.dao;

import com.stefanini.dao.abstracao.GenericDao;
import com.stefanini.model.Endereco;
import com.stefanini.model.PessoaPerfil;

public class PessoaPerfilDao  extends GenericDao<PessoaPerfil, Long> {

    public PessoaPerfilDao() {
        super(PessoaPerfil.class);
    }

}
