package com.FilmHunt.cl.FilmHunt.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.FilmHunt.cl.FilmHunt.controller.EstadoPeliculaControllerV2;
import com.FilmHunt.cl.FilmHunt.model.estadoPelicula;

@Component


public class EstadoPeliculaModelAssembler implements RepresentationModelAssembler<estadoPelicula, EntityModel<estadoPelicula>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<estadoPelicula> toModel(estadoPelicula entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(EstadoPeliculaControllerV2.class).getestadoPeliculaById(entity.getId())).withSelfRel(),
                linkTo(methodOn(EstadoPeliculaControllerV2.class).getAllEstadoPelicula()).withRel("estado peliculas"),
                linkTo(methodOn(EstadoPeliculaControllerV2.class).createestadoPelicula(entity)).withRel("agregar"),
                linkTo(methodOn(EstadoPeliculaControllerV2.class).deleteById(entity.getId())).withRel("eliminar"),
                linkTo(methodOn(EstadoPeliculaControllerV2.class).patchEstadoPelicula(entity.getId(), entity)).withRel("actualizar-parcial")
        );
    }
}
