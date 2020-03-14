package com.stefanini.servico;

import com.stefanini.dao.EnderecoDao;
import com.stefanini.dao.PessoaPerfilDao;
import com.stefanini.model.Endereco;
import com.stefanini.model.PessoaPerfil;
import com.stefanini.util.IGenericService;

import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class PessoaPerfilServico implements Serializable {

    @Inject
    private PessoaPerfilDao dao;


//    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public PessoaPerfil salvar(@Valid PessoaPerfil entity) {
        return dao.salvar(entity);
    }

//    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public PessoaPerfil atualizar(@Valid PessoaPerfil entity) {
        return dao.atualizar(entity);
    }

//    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String remover(Long id) {
        dao.remover(id);
        return "Removido com sucesso!";
    }

//    @Override
    public Optional<List<PessoaPerfil>> getList() {
        return Optional.empty();
    }

//    @Override
    public Optional<PessoaPerfil> encontrar(Long id) {
        return dao.encontrar(id);
    }
}
