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

import com.FilmHunt.cl.FilmHunt.model.genero;
import com.FilmHunt.cl.FilmHunt.service.GeneroService;
    
    
@RestController
@RequestMapping("/api/v1/Genero")
@Tag(name = "Genero", description = "Operaciones relacionadas con el genero")

public class GeneroController {
 
    
    @Autowired
    private GeneroService generoService;
    
    @GetMapping
     @Operation(
        summary = "Obtener generos",
        description = "Obtiene todas los generos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Genero obtenido correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay generos a mostrar")
    })
    public ResponseEntity<List<genero>> listar(){
        List<genero> usuario =generoService.findAll();
        if (usuario.isEmpty()){
            return ResponseEntity.noContent().build();
    
        }
        return ResponseEntity.ok(usuario);
    }
    
    
    @GetMapping("/{id}")
     @Operation(
        summary = "Obtener Genero",
        description = "Busca y obtiene un genero por su ID"
    )   
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Genero obtenido correctamente"),
        @ApiResponse(responseCode = "404", description = "Genero no encontrado")
    })
    public ResponseEntity<genero> buscar(@PathVariable Integer id){
        try{
            genero usuario = generoService.findById(id);
            return ResponseEntity.ok(usuario);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
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
    public ResponseEntity<genero> guardar(@RequestBody genero usuario) {
        genero usuarioNuevo = generoService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioNuevo);
    }
    
    @PutMapping("/{id}")
     @Operation(
        summary = "Actualizar generos",
        description = "Actualiza un genero existente por su ID, reemplazando todos sus campos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Genero obtenido correctamente",
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
    public ResponseEntity<genero> actualizar(@PathVariable Integer id, @RequestBody genero usuario){
        try{
            generoService.save(usuario);
            return ResponseEntity.ok(usuario);
        }catch( Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/{id}")
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
    public ResponseEntity<genero> patchUsuario(@PathVariable Integer id, @RequestBody genero partialUsuario) {
        try {
            genero updatedUsuario = generoService.patchGenero(id, partialUsuario);
            return ResponseEntity.ok(updatedUsuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
        
    @DeleteMapping("/{id}")
     @Operation(
        summary = "Eliminar generos",
        description = "Elimina un genero por su ID, si existe"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Genero eliminado"),
        @ApiResponse(responseCode = "404", description = "Genero no encontrado")
    })
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        try{
            generoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}

