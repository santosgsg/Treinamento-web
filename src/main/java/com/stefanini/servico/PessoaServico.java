package com.stefanini.servico;

import com.stefanini.dao.PessoaDao;
import com.stefanini.dto.PessoaDto;
import com.stefanini.model.*;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.validation.Valid;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Base64;
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
	public Pessoa salvar(@Valid PessoaDto pessoaDto) throws Exception {
		this.validarEmail(pessoaDto);
		String imagePath = this.salvarImagem(pessoaDto.getImagem(), pessoaDto.getEmail());	
		
		return dao.salvar(pessoaDto, imagePath);
	}

	private String salvarImagem(Imagem imagem, String email) throws IOException {
		if(imagem == null)
			return null;
		String uniqueKey = this.createPathKey(email);

		String imagePath = "C:\\ImagemServidor\\" + uniqueKey +"\\";
		if(!(imagem.getNome().contains(".jpeg") || imagem.getNome().contains(".png"))) {
			String ext = imagem.getTipo().contains("jpeg") ? "jpeg" : "png";
			imagePath += imagem.getNome() + "." + ext;
		}
		else {
			imagePath += imagem.getNome();
		}

		byte[] decodedImg = Base64.getDecoder().decode(imagem.getBase64());
		BufferedImage img = ImageIO.read(new ByteArrayInputStream(decodedImg));

		File outputfile = new File(imagePath);
		try {
			outputfile.getParentFile().mkdirs();
		}catch (Exception ex){
			ex.getMessage();
		}
		ImageIO.write(img, "png", outputfile);
		 return imagePath;
	}

	private String createPathKey(String email) {
		return email
				.replace("@", "_")
				.replace(".", "_");
	}

	/**
	 * Atualizar o dados de uma pessoa
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Pessoa atualizar(@Valid PessoaDto pessoa) throws Exception {

		this.validarEmail(pessoa);
		String imagePath = this.salvarImagem(pessoa.getImagem(), pessoa.getEmail());

		Pessoa entity = new Pessoa(pessoa.getNome(), pessoa.getEmail(), pessoa.getDataNascimento(),
				pessoa.getSituacao(), imagePath);
		return dao.atualizar(entity);
	}

	/**
	 * Remover uma pessoa pelo id
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String remover(@Valid Long id) {
		dao.remover(id);
		return "Removido!";
	}

	/**
	 * Buscar uma lista de Pessoa
	 */
	public Optional<List<Pessoa>> getList() {
		return dao.getList();
	}

	/**
	 * Buscar uma Pessoa pelo ID
	 */
	public Optional<Pessoa> encontrar(Long id) {
		return dao.encontrar(id);
	}

	/**
	 * Buscar uma Pessoa pela Uf
	 */
	public Optional<List<Pessoa>> obterListaPessoaPorUf(String uf) {
		return dao.obterListaPessoaPorUf(uf);
	}

	/**
	 * Valida o email
	 */
	private void validarEmail(PessoaDto pessoa) throws Exception {
		if (dao.emailRegistrado(pessoa.getEmail()))
			throw new SecurityException();
	}
}
