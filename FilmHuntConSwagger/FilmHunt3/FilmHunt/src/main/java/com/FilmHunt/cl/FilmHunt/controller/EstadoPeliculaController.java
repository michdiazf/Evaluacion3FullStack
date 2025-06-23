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

import com.FilmHunt.cl.FilmHunt.model.estadoPelicula;
import com.FilmHunt.cl.FilmHunt.service.EstadoPeliculaService;
    
    
@RestController
@RequestMapping("/api/v1/EstadoPelicula")
@Tag(name = "Estado Pelicula", description = "Operaciones relacionadas con el estado de las peliculas")
public class EstadoPeliculaController {
 
    
    @Autowired
    private EstadoPeliculaService estadoPeliculaService;
    
    @GetMapping
    @Operation(
        summary = "Obtener Estado de las Peliculas",
        description = "Obtiene todas los estados de las peliculas"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado de pelicula obtenida correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay estados a mostrar")
    })
    public ResponseEntity<List<estadoPelicula>> listar(){
        List<estadoPelicula> usuario =estadoPeliculaService.findAll();
        if (usuario.isEmpty()){
            return ResponseEntity.noContent().build();
    
        }
        return ResponseEntity.ok(usuario);
    }
    
    
    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener Estado de las Peliculas",
        description = "Obtiene los estados de las peliculas por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado pelicula obtenido correctamente"),
        @ApiResponse(responseCode = "404", description = "Estado pelicula no encontrado")
    })
    public ResponseEntity<estadoPelicula> buscar(@PathVariable Integer id){
        try{
            estadoPelicula usuario = estadoPeliculaService.findById(id);
                return ResponseEntity.ok(usuario);
            }catch (Exception e){
                return ResponseEntity.notFound().build();
            }
        }
    
    @PostMapping
    @Operation(
        summary = "Guardar Estado de Peliculas",
        description = "Guarda los estados de las peliculas en la base de datos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Estado pelicula agregado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = estadoPelicula.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Error al agregar Estado pelicula",
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
    public ResponseEntity<estadoPelicula> guardar(@RequestBody estadoPelicula usuario) {
        estadoPelicula usuarioNuevo = estadoPeliculaService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioNuevo);
    }
    
    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar Estado de Peliculas",
        description = "Actualiza estados de las peliculas por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado pelicula obtenido correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = estadoPelicula.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Estado pelicula no encontrado")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Director a modificar",
        required = true
    )
    public ResponseEntity<estadoPelicula> actualizar(@PathVariable Integer id, @RequestBody estadoPelicula usuario){
        try{
            estadoPeliculaService.save(usuario);
            return ResponseEntity.ok(usuario);
        }catch( Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/{id}")
    @Operation(
        summary = "Actualizar Estado de Peliculas",
        description = "Actualiza estados de las peliculas por Id de forma parcial"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado pelicula modificado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = estadoPelicula.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Estado pelicula no encontrado")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Estado pelicula a modificar",
        required = true
    )
    public ResponseEntity<estadoPelicula> patchUsuario(@PathVariable Integer id, @RequestBody estadoPelicula partialUsuario) {
        try {
            estadoPelicula updatedUsuario = estadoPeliculaService.patchEstadoPelicula(id, partialUsuario);
            return ResponseEntity.ok(updatedUsuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
        
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar Estado de Peliculas",
        description = "Elimina estados de las peliculas por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Estado pelicula eliminado"),
        @ApiResponse(responseCode = "404", description = "Estado pelicula no encontrado")
    })
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        try{
            estadoPeliculaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
    




