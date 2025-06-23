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
import com.FilmHunt.cl.FilmHunt.repository.TipoUsuarioRepository;


@SpringBootTest
public class TipoUsuarioServiceTest {

    @Autowired
    private TipoUsuarioService tipoUsuarioService;

    @MockBean
    private TipoUsuarioRepository tipoUsuarioRepository;


    private TipoUsuario createTipoUsuario() {
        return new TipoUsuario(1, "Admin");
    }

    @Test
    public void testFindAll(){
        when(tipoUsuarioRepository.findAll()).thenReturn(List.of(createTipoUsuario()));
        List<TipoUsuario> tipos = tipoUsuarioService.findAll();
        assertNotNull(tipos);
        assertEquals(1, tipos.size());
    }

    @Test
    public void testFindById() {
        when(tipoUsuarioRepository.findById(1)).thenReturn(Optional.of(createTipoUsuario()));
        TipoUsuario tipoUsuario = tipoUsuarioService.findById(1);
        assertNotNull(tipoUsuario);
        assertEquals("Admin", tipoUsuario.getTipoUsuario());
    }

    @Test
    public void testSave() {
        TipoUsuario tipoUsuario = createTipoUsuario();
        when(tipoUsuarioRepository.save(tipoUsuario)).thenReturn(tipoUsuario);
        TipoUsuario savedTipoUsuario = tipoUsuarioService.save(tipoUsuario);
        assertNotNull(savedTipoUsuario);
        assertEquals("Admin", savedTipoUsuario.getTipoUsuario());
    }

    @Test
    public void testPatchTipoUsuario() {
        TipoUsuario existingTipoUsuario = createTipoUsuario();

        TipoUsuario patchData = new TipoUsuario();
        patchData.setTipoUsuario("Admin");

        when(tipoUsuarioRepository.findById(1)).thenReturn(Optional.of(existingTipoUsuario));
        when(tipoUsuarioRepository.save(any(TipoUsuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TipoUsuario updatedTipoUsuario = tipoUsuarioService.patchTipoUsuario(1, patchData);

        assertNotNull(updatedTipoUsuario);
        assertEquals("Tipo de Usuario Actualizado", updatedTipoUsuario.getTipoUsuario());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(tipoUsuarioRepository).deleteById(1);
        tipoUsuarioService.deleteById(1);
        verify(tipoUsuarioRepository, times(1)).deleteById(1);
    }

}
