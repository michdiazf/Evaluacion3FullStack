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

import com.FilmHunt.cl.FilmHunt.assemblers.PeliculasModelAssembler;
import com.FilmHunt.cl.FilmHunt.model.Peliculas;
import com.FilmHunt.cl.FilmHunt.service.PeliculasService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/Peliculas")
@Tag(name = "Peliculas", description = "Operaciones relacionadas con las Películas")
public class PeliculasControllerV2 {

    @Autowired
    private PeliculasService PeliculasService;
    @Autowired
    private PeliculasModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Obtener Películas",
        description = "Obtiene todas las películas"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pelicula obtenida correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay peliculas a mostrar")
    })
    public CollectionModel<EntityModel<Peliculas>> getAllPeliculas() {
        List<EntityModel<Peliculas>> lista = PeliculasService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(lista,
                linkTo(methodOn(PeliculasControllerV2.class).getAllPeliculas()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Obtener Peliculas",
        description = "Obtiene peliculas por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pelicula obtenida correctamente"),
        @ApiResponse(responseCode = "404", description = "Pelicula no encontrada")
    })
    public ResponseEntity<EntityModel<Peliculas>> getpeliculasById(@PathVariable Integer id) {
        Peliculas pelicula = PeliculasService.findById(id);
        if (pelicula == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(pelicula));
    }


    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Guardar Peliculas",
        description = "Guarda las peliculas en la base de datos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pelicula agregada correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Peliculas.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Error al agregar pelicula",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = String.class)
            )
        )
    })
    //dice a swagger que el metodo espera recibir un director en el cuerpo de la peticion
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Pelicula a agregar", 
       required = true
    )
    public ResponseEntity<EntityModel<Peliculas>> createPelicula(@RequestBody Peliculas pelicula) {
        Peliculas newPeliculas = PeliculasService.save(pelicula);
        return ResponseEntity
                .created(linkTo(methodOn(PeliculasControllerV2.class).getpeliculasById(newPeliculas.getId())).toUri())
                .body(assembler.toModel(newPeliculas));
    }

    @DeleteMapping("/estadoPelicula/{id}")
    @Operation(
        summary = "Eliminar Peliculas",
        description = "Elimina peliculas por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pelicula eliminada"),
        @ApiResponse(responseCode = "404", description = "Pelicula no encontrada")
    })
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        PeliculasService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Actualizar Peliculas",
        description = "Actualiza peliculas por Id de forma parcial"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pelicula modificada correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Peliculas.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Pelicula no encontrada")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Pelicula a modificar",
        required = true
    )
    public ResponseEntity<EntityModel<Peliculas>> patchPeliculas(@PathVariable Integer id, @RequestBody Peliculas partialPeliculas) {
    Peliculas existingPeliculas = PeliculasService.findById(id);
    if (existingPeliculas == null) {
        return ResponseEntity.notFound().build();
    }
    if (partialPeliculas.getTituloPelicula() != null) {
        existingPeliculas.setTituloPelicula(partialPeliculas.getTituloPelicula());
    }

    if (partialPeliculas.getPuntuacion() != null) {
        existingPeliculas.setPuntuacion(partialPeliculas.getPuntuacion());
    }

    if (partialPeliculas.getDuracion() != null) {
        existingPeliculas.setDuracion(partialPeliculas.getDuracion());
    }
            
    if (partialPeliculas.getAno() != 0) {
        existingPeliculas.setAno(partialPeliculas.getAno());
    }

    Peliculas updatedPeliculas = PeliculasService.save(existingPeliculas);
    return ResponseEntity.ok(assembler.toModel(updatedPeliculas));
}

}
