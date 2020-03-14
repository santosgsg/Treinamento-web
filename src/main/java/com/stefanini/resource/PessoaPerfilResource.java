package com.stefanini.resource;


import com.stefanini.model.Perfil;
import com.stefanini.model.PessoaPerfil;
import com.stefanini.servico.PessoaPerfilServico;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("pessoaPerfil")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PessoaPerfilResource {

    @Inject
    private PessoaPerfilServico pessoaPerfilServico;

    @GET
    public Response obterListaPerfil() {
        return Response.ok(pessoaPerfilServico.getList().get()).build();
    }

    @POST
    public Response salvarPerfil(@Valid PessoaPerfil perfil) throws Exception {
        try {
        return Response.ok(pessoaPerfilServico.salvar(perfil)).build();
        }
        catch(Exception ex){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro Inesperado").build();
        }
    }


    @GET
    @Path("{id}")
    public Response obterPessoaPerfil(@PathParam("id") Long id) {
        try {
        return Response.ok(pessoaPerfilServico.encontrar(id).get()).build();
        }
        catch(Exception ex){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro Inesperado").build();
        }
    }
//		return Response.ok(pessoaServico.encontrar(id).get()).build();


    @DELETE
    public Response removerPessoaPerfil(PessoaPerfil pessoaPerfil) {
        try {
            return Response.ok(pessoaPerfilServico.remover(pessoaPerfil.getId())).build();
        }
        catch(Exception ex){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro Inesperado").build();
        }
    }
}
