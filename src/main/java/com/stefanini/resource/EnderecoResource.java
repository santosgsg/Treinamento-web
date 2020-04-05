package com.stefanini.resource;

import com.stefanini.dto.EnderecoViaCepDTO;
import com.stefanini.model.Endereco;

import com.stefanini.servico.EnderecoServico;
import com.stefanini.servico.ViaCEP;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

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
    @Path("{id}")
    public Response deleteEndereco(@PathParam("id") Long id) throws Exception {
        try{
        return Response.ok(enderecoServico.remover(id)).build();
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

    @GET
    @Path("buscarCep/{cep}")
    public Response buscarCep(@PathParam("cep") String cep) {
        Optional<EnderecoViaCepDTO> endereco = ViaCEP.buscarCep(cep);
        return Response.ok(endereco.get()).build();
    }
}
