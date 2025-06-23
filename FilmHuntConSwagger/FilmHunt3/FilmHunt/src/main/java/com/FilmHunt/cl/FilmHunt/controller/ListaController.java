package com.FilmHunt.cl.FilmHunt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.FilmHunt.cl.FilmHunt.model.Lista;
import com.FilmHunt.cl.FilmHunt.service.ListaService;


@RestController
@RequestMapping("/api/v1/Lista")
@Tag(name = "Lista", description = "Operaciones relacionadas con las listas de películas")
public class ListaController {

    @Autowired
    private ListaService listaService;

    @GetMapping
    @Operation(
        summary = "Obtener Listas de películas",
        description = "Obtiene todas las listas de películas"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay listas a mostrar")
    })
    public ResponseEntity <List<Lista>> listar(){
        List<Lista> lista =listaService.findAll();
        if (lista.isEmpty()){
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.ok(lista);
    }
    
    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener Listas",
        description = "Obtiene listas por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "404", description = "Lista no encontrada")
    })
    public ResponseEntity<Lista> buscar(@PathVariable Integer id){
        try{
            Lista lista = listaService.findById(id);
            return ResponseEntity.ok(lista);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
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
    public ResponseEntity<Lista> guardar(@RequestBody Lista lista) {
        Lista listaNuevo = listaService.save(lista);
        return ResponseEntity.status(HttpStatus.CREATED).body(listaNuevo);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar listas",
        description = "Actualiza listas por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenido correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Lista.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Lista no encontrado")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Lista a modificar",
        required = true
    )
    public ResponseEntity<Lista> actualizar(@PathVariable Integer id, @RequestBody Lista lista){
        try{
            listaService.save(lista);
            return ResponseEntity.ok(lista);
        }catch( Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
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
    public ResponseEntity<Lista> patchLista(@PathVariable Integer id, @RequestBody Lista partialLista) {
        try {
            Lista updatedLista = listaService.patchLista(id, partialLista);
            return ResponseEntity.ok(updatedLista);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar Listas",
        description = "Elimina listas por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Lista eliminada"),
        @ApiResponse(responseCode = "404", description = "Lista no encontrada")
    })
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        try{
            listaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
