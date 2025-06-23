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

import com.FilmHunt.cl.FilmHunt.model.directores;
import com.FilmHunt.cl.FilmHunt.service.DirectoresService;
    
    
@RestController
@RequestMapping("/api/v1/Directores")
@Tag(name = "Directores", description = "Operaciones relacionadas con directores")
public class DirectoresController {
 
    
    @Autowired
    private DirectoresService directoresService;
    
    @GetMapping
    @Operation(
        summary = "Obtener directores",
        description = "Obtiene todas los directores"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Directores obtenidas correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay directores a mostrar")
    })
    public ResponseEntity<List<directores>> listar(){
        List<directores> usuario =directoresService.findAll();
        if (usuario.isEmpty()){
        return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuario);
    }
    
    
    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener directores",
        description = "Obtiene directores por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Director obtenido correctamente"),
        @ApiResponse(responseCode = "404", description = "Director no encontrado")
    })
    public ResponseEntity<directores> buscar(@PathVariable Integer id){
        try{
            directores usuario = directoresService.findById(id);
            return ResponseEntity.ok(usuario);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
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
    public ResponseEntity<directores> guardar(@RequestBody directores usuario) {
        directores usuarioNuevo = directoresService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioNuevo);
        }
    
    @PutMapping("/{id}")
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
    public ResponseEntity<directores> actualizar(@PathVariable Integer id, @RequestBody directores usuario){
        try{
            directoresService.save(usuario);
            return ResponseEntity.ok(usuario);
        }catch( Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/{id}")
    @Operation(
        summary = "Actualizar directores",
        description = "Actualiza parcialmente un director por Id, si este existe"
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
    public ResponseEntity<directores> patchUsuario(@PathVariable Integer id, @RequestBody directores partialUsuario) {
        try {
            directores updatedUsuario = directoresService.patchDirectores(id, partialUsuario);
            return ResponseEntity.ok(updatedUsuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
        
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar directores",
        description = "Eliminar un director existente por su Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Director eliminado"),
        @ApiResponse(responseCode = "404", description = "Director no encontrado")
    })
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        try{
            directoresService.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}

