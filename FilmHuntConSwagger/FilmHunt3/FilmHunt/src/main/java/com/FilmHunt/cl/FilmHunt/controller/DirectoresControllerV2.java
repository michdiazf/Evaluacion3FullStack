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

import com.FilmHunt.cl.FilmHunt.assemblers.DirectoresModelAssembler;
import com.FilmHunt.cl.FilmHunt.model.directores;
import com.FilmHunt.cl.FilmHunt.service.DirectoresService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/Directores")
@Tag(name = "Directores", description = "Operaciones relacionadas con directores")
public class DirectoresControllerV2 {
    @Autowired
    private DirectoresService directoresService;
    @Autowired
    private DirectoresModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Obtener directores",
        description = "Obtiene todas los directores"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Directores obtenidas correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay directores a mostrar")
    })
    public CollectionModel<EntityModel<directores>> getAllDirectores() {
        List<EntityModel<directores>> directores = directoresService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(directores,
                linkTo(methodOn(DirectoresControllerV2.class).getAllDirectores()).withSelfRel());
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Guardar directores",
        description = "Guarda los directores en la base de datos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Director agregado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = directores.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Error al agregar director",
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
    public ResponseEntity<EntityModel<directores>> createDirectores(@RequestBody directores director) {
        directores newDirectores = directoresService.save(director);
        return ResponseEntity
                .created(linkTo(methodOn(DirectoresControllerV2.class).getdirectoresById(newDirectores.getId())).toUri())
                .body(assembler.toModel(newDirectores));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Actualizar directores",
        description = "Actualiza un director existente por su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Director obtenido correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = directores.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Director no encontrado")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Director a modificar",
        required = true
    )
    public ResponseEntity<EntityModel<directores>> updateDirectores(@PathVariable Integer id, @RequestBody directores director) {
        director.setId(id);
        directores updatedDirectores = directoresService.save(director);
        return ResponseEntity
                .ok(assembler.toModel(updatedDirectores));
    }

    @DeleteMapping("/directores/{id}")
    @Operation(
        summary = "Eliminar directores",
        description = "Eliminar un director existente por su Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Director eliminado"),
        @ApiResponse(responseCode = "404", description = "Director no encontrado")
    })
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        directoresService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Obtener directores",
        description = "Obtiene directores por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Director obtenido correctamente"),
        @ApiResponse(responseCode = "404", description = "Director no encontrado")
    })
    public ResponseEntity<EntityModel<directores>> getdirectoresById(@PathVariable Integer id) {
        directores director = directoresService.findById(id);
        if (director == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(director));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Actualizar directores",
        description = "Actualiza un director existente por su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Director modificado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = directores.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Director no encontrado")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Director a modificar",
        required = true
    )
    public ResponseEntity<EntityModel<directores>> patchDirectores(@PathVariable Integer id, @RequestBody directores partialDirector) {
    directores existingDirector = directoresService.findById(id);
    if (existingDirector == null) {
        return ResponseEntity.notFound().build();
    }
    if (partialDirector.getNombreDirector() != null) {
        existingDirector.setNombreDirector(partialDirector.getNombreDirector());
    }
    directores updatedDirector = directoresService.save(existingDirector);
    return ResponseEntity.ok(assembler.toModel(updatedDirector));
}

}
