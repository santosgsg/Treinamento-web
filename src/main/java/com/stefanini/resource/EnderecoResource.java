package com.stefanini.resource;

import com.stefanini.model.Endereco;

import com.stefanini.servico.EnderecoServico;

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
        try{
        return Response.ok(enderecoServico.getList().get()).build();
        }
        catch(Exception ex){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro Inesperado").build();
        }
    }

    @POST
    public Response salvarEndereco(@Valid Endereco endereco) throws Exception {
        try{
        return Response.ok(enderecoServico.salvar(endereco)).build();
        }
        catch(Exception ex){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro Inesperado").build();
        }
    }

    @PUT
    public Response atualizarEndereco(@Valid Endereco endereco) throws Exception {
        try{
        return Response.ok(enderecoServico.atualizar(endereco)).build();
        }
        catch(Exception ex){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro Inesperado").build();
        }
    }

    @DELETE
    public Response deleteEndereco(@Valid Endereco endereco) throws Exception {
        try{
        return Response.ok(enderecoServico.remover(endereco.getId())).build();
        }
        catch(Exception ex){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro Inesperado").build();
        }
    }


    @GET
    @Path("{id}")
    public Response obterEndereco(@PathParam("id") Long id) {
        try {
        return Response.ok(enderecoServico.encontrar(id).get()).build();
        }
        catch(Exception ex){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro Inesperado").build();
        }
//		return Response.ok(enderecoServico.encontrar(id).get()).build();
    }
}
