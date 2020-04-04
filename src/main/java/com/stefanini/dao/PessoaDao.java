package com.stefanini.dao;

import com.stefanini.dao.abstracao.GenericDao;
import com.stefanini.dto.PessoaDto;
import com.stefanini.model.Pessoa;
import com.stefanini.model.PessoaPerfil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * O Unico objetivo da Dao 
 * @author joaopedromilhome
 *
 */
public class PessoaDao extends GenericDao<Pessoa, Long> {

	public PessoaDao() {
		super(Pessoa.class);
	}
	public Optional<List<Pessoa>> obterListaPessoaPorUf(String uf){
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT p.co_seq_pessoa, p.no_nome, p.ds_email, p.dt_nascimento, p.st_pessoa ");
		sqlQuery.append("FROM public.tb_pessoa p inner join tb_endereco e on e.co_seq_pessoa = p.co_seq_pessoa ");
		sqlQuery.append("WHERE e.co_uf = '" + uf + "';");
		List retorno = new ArrayList();
		try {
			retorno = getEntityManager().createNativeQuery(sqlQuery.toString()).getResultList();
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		return Optional.of(retorno);
	}

    public Boolean emailRegistrado(String email) {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT co_seq_pessoa, no_nome, ds_email, dt_nascimento, st_pessoa ");
		sqlQuery.append("FROM public.tb_pessoa ");
		sqlQuery.append("WHERE ds_email = '" + email + "';");

		return !getEntityManager().createNativeQuery(sqlQuery.toString()).getResultList().isEmpty();
    }

    public Pessoa salvar(PessoaDto pessoaDto, String imagePath) {
		Pessoa pessoa = new Pessoa(
				pessoaDto.getNome(),
				pessoaDto.getEmail(),
				pessoaDto.getDataNascimento(),
				pessoaDto.getSituacao(),
				imagePath);

		this.getEntityManager().persist(pessoa);

		if(pessoaDto.getEnderecos() != null) {
			pessoaDto.getEnderecos().forEach(x -> {
				x.setIdPessoa(pessoa.getId());
				try {
					this.getEntityManager().persist(x);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}

		if(pessoaDto.getPerfils() != null) {
			pessoaDto.getPerfils().forEach(x -> {
				this.getEntityManager().persist(new PessoaPerfil(x, pessoa));
			});
		}

		return pessoa;
	}

}
