package com.FilmHunt.cl.FilmHunt.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.FilmHunt.cl.FilmHunt.controller.ListaControllerV2;
import com.FilmHunt.cl.FilmHunt.model.Lista;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;



@Component

public class ListaModelAssembler implements RepresentationModelAssembler<Lista, EntityModel<Lista>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Lista> toModel(Lista updatedLista) {
        return EntityModel.of(updatedLista,
                linkTo(methodOn(ListaControllerV2.class).getlistaById(updatedLista.getId())).withSelfRel(),
                linkTo(methodOn(ListaControllerV2.class).getAllLista()).withRel("lista"),
                linkTo(methodOn(ListaControllerV2.class).createLista(updatedLista)).withRel("agregar"),
                linkTo(methodOn(ListaControllerV2.class).deleteById(updatedLista.getId())).withRel("eliminar"),
                linkTo(methodOn(ListaControllerV2.class).patchLista(updatedLista.getId(), updatedLista)).withRel("actualizar-parcial")
        );
    }
}
