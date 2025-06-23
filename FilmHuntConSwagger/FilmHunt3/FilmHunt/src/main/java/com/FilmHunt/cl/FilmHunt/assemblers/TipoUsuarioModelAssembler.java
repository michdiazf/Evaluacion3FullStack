package com.FilmHunt.cl.FilmHunt.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.FilmHunt.cl.FilmHunt.controller.TipoUsuarioControllerV2;
import com.FilmHunt.cl.FilmHunt.model.TipoUsuario;

@Component

public class TipoUsuarioModelAssembler implements RepresentationModelAssembler<TipoUsuario, EntityModel<TipoUsuario>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<TipoUsuario> toModel(TipoUsuario updateTipoUsuario) {
        return EntityModel.of(updateTipoUsuario,
                linkTo(methodOn(TipoUsuarioControllerV2.class).getTipoUsuarioById(updateTipoUsuario.getIdTipoUsuario())).withSelfRel(),
                linkTo(methodOn(TipoUsuarioControllerV2.class).getAllTipoUsuario()).withRel("tipo de usuario"),
                linkTo(methodOn(TipoUsuarioControllerV2.class).createTipoUsuario(updateTipoUsuario)).withRel("agregar"),
                linkTo(methodOn(TipoUsuarioControllerV2.class).deleteById(updateTipoUsuario.getIdTipoUsuario())).withRel("eliminar"),
                linkTo(methodOn(TipoUsuarioControllerV2.class).patchTipoUsuario(updateTipoUsuario.getIdTipoUsuario(), updateTipoUsuario)).withRel("actualizar-parcial")
        );
    }
}
