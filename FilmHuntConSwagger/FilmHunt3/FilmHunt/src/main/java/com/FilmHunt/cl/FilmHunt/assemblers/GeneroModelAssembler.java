package com.FilmHunt.cl.FilmHunt.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.FilmHunt.cl.FilmHunt.controller.GeneroControllerV2;
import com.FilmHunt.cl.FilmHunt.model.genero;

@Component

public class GeneroModelAssembler implements RepresentationModelAssembler<genero, EntityModel<genero>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<genero> toModel(genero entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(GeneroControllerV2.class).getgeneroById(entity.getId())).withSelfRel(),
                linkTo(methodOn(GeneroControllerV2.class).getAllGenero()).withRel("genero"),
                linkTo(methodOn(GeneroControllerV2.class).createGenero(entity)).withRel("agregar"),
                linkTo(methodOn(GeneroControllerV2.class).deleteById(entity.getId())).withRel("eliminar"),
                linkTo(methodOn(GeneroControllerV2.class).patchGenero(entity.getId(), entity)).withRel("actualizar-parcial")
        );
    }

}
