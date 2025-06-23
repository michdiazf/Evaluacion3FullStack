package com.FilmHunt.cl.FilmHunt.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.FilmHunt.cl.FilmHunt.assemblers.EstadoPeliculaModelAssembler;
import com.FilmHunt.cl.FilmHunt.model.estadoPelicula;
import com.FilmHunt.cl.FilmHunt.service.EstadoPeliculaService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/EstadoPelicula")
@Tag(name = "Estado Pelicula", description = "Operaciones relacionadas con el estado de las peliculas")

public class EstadoPeliculaControllerV2 {

    @Autowired
    private EstadoPeliculaService EstadoPeliculaService;
    @Autowired
    private EstadoPeliculaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Obtener Estado de las Peliculas",
        description = "Obtiene todas los estados de las peliculas"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado de pelicula obtenida correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay estados a mostrar")
    })
    public CollectionModel<EntityModel<estadoPelicula>> getAllEstadoPelicula() {
        List<EntityModel<estadoPelicula>> estadoPelicula = EstadoPeliculaService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(estadoPelicula,
                linkTo(methodOn(EstadoPeliculaControllerV2.class).getAllEstadoPelicula()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Obtener Estado de las Peliculas",
        description = "Obtiene los estados de las peliculas por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado pelicula obtenido correctamente"),
        @ApiResponse(responseCode = "404", description = "Estado pelicula no encontrado")
    })
    public ResponseEntity<EntityModel<estadoPelicula>> getestadoPeliculaById(@PathVariable Integer id) {
        estadoPelicula estadoPelicula = EstadoPeliculaService.findById(id);
        if (estadoPelicula == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(estadoPelicula));
    }


    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Guardar Estado de Peliculas",
        description = "Guarda los estados de las peliculas en la base de datos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Estado pelicula agregado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = estadoPelicula.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Error al agregar Estado pelicula",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = String.class)
            )
        )
    })
    //dice a swagger que el metodo espera recibir un director en el cuerpo de la peticion
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Director a agregar", 
        required = true
    )
    public ResponseEntity<EntityModel<estadoPelicula>> createestadoPelicula(@RequestBody estadoPelicula estadoPelicula) {
        estadoPelicula newEstadoPelicula = EstadoPeliculaService.save(estadoPelicula);
        return ResponseEntity
                .created(linkTo(methodOn(EstadoPeliculaControllerV2.class).getestadoPeliculaById(newEstadoPelicula.getId())).toUri())
                .body(assembler.toModel(newEstadoPelicula));
    }

    @DeleteMapping("/estadoPelicula/{id}")
    @Operation(
        summary = "Eliminar Estado de Peliculas",
        description = "Elimina estados de las peliculas por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Estado pelicula eliminado"),
        @ApiResponse(responseCode = "404", description = "Estado pelicula no encontrado")
    })
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        EstadoPeliculaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Actualizar Estado de Peliculas",
        description = "Actualiza estados de las peliculas por Id de forma parcial"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado pelicula modificado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = estadoPelicula.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Estado pelicula no encontrado")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Estado pelicula a modificar",
        required = true
    )
    public ResponseEntity<EntityModel<estadoPelicula>> patchEstadoPelicula(@PathVariable Integer id, @RequestBody estadoPelicula partialestadoPelicula) {
    estadoPelicula existingestadoPelicula = EstadoPeliculaService.findById(id);
    if (existingestadoPelicula == null) {
        return ResponseEntity.notFound().build();
    }
    if (partialestadoPelicula.getNombreEstado() != null) {
        existingestadoPelicula.setNombreEstado(partialestadoPelicula.getNombreEstado());
    }
    estadoPelicula updatedEstadoPelicula = EstadoPeliculaService.save(existingestadoPelicula);
    return ResponseEntity.ok(assembler.toModel(updatedEstadoPelicula));
}
}
