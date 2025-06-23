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

import com.FilmHunt.cl.FilmHunt.assemblers.ListaModelAssembler;
import com.FilmHunt.cl.FilmHunt.model.Lista;
import com.FilmHunt.cl.FilmHunt.service.ListaService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/Lista")
@Tag(name = "Lista", description = "Operaciones relacionadas con las listas de películas")

public class ListaControllerV2 {

    @Autowired
    private ListaService listaService;
    @Autowired
    private ListaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Obtener Listas de películas",
        description = "Obtiene todas las listas de películas"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay listas a mostrar")
    })
    public CollectionModel<EntityModel<Lista>> getAllLista() {
        List<EntityModel<Lista>> lista = listaService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(lista,
                linkTo(methodOn(ListaControllerV2.class).getAllLista()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Obtener Listas",
        description = "Obtiene listas por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "404", description = "Lista no encontrada")
    })
    public ResponseEntity<EntityModel<Lista>> getlistaById(@PathVariable Integer id) {
        Lista lista = listaService.findById(id);
        if (lista == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(lista));
    }


    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Guardar Listas",
        description = "Guarda las listas en la base de datos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Lista agregada correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Lista.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Error al agregar lista",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = String.class)
            )
        )
    })
    //dice a swagger que el metodo espera recibir un director en el cuerpo de la peticion
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Lista a agregar", 
       required = true
    )
    public ResponseEntity<EntityModel<Lista>> createLista(@RequestBody Lista lista) {
        Lista newLista = listaService.save(lista);
        return ResponseEntity
                .created(linkTo(methodOn(ListaControllerV2.class).getlistaById(newLista.getId())).toUri())
                .body(assembler.toModel(newLista));
    }

    @DeleteMapping("/estadoPelicula/{id}")
    @Operation(
        summary = "Eliminar Listas",
        description = "Elimina listas por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Lista eliminada"),
        @ApiResponse(responseCode = "404", description = "Lista no encontrada")
    })
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        listaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Actualizar Listas",
        description = "Actualiza listas por Id de forma parcial"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista modificada correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Lista.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Lista no encontrada")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Lista a modificar",
        required = true
    )
    public ResponseEntity<EntityModel<Lista>> patchLista(@PathVariable Integer id, @RequestBody Lista partiallista) {
    Lista existinglista = listaService.findById(id);
    if (existinglista == null) {
        return ResponseEntity.notFound().build();
    }
    if (partiallista.getTituloLista() != null) {
        existinglista.setTituloLista(partiallista.getTituloLista());
    }
    Lista updatedLista = listaService.save(existinglista);
    return ResponseEntity.ok(assembler.toModel(updatedLista));
}


}
