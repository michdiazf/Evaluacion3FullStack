package com.FilmHunt.cl.FilmHunt.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.FilmHunt.cl.FilmHunt.controller.ResenaControllerV2;
import com.FilmHunt.cl.FilmHunt.model.resena;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component

public class ResenaModelAssembler implements RepresentationModelAssembler<resena, EntityModel<resena>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<resena> toModel(resena updatedresena) {
        return EntityModel.of(updatedresena,
                linkTo(methodOn(ResenaControllerV2.class).getresenaById(updatedresena.getId())).withSelfRel(),
                linkTo(methodOn(ResenaControllerV2.class).getAllResenas()).withRel("resenas"),
                linkTo(methodOn(ResenaControllerV2.class).createResena(updatedresena)).withRel("agregar"),
                linkTo(methodOn(ResenaControllerV2.class).deleteById(updatedresena.getId())).withRel("eliminar"),
                linkTo(methodOn(ResenaControllerV2.class).patchResena(updatedresena.getId(), updatedresena)).withRel("actualizar-parcial")
        );
    }
}
