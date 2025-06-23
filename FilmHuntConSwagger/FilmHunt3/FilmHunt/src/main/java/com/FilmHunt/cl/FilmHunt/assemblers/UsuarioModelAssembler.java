package com.FilmHunt.cl.FilmHunt.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.FilmHunt.cl.FilmHunt.controller.UsuarioControllerV2;
import com.FilmHunt.cl.FilmHunt.model.Usuario;


@Component

public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Usuario> toModel(Usuario updateUsuario) {
        return EntityModel.of(updateUsuario,
                linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(updateUsuario.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withRel("tipo de usuario"),
                linkTo(methodOn(UsuarioControllerV2.class).createUsuario(updateUsuario)).withRel("agregar"),
                linkTo(methodOn(UsuarioControllerV2.class).deleteById(updateUsuario.getId())).withRel("eliminar"),
                linkTo(methodOn(UsuarioControllerV2.class).patchUsuario(updateUsuario.getId(), updateUsuario)).withRel("actualizar-parcial")
        );
    }
}
