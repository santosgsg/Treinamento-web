package com.stefanini.resource;

import com.stefanini.model.Endereco;
import com.stefanini.model.Pessoa;
import com.stefanini.servico.EnderecoServico;
import com.stefanini.servico.PessoaServico;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("enderecos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    @Inject
    private EnderecoServico enderecoServico;

    @GET
    public Response obterListaEndereco() {
        return Response.ok(enderecoServico.getList().get()).build();
    }

    @POST
    public Response salvarEndereco(@Valid Endereco endereco) throws Exception {
        return Response.ok(enderecoServico.salvar(endereco)).build();
    }

    @PUT
    public Response atualizarEndereco(@Valid Endereco endereco) throws Exception {
        return Response.ok(enderecoServico.atualizar(endereco)).build();
    }


    @GET
    @Path("{id}")
    public Response obterEndereco(@PathParam("id") Long id) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("deu ruim").build();
//		return Response.ok(enderecoServico.encontrar(id).get()).build();
    }
}
