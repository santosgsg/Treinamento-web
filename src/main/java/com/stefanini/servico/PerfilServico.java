package com.stefanini.servico;

import com.stefanini.dao.PerfilDao;
import com.stefanini.dao.PessoaDao;
import com.stefanini.model.Perfil;

import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.Valid;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class PerfilServico implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Inject
    private PerfilDao dao;

    /**
     * Salvar os dados de um Perfil
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Perfil salvar(@Valid Perfil perfil) throws Exception {
        perfil.setDataHoraInclusao(LocalDateTime.now());
        perfil.setDataHoraAlteracao(perfil.getDataHoraInclusao());
        return dao.salvar(perfil);
    }

    /**
     * Atualizar o dados de um Perfil
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Perfil atualizar(@Valid Perfil perfil) {
        perfil.setDataHoraAlteracao(LocalDateTime.now());
        Optional<Perfil> ultimoRegistro = dao.encontrar(perfil.getId());
        perfil.setDataHoraInclusao(ultimoRegistro.get().getDataHoraInclusao());

        return dao.atualizar(perfil);
    }

    /**
     * Remover um Perfil pelo id
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String remover(@Valid Long id) {
        dao.remover(id);
        return "Removido!";
    }

    /**
     * Buscar uma lista de Perfil
     */
    public Optional<List<Perfil>> getList() {
        return dao.getList();
    }

    /**
     * Buscar um Perfil pelo ID
     */
    public Optional<Perfil> encontrar(Long id) {
        return dao.encontrar(id);
    }

}
