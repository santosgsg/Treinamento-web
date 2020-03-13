package com.stefanini.resource;

import com.stefanini.model.Perfil;
import com.stefanini.servico.PerfilServico;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Path("perfil")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PerfilResource {
    @Inject
    private PerfilServico perfilServico;

    @GET
    public Response obterListaPerfil() {
        try {
            return Response.ok(perfilServico.getList().get()).build();
        }
        catch(Exception ex){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro Inesperado").build();
        }
    }

    @POST
    public Response salvarPerfil(Perfil perfil) {
        try{
            return Response.ok(perfilServico.salvar(perfil)).build();
        }
        catch(Exception ex){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro Inesperado").build();
        }
    }

    @PUT
    public Response atualizarPerfil(Perfil perfil) {
        try {
            return Response.ok(perfilServico.atualizar(perfil)).build();
        }
        catch(Exception ex){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro Inesperado").build();
        }
    }

    @DELETE
    public Response removerPerfil(Perfil perfil) {
        try {
            return Response.ok(perfilServico.remover(perfil.getId())).build();
        }
        catch(Exception ex){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro Inesperado").build();
        }
    }


    @GET
    @Path("{id}")
    public Response obterPerfil(@PathParam("id") Long id) {
        try {
            return Response.ok(perfilServico.encontrar(id).get()).build();
        }
        catch (NoSuchElementException ex){
            return Response.status(Response.Status.FORBIDDEN).entity("Perfil não encontrado").build();
        }
        catch (Exception ex){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro Inesperado").build();
        }
    }
}
