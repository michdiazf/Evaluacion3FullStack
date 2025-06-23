package com.FilmHunt.cl.FilmHunt.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FilmHunt.cl.FilmHunt.assemblers.UsuarioModelAssembler;
import com.FilmHunt.cl.FilmHunt.model.Usuario;
import com.FilmHunt.cl.FilmHunt.service.UsuarioService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/Usuario")
@Tag(name = "Usuario", description = "Operaciones relacionadas con los Usuarios")

public class UsuarioControllerV2 {

        @Autowired
    private UsuarioService UsuarioService;
    @Autowired
    private UsuarioModelAssembler assembler;

     @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
     @Operation(
        summary = "Obtener Usuario",
        description = "Obtiene todas los usuarios"
    )
    public CollectionModel<EntityModel<Usuario>> getAllUsuarios() {
        List<EntityModel<Usuario>> Usuario = UsuarioService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(Usuario,
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Obtener Usuario",
        description = "Obtiene usuario por Id"
    )
    public ResponseEntity<EntityModel<Usuario>> getUsuarioById(@PathVariable Integer id) {
        Usuario Usuario = UsuarioService.findById(id);
        if (Usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(Usuario));
    }


    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Guardar usuarios",
        description = "Guarda los usuarios en la base de datos"
    )
    public ResponseEntity<EntityModel<Usuario>> createUsuario(@RequestBody Usuario Usuario) {
        Usuario newUsuario = UsuarioService.save(Usuario);
        return ResponseEntity
                .created(linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(newUsuario.getId())).toUri())
                .body(assembler.toModel(newUsuario));
    }

    @DeleteMapping("/estadoPelicula/{id}")
    @Operation(
        summary = "Eliminar Usuario",
        description = "Elimina usuarios por Id"
    )
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        UsuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Actualizar Usuario",
        description = "Actualiza usuarios por Id de forma parcial"
    )
    public ResponseEntity<EntityModel<Usuario>> patchUsuario(@PathVariable Integer id, @RequestBody Usuario partialUsuario) {
    Usuario existingUsuario = UsuarioService.findById(id);
    if (existingUsuario == null) {
        return ResponseEntity.notFound().build();
    }
    if (partialUsuario.getNombreUsuario() != null) {
        existingUsuario.setNombreUsuario(partialUsuario.getNombreUsuario());
    }

    if (partialUsuario.getCorreo() != null) {
        existingUsuario.setCorreo(partialUsuario.getCorreo());
    }

    if (partialUsuario.getContrasena() != null) {
        existingUsuario.setContrasena(partialUsuario.getContrasena());
    }
    if (partialUsuario.getIdTipoUsuario() != null) {
        existingUsuario.setIdTipoUsuario(partialUsuario.getIdTipoUsuario());
    }

    Usuario updatedUsuario = UsuarioService.save(existingUsuario);
    return ResponseEntity.ok(assembler.toModel(updatedUsuario));
}

}
