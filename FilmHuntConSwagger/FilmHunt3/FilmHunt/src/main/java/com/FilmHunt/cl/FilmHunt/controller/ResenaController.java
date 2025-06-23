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

import com.FilmHunt.cl.FilmHunt.model.resena;
import com.FilmHunt.cl.FilmHunt.service.ResenaService;

@RestController
@RequestMapping("/api/v1/Resena")
@Tag(name = "Reseñas", description = "Operaciones relacionadas con las reseñas")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    @GetMapping
    @Operation(
        summary = "Obtener Reseñas",
        description = "Obtiene todas las reseñas"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña obtenida correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay reseñas a mostrar")
    })
    public ResponseEntity <List<resena>> listar(){
        List<resena> resena =resenaService.findAll();
        if (resena.isEmpty()){
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.ok(resena);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener Reseñas",
        description = "Obtiene reseñas por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña obtenida correctamente"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    public ResponseEntity<resena> buscar(@PathVariable Integer id){
        try{
            resena resena = resenaService.findById(id);
            return ResponseEntity.ok(resena);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
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
    public ResponseEntity<resena> guardar(@RequestBody resena resena) {
        resena resenaNuevo = resenaService.save(resena);
        return ResponseEntity.status(HttpStatus.CREATED).body(resenaNuevo);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar reseñas",
        description = "Actualiza reseñas por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña obtenida correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = resena.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrado")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Reseña a modificar",
        required = true
    )
    public ResponseEntity<resena> actualizar(@PathVariable Integer id, @RequestBody resena resena){
        try{
            resenaService.save(resena);
            return ResponseEntity.ok(resena);
        }catch( Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
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
    public ResponseEntity<resena> patchUsuario(@PathVariable Integer id, @RequestBody resena partialresena) {
        try {
            resena updatedrResena = resenaService.patchResena(id, partialresena);
            return ResponseEntity.ok(updatedrResena);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar Reseñas",
        description = "Elimina reseñas por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Reseña eliminada"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        try{
            resenaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
