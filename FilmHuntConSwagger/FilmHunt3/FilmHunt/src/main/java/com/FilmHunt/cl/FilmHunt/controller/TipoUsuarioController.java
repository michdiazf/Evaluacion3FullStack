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

import com.FilmHunt.cl.FilmHunt.model.TipoUsuario;
import com.FilmHunt.cl.FilmHunt.service.TipoUsuarioService;
    
    
    @RestController
    @RequestMapping("/api/v1/TipoUsuarios")
    @Tag(name = "Tipo usuario", description = "Operaciones relacionadas con los tipos de usuario")

    public class TipoUsuarioController {
    
        @Autowired
        private TipoUsuarioService tipoUsuarioService;
    
        @GetMapping
        @Operation(
        summary = "Obtener Tipos de Usuario",
        description = "Obtiene todas los tipos de usuario"
        )
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuario obtenida correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay Tipos de usuario a mostrar")
        })
        public ResponseEntity<List<TipoUsuario>> listar(){
            List<TipoUsuario> usuario =tipoUsuarioService.findAll();
            if (usuario.isEmpty()){
                return ResponseEntity.noContent().build();
    
            }
            return ResponseEntity.ok(usuario);
        }
    
    
        @GetMapping("/{id}")
        @Operation(
        summary = "Obtener Tipos de Usuario",
        description = "Obtiene tipos de usuario por Id"
        )
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tipo de usuario obtenida correctamente"),
        @ApiResponse(responseCode = "404", description = "Tipo de usuario no encontrada")
        })
        public ResponseEntity<TipoUsuario> buscar(@PathVariable Integer id){
            try{
                TipoUsuario usuario = tipoUsuarioService.findById(id);
                return ResponseEntity.ok(usuario);
            }catch (Exception e){
                return ResponseEntity.notFound().build();
            }
        }
    
        @PostMapping
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
        public ResponseEntity<TipoUsuario> guardar(@RequestBody TipoUsuario usuario) {
            TipoUsuario usuarioNuevo = tipoUsuarioService.save(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioNuevo);
        }
        
        @PutMapping("/{id}")
        @Operation(
        summary = "Actualizar Tipos de Usuario",
        description = "Actualiza tipos de usuario por Id"
        )
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tipo de usuario obtenida correctamente",
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
        public ResponseEntity<TipoUsuario> actualizar(@PathVariable Integer id, @RequestBody TipoUsuario usuario){
            try{
                tipoUsuarioService.save(usuario);
                return ResponseEntity.ok(usuario);
            }catch( Exception e){
                return ResponseEntity.notFound().build();
            }
        }
    
        @PatchMapping("/{id}")
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
        public ResponseEntity<TipoUsuario> patchUsuario(@PathVariable Integer id, @RequestBody TipoUsuario partialUsuario) {
            try {
                TipoUsuario updatedUsuario = tipoUsuarioService.patchTipoUsuario(id, partialUsuario);
                return ResponseEntity.ok(updatedUsuario);
            } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
            }
        }
        
        @DeleteMapping("/{id}")
        @Operation(
        summary = "Eliminar Tipos de Usuario",
        description = "Elimina tipos de usuario por Id"
        )
        @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Tipo de usuario eliminado"),
        @ApiResponse(responseCode = "404", description = "Tipo de usuario no encontrado")
    })
        public ResponseEntity<?> eliminar(@PathVariable Integer id){
            try{
                tipoUsuarioService.deleteById(id);
                return ResponseEntity.noContent().build();
            }catch (Exception e){
                return ResponseEntity.notFound().build();
            }
        }
    }
    


