package com.FilmHunt.cl.FilmHunt.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.FilmHunt.cl.FilmHunt.controller.DirectoresControllerV2;
import com.FilmHunt.cl.FilmHunt.model.directores;

@Component

public class DirectoresModelAssembler implements RepresentationModelAssembler<directores, EntityModel<directores>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<directores> toModel(directores entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(DirectoresControllerV2.class).getdirectoresById(entity.getId())).withSelfRel(),
                linkTo(methodOn(DirectoresControllerV2.class).getAllDirectores()).withRel("directores"),
                linkTo(methodOn(DirectoresControllerV2.class).updateDirectores(entity.getId(), entity)).withRel("actualizar"),
                linkTo(methodOn(DirectoresControllerV2.class).deleteById(entity.getId())).withRel("eliminar"),
                linkTo(methodOn(DirectoresControllerV2.class).patchDirectores(entity.getId(), entity)).withRel("actualizar-parcial")
        );
    }
}
