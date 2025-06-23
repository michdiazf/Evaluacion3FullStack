package com.FilmHunt.cl.FilmHunt.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.FilmHunt.cl.FilmHunt.model.TipoUsuario;
import com.FilmHunt.cl.FilmHunt.model.Usuario;
import com.FilmHunt.cl.FilmHunt.repository.UsuarioRepository;


@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;


    private Usuario createUsuario() {
        return new Usuario(1, "juan","Juan@Correo","Juan_clave",new TipoUsuario());
    }

    @Test
    public void testFindAll(){
        when(usuarioRepository.findAll()).thenReturn(List.of(createUsuario()));
        List<Usuario> usuarios = usuarioService.findAll();
        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
    }

    @Test
    public void testFindById() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(createUsuario()));
        Usuario usuario = usuarioService.findById(1);
        assertNotNull(usuario);
        assertEquals("juan", usuario.getNombreUsuario());
    }

    @Test
    public void testSave() {
        Usuario usuario = createUsuario();
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        Usuario savedUsuario = usuarioService.save(usuario);
        assertNotNull(savedUsuario);
        assertEquals("juan", savedUsuario.getNombreUsuario());
    }

    @Test
    public void testPatchUsuario() {
        Usuario existingUsuario = createUsuario();

        Usuario patchData = new Usuario();
        patchData.setNombreUsuario("juan");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(existingUsuario));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario updatedUsuario = usuarioService.patchUsuario(1, patchData);

        assertNotNull(updatedUsuario);
        assertEquals("Nombre de Usuario Actualizado", updatedUsuario.getNombreUsuario());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(usuarioRepository).deleteById(1);
        usuarioService.deleteById(1);
        verify(usuarioRepository, times(1)).deleteById(1);
    }

}

