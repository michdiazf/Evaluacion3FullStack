package com.FilmHunt.cl.FilmHunt.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.FilmHunt.cl.FilmHunt.controller.PeliculasControllerV2;
import com.FilmHunt.cl.FilmHunt.model.Peliculas;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component

public class PeliculasModelAssembler implements RepresentationModelAssembler<Peliculas, EntityModel<Peliculas>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Peliculas> toModel(Peliculas updatedPeliculas) {
        return EntityModel.of(updatedPeliculas,
                linkTo(methodOn(PeliculasControllerV2.class).getpeliculasById(updatedPeliculas.getId())).withSelfRel(),
                linkTo(methodOn(PeliculasControllerV2.class).getAllPeliculas()).withRel("peliculas"),
                linkTo(methodOn(PeliculasControllerV2.class).createPelicula(updatedPeliculas)).withRel("agregar"),
                linkTo(methodOn(PeliculasControllerV2.class).deleteById(updatedPeliculas.getId())).withRel("eliminar"),
                linkTo(methodOn(PeliculasControllerV2.class).patchPeliculas(updatedPeliculas.getId(), updatedPeliculas)).withRel("actualizar-parcial")
        );
    }
}
