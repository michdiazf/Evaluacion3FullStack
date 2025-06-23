package com.FilmHunt.cl.FilmHunt.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.FilmHunt.cl.FilmHunt.assemblers.TipoUsuarioModelAssembler;

import com.FilmHunt.cl.FilmHunt.model.TipoUsuario;
import com.FilmHunt.cl.FilmHunt.service.TipoUsuarioService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/TipoUsuario")
@Tag(name = "Tipo usuario", description = "Operaciones relacionadas con los tipos de usuario")

public class TipoUsuarioControllerV2 {

        @Autowired
    private TipoUsuarioService TipoUsuarioService;
    @Autowired
    private TipoUsuarioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Obtener Tipos de Usuario",
        description = "Obtiene todas los tipos de usuario"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuario obtenida correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay Tipos de usuario a mostrar")
        })
    public CollectionModel<EntityModel<TipoUsuario>> getAllTipoUsuario() {
        List<EntityModel<TipoUsuario>> TipoUsuario = TipoUsuarioService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(TipoUsuario,
                linkTo(methodOn(TipoUsuarioControllerV2.class).getAllTipoUsuario()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Obtener Tipos de Usuario",
        description = "Obtiene tipos de usuario por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tipo de usuario obtenida correctamente"),
        @ApiResponse(responseCode = "404", description = "Tipo de usuario no encontrada")
        })
    public ResponseEntity<EntityModel<TipoUsuario>> getTipoUsuarioById(@PathVariable Integer id) {
        TipoUsuario TipoUsuario = TipoUsuarioService.findById(id);
        if (TipoUsuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(TipoUsuario));
    }


    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Guardar Tipos de usuarios",
        description = "Guarda los tipos de usuario en la base de datos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Tipo de usuario agregada correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = TipoUsuario.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Error al agregar Tipo de usuario",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = String.class)
            )
        )
        })
        //dice a swagger que el metodo espera recibir un director en el cuerpo de la peticion
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Tipo de usuario a agregar", 
        required = true
        )
    public ResponseEntity<EntityModel<TipoUsuario>> createTipoUsuario(@RequestBody TipoUsuario TipoUsuario) {
        TipoUsuario newTipoUsuario = TipoUsuarioService.save(TipoUsuario);
        return ResponseEntity
                .created(linkTo(methodOn(TipoUsuarioControllerV2.class).getTipoUsuarioById(newTipoUsuario.getIdTipoUsuario())).toUri())
                .body(assembler.toModel(newTipoUsuario));
    }

    @DeleteMapping("/estadoPelicula/{id}")
    @Operation(
        summary = "Eliminar Tipos de Usuario",
        description = "Elimina tipos de usuario por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Tipo de usuario eliminado"),
        @ApiResponse(responseCode = "404", description = "Tipo de usuario no encontrado")
    })
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        TipoUsuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Actualizar Tipos de Usuario",
        description = "Actualiza tipos de usuario por Id de forma parcial"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tipo de usuario modificada correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = TipoUsuario.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Tipo de usuario no encontrado")
        })
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Tipo de usuario a modificar",
        required = true
        )
    public ResponseEntity<EntityModel<TipoUsuario>> patchTipoUsuario(@PathVariable Integer id, @RequestBody TipoUsuario partialTipoUsuario) {
    TipoUsuario existingTipoUsuario = TipoUsuarioService.findById(id);
    if (existingTipoUsuario == null) {
        return ResponseEntity.notFound().build();
    }
    if (partialTipoUsuario.getTipoUsuario() != null) {
        existingTipoUsuario.setTipoUsuario(partialTipoUsuario.getTipoUsuario());
    }
    TipoUsuario updatedTipoUsuario = TipoUsuarioService.save(existingTipoUsuario);
    return ResponseEntity.ok(assembler.toModel(updatedTipoUsuario));
}
}
