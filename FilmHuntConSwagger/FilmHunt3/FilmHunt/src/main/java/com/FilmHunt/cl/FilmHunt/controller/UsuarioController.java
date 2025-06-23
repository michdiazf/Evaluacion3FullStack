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

import com.FilmHunt.cl.FilmHunt.model.Usuario;
import com.FilmHunt.cl.FilmHunt.service.UsuarioService;


@RestController
@RequestMapping("/api/v1/Usuarios")
@Tag(name = "Usuario", description = "Operaciones relacionadas con los Usuarios")

public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(
        summary = "Obtener Usuario",
        description = "Obtiene todas los usuarios"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario obtenida correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay usuario a mostrar")
        })
    public ResponseEntity<List<Usuario>> listar(){
        List<Usuario> usuario =usuarioService.findAll();
        if (usuario.isEmpty()){
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.ok(usuario);
    }


    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener Usuario",
        description = "Obtiene usuario por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario obtenido correctamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        })
    public ResponseEntity<Usuario> buscar(@PathVariable Integer id){
        try{
            Usuario usuario = usuarioService.findById(id);
            return ResponseEntity.ok(usuario);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(
        summary = "Guardar usuarios",
        description = "Guarda los usuarios en la base de datos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario agregada correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Usuario.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Error al agregar usuario",
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
    public ResponseEntity<Usuario> guardar(@RequestBody Usuario usuario) {
        Usuario usuarioNuevo = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioNuevo);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar Usuario",
        description = "Actualiza usuarios por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario obtenido correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Usuario.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        })
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Usuario a modificar",
        required = true
        )
    public ResponseEntity<Usuario> actualizar(@PathVariable Integer id, @RequestBody Usuario usuario){
        try{
            usuarioService.save(usuario);
            return ResponseEntity.ok(usuario);
        }catch( Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(
        summary = "Actualizar Usuario",
        description = "Actualiza usuarios por Id de forma parcial"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario modificada correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Usuario.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        })
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Usuario a modificar",
        required = true
        )
    public ResponseEntity<Usuario> patchUsuario(@PathVariable Integer id, @RequestBody Usuario partialUsuario) {
        try {
            Usuario updatedUsuario = usuarioService.patchUsuario(id, partialUsuario);
            return ResponseEntity.ok(updatedUsuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar Usuario",
        description = "Elimina usuarios por Id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        try{
            usuarioService.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}


