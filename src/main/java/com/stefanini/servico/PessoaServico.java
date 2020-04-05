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

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
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
		if(imagem != null) {
			if (imagem.getBase64().isEmpty())
				return null;
		} else {
			return null;
		}
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

	public Imagem obterImagem(String imagePath) {
		Imagem imagem = new Imagem();
		try {
			File file =  new File(imagePath);
			FileInputStream fileInputStreamReader = new FileInputStream(file);
			byte[] bytes = new byte[(int)file.length()];
			fileInputStreamReader.read(bytes);

			imagem.setBase64(new String(Base64.getEncoder().encode(bytes)));
			imagem.setNome(file.getName());

		}catch (IOException ex) {
			ex.printStackTrace();
		}

		return imagem ;
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
	public Pessoa atualizar(@Valid PessoaDto pessoaDto) throws Exception {

		this.validarEmail(pessoaDto);
		String imagePath = this.salvarImagem(pessoaDto.getImagem(), pessoaDto.getEmail());

		Pessoa pessoa = new Pessoa(
				pessoaDto.getNome(),
				pessoaDto.getEmail(),
				pessoaDto.getDataNascimento(),
				pessoaDto.getSituacao(),
				imagePath);
		pessoa.setId(pessoaDto.getId());
		pessoa.setPerfils(pessoaDto.getPerfils());
		pessoa.setEnderecos(pessoaDto.getEnderecos());

		return dao.atualizar(pessoa);
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
	public Optional<PessoaDto> encontrar(Long id) {
		PessoaDto pessoaDto = new PessoaDto();
		Pessoa pessoa = new Pessoa();
		try {
			pessoa = dao.buscarPessoaComEnderecoEPerfil(id);
		}catch (Exception ex){
			ex.printStackTrace();
		}

		pessoaDto.setId(pessoa.getId());
		pessoaDto.setNome(pessoa.getNome());
		pessoaDto.setEmail(pessoa.getEmail());
		pessoaDto.setDataNascimento(pessoa.getDataNascimento());
		pessoaDto.setSituacao(pessoa.getSituacao());

		if(pessoa.getImagem() != null)
			pessoaDto.setImagem(this.obterImagem(pessoa.getImagem()));

		if(pessoa.getPerfils() != null)
			pessoaDto.setPerfils(pessoa.getPerfils());

		if(pessoa.getEnderecos() != null)
			pessoaDto.setEnderecos(pessoa.getEnderecos());

		return Optional.of(pessoaDto);
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
		List<Object> result = dao.emailRegistrado(pessoa.getEmail());
		if (!result.isEmpty()) {
			if (pessoa.getId() != Long.decode(Array.get(result.get(0), 0).toString())) {
				throw new SecurityException();
			}
		}
	}
}
