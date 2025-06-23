package com.FilmHunt.cl.FilmHunt.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FilmHunt.cl.FilmHunt.assemblers.ResenaModelAssembler;
import com.FilmHunt.cl.FilmHunt.model.resena;
import com.FilmHunt.cl.FilmHunt.service.ResenaService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/Resena")
@Tag(name = "Reseñas", description = "Operaciones relacionadas con las reseñas")

public class ResenaControllerV2 {

    @Autowired
    private ResenaModelAssembler assembler;

    @Autowired
    private ResenaService ResenaService;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Obtener Reseñas",
        description = "Obtiene todas las reseñas"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña obtenida correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay reseñas a mostrar")
    })
    public CollectionModel<EntityModel<resena>> getAllResenas() {
        List<EntityModel<resena>> resena = ResenaService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(resena,
                linkTo(methodOn(ResenaControllerV2.class).getAllResenas()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Obtener Reseñas",
        description = "Obtiene reseñas por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña obtenida correctamente"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    public ResponseEntity<EntityModel<resena>> getresenaById(@PathVariable Integer id) {
        resena resena = ResenaService.findById(id);
        if (resena == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(resena));
    }


    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Guardar Reseñas",
        description = "Guarda las reseñas en la base de datos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Reseña agregada correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = resena.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Error al agregar reseña",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = String.class)
            )
        )
    })
    //dice a swagger que el metodo espera recibir un director en el cuerpo de la peticion
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Reseña a agregar", 
       required = true
    )
    public ResponseEntity<EntityModel<resena>> createResena(@RequestBody resena resenas) {
        resena newResena = ResenaService.save(resenas);
        return ResponseEntity
                .created(linkTo(methodOn(ResenaControllerV2.class).getresenaById(newResena.getId())).toUri())
                .body(assembler.toModel(newResena));
    }

    @DeleteMapping("/estadoPelicula/{id}")
    @Operation(
        summary = "Eliminar Reseñas",
        description = "Elimina reseñas por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Reseña eliminada"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        ResenaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Actualizar Reseñas",
        description = "Actualiza reseñas por Id de forma parcial"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña modificada correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = resena.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Reseña a modificar",
        required = true
    )
    public ResponseEntity<EntityModel<resena>> patchResena(@PathVariable Integer id, @RequestBody resena partialResena) {
    resena existingResena = ResenaService.findById(id);
    if (existingResena == null) {
        return ResponseEntity.notFound().build();
    }
    if (partialResena.getTituloResena() != null) {
        existingResena.setTituloResena(partialResena.getTituloResena());
    }

    if (partialResena.getAno() != null) {
        existingResena.setAno(partialResena.getAno());
    }

    if (partialResena.getPuntuacionResena() != null) {
        existingResena.setPuntuacionResena(partialResena.getPuntuacionResena());
    }
            
    if (partialResena.getIdUsuario() != null && partialResena.getIdUsuario().getId() != 0) {
        existingResena.setIdUsuario(partialResena.getIdUsuario());
    }

    if (partialResena.getIdPelicula() != null) {
        existingResena.setIdPelicula(partialResena.getIdPelicula());
    }

    resena updatedresena = ResenaService.save(existingResena);
    return ResponseEntity.ok(assembler.toModel(updatedresena));
}

}
