package com.stefanini.resource;


import com.stefanini.model.PessoaPerfil;
import com.stefanini.servico.PessoaPerfilServico;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("perfis")
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
        return Response.ok(pessoaPerfilServico.salvar(perfil)).build();
    }


    @GET
    @Path("{id}")
    public Response obterPerfil(@PathParam("id") Long id) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("deu ruim").build();
//		return Response.ok(pessoaServico.encontrar(id).get()).build();
    }
}
