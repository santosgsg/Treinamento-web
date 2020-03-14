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

    /**
     * Salvar a associação entre pessoa e perfil
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public PessoaPerfil salvar(@Valid PessoaPerfil entity) {
        return dao.salvar(entity);
    }

    /**
     * Atualizar a associação entre pessoa e perfil
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public PessoaPerfil atualizar(@Valid PessoaPerfil entity) {
        return dao.atualizar(entity);
    }

    /**
     * Remover a associação entre pessoa e perfil pelo id
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String remover(Long id) {
        dao.remover(id);
        return "Removido com sucesso!";
    }

    /**
     * Buscar as associações entre pessoa e perfil cadastradas na tabela
     */
    public Optional<List<PessoaPerfil>> getList() {
        return Optional.empty();
    }

    /**
     * Buscar a associação entre pessoa e perfil pelo Id
     */
    public Optional<PessoaPerfil> encontrar(Long id) {
        return dao.encontrar(id);
    }
}
