package com.FilmHunt.cl.FilmHunt.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.FilmHunt.cl.FilmHunt.assemblers.GeneroModelAssembler;
import com.FilmHunt.cl.FilmHunt.model.genero;
import com.FilmHunt.cl.FilmHunt.service.GeneroService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/Genero")
@Tag(name = "Genero", description = "Operaciones relacionadas con el genero")

public class GeneroControllerV2 {

    @Autowired
    private GeneroService GeneroService;
    @Autowired
    private GeneroModelAssembler assembler;


    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
      @Operation(
        summary = "Obtener generos",
        description = "Obtiene todas los generos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Genero obtenido correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay generos a mostrar")
    })
    public CollectionModel<EntityModel<genero>> getAllGenero() {
        List<EntityModel<genero>> genero = GeneroService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(genero,
                linkTo(methodOn(GeneroControllerV2.class).getAllGenero()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(
        summary = "Obtener generos",
        description = "Busca y obtiene un genero por su ID"
    )  
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Genero obtenido correctamente"),
        @ApiResponse(responseCode = "404", description = "Genero no encontrado")
    })
    public ResponseEntity<EntityModel<genero>> getgeneroById(@PathVariable Integer id) {
        genero genero = GeneroService.findById(id);
        if (genero == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(genero));
    }


    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Guardar generos",
        description = "Agrega generos a la base de datos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Genero agregado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = genero.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Error al agregar genero",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = String.class)
            )
        )
    })
    //dice a swagger que el metodo espera recibir un director en el cuerpo de la peticion
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Genero a agregar", 
       required = true
    )
    public ResponseEntity<EntityModel<genero>> createGenero(@RequestBody genero genero) {
        genero newGenero = GeneroService.save(genero);
        return ResponseEntity
                .created(linkTo(methodOn(GeneroControllerV2.class).getgeneroById(newGenero.getId())).toUri())
                .body(assembler.toModel(newGenero));
    }

    @DeleteMapping("/estadoPelicula/{id}")
     @Operation(
        summary = "Eliminar generos",
        description = "Elimina un genero por su ID, si existe"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Genero eliminado"),
        @ApiResponse(responseCode = "404", description = "Genero no encontrado")
    })
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        GeneroService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Actualizar generos parcialmente",
        description = "Actualiza parcialmente un genero existente por su ID, modificando solo algunos campos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Genero modificado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = genero.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Genero no encontrado")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Genero a modificar",
        required = true
    )
    public ResponseEntity<EntityModel<genero>> patchGenero(@PathVariable Integer id, @RequestBody genero partialgenero) {
    genero existinggenero = GeneroService.findById(id);
    if (existinggenero == null) {
        return ResponseEntity.notFound().build();
    }
    if (partialgenero.getNombreGenero() != null) {
        existinggenero.setNombreGenero(partialgenero.getNombreGenero());
    }
    genero updatedGenero = GeneroService.save(existinggenero);
    return ResponseEntity.ok(assembler.toModel(updatedGenero));
}

}
