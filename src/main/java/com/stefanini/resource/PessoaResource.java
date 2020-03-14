package com.stefanini.resource;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.stefanini.model.Pessoa;
import com.stefanini.servico.PessoaServico;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Path("pessoas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PessoaResource {

	@Inject
	private PessoaServico pessoaServico;

	@GET
	public Response obterListaPessoa() {
		try {
			return Response.ok(pessoaServico.getList().get()).build();
		}
		catch(Exception ex){
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro Inesperado").build();
		}
	}

	@POST
	public Response salvarPessoa(Pessoa pessoa) {
		try{
		return Response.ok(pessoaServico.salvar(pessoa)).build();
		}
		catch(Exception ex){
			if(ex.getMessage().equals("Email já está cadastrado"))
				return Response.status(Status.CONFLICT).entity(ex.getMessage()).build();
			else
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("erro inesperado").build();
		}
	}

	@PUT
	public Response atualizarPessoa(Pessoa pessoa) {
		try {
		return Response.ok(pessoaServico.atualizar(pessoa)).build();
		}
		catch(Exception ex){
			if(ex.getMessage().equals("Email já está cadastrado"))
				return Response.status(Status.CONFLICT).entity(ex.getMessage()).build();
			else
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("erro inesperado").build();
		}
	}

    @DELETE
    public Response removerPessoa(Pessoa pessoa) {
		try {
        return Response.ok(pessoaServico.remover(pessoa.getId())).build();
		}
		catch(Exception ex){
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro Inesperado").build();
		}
    }
	

	@GET
	@Path("{id}")
	public Response obterPessoa(@PathParam("id") Long id) {
	    try {
		return Response.ok(pessoaServico.encontrar(id).get()).build();
		}
	    catch (NoSuchElementException ex){
	        return Response.status(Status.FORBIDDEN).entity("Pessoa não encontrada").build();
        }
	    catch (Exception ex){
	    	return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro Inesperado").build();
		}
	}

	@GET
	@Path("uf/{uf}")
	public Response obterListaPessoaPorUf(@PathParam("uf") String uf) {
		try {
			Optional<List<Pessoa>> result =  pessoaServico.obterListaPessoaPorUf(uf);
			return Response.ok(result.get()).build();
		}
		catch (Exception ex){
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
		}
	}

}
