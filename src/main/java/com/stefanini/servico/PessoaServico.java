package com.stefanini.servico;

import com.stefanini.dao.PessoaDao;
import com.stefanini.model.Pessoa;
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
 * 
 * @author joaopedromilhome
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class PessoaServico implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private PessoaDao dao;

	/**
	 * Salvar os dados de uma Pessoa
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Pessoa salvar(@Valid Pessoa pessoa) throws Exception {
		return dao.salvar(pessoa);
	}

	/**
	 * Atualizar o dados de uma pessoa
	 */
//	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Pessoa atualizar(@Valid Pessoa pessoa) {
		return dao.atualizar(pessoa);
	}

	/**
	 * Remover uma pessoa pelo id
	 */
//	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String remover(@Valid Long id) {
		dao.remover(id);
		return "Removido!";
	}

	/**
	 * Buscar uma lista de Pessoa
	 */
//	@Override
	public Optional<List<Pessoa>> getList() {
		return dao.getList();
	}

	/**
	 * Buscar uma Pessoa pelo ID
	 */
//	@Override
	public Optional<Pessoa> encontrar(Long id) {
		return dao.encontrar(id);
	}

	public Optional<List<Pessoa>> obterListaPessoaPorUf(String uf) {
		return dao.obterListaPessoaPorUf(uf);
	}

}
