package com.stefanini.servico;

import com.stefanini.dao.EnderecoDao;
import com.stefanini.model.Endereco;
import com.stefanini.util.IGenericService;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 *
 * Classe de servico, as regras de negocio devem estar nessa classe
 * @author joaopedromilhome
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class EnderecoServico implements Serializable {

	@Inject
	private EnderecoDao dao;


//	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Endereco salvar(@Valid Endereco entity) throws Exception {
		return dao.salvar(entity);
	}

//	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Endereco atualizar(@Valid Endereco entity) {
		return dao.atualizar(entity);
	}

//	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remover(Long id) {
	dao.remover(id);
	}

//	@Override
	public Optional<List<Endereco>> getList() {
		return dao.getList();
	}

//	@Override
	public Optional<Endereco> encontrar(Long id) {
		return dao.encontrar(id);
	}
}
