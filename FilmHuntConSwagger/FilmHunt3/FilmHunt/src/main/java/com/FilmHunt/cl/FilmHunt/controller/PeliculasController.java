package com.FilmHunt.cl.FilmHunt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FilmHunt.cl.FilmHunt.model.Peliculas;
import com.FilmHunt.cl.FilmHunt.service.PeliculasService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/Peliculas")
@Tag(name = "Peliculas", description = "Operaciones relacionadas con las Películas")

public class PeliculasController {

    @Autowired
    private PeliculasService peliculaService;

    @GetMapping
    @Operation(
        summary = "Obtener Películas",
        description = "Obtiene todas las películas"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pelicula obtenida correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay peliculas a mostrar")
    })
    public ResponseEntity <List<Peliculas>> listar(){
        List<Peliculas> pelicula =peliculaService.findAll();
        if (pelicula.isEmpty()){
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.ok(pelicula);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener Peliculas",
        description = "Obtiene peliculas por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pelicula obtenida correctamente"),
        @ApiResponse(responseCode = "404", description = "Pelicula no encontrada")
    })
    public ResponseEntity<Peliculas> buscar(@PathVariable Integer id){
        try{
            Peliculas pelicula = peliculaService.findById(id);
            return ResponseEntity.ok(pelicula);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
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
    public ResponseEntity<Peliculas> guardar(@RequestBody Peliculas pelicula) {
        Peliculas peliculaNuevo = peliculaService.save(pelicula);
        return ResponseEntity.status(HttpStatus.CREATED).body(peliculaNuevo);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar peliculas",
        description = "Actualiza peliculas por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pelicula obtenida correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Peliculas.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Pelicula no encontrado")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Pelicula a modificar",
        required = true
    )
    public ResponseEntity<Peliculas> actualizar(@PathVariable Integer id, @RequestBody Peliculas pelicula){
        try{
            peliculaService.save(pelicula);
            return ResponseEntity.ok(pelicula);
        }catch( Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
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
    public ResponseEntity<Peliculas> patchPelicula(@PathVariable Integer id, @RequestBody Peliculas partialPelicula) {
        try {
            Peliculas updatedPeliculas = peliculaService.patchPelicula(id, partialPelicula);
            return ResponseEntity.ok(updatedPeliculas);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar Peliculas",
        description = "Elimina peliculas por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pelicula eliminada"),
        @ApiResponse(responseCode = "404", description = "Pelicula no encontrada")
    })
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        try{
            peliculaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }




}
