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

import com.FilmHunt.cl.FilmHunt.model.directores;
import com.FilmHunt.cl.FilmHunt.repository.DirectoresRepository;


@SpringBootTest
public class DirectoresServiceTest {

    @Autowired
    private DirectoresService directoresService;

    @MockBean
    private DirectoresRepository directoresRepository;


    private directores createDirectores() {
        return new directores(1, "Juan_director");
    }

    @Test
    public void testFindAll(){
        when(directoresRepository.findAll()).thenReturn(List.of(createDirectores()));
        List<directores> directores = directoresService.findAll();
        assertNotNull(directores);
        assertEquals(1, directores.size());
    }

    @Test
    public void testFindById() {
        when(directoresRepository.findById(1)).thenReturn(Optional.of(createDirectores()));
        directores directores = directoresService.findById(1);
        assertNotNull(directores);
        assertEquals("Juan_director", directores.getNombreDirector());
    }

    @Test
    public void testSave() {
        directores directores = createDirectores();
        when(directoresRepository.save(directores)).thenReturn(directores);
        directores savedDirectores = directoresService.save(directores);
        assertNotNull(savedDirectores);
        assertEquals("Juan_director", savedDirectores.getNombreDirector());
    }

    @Test
    public void testPatchDirector() {
        directores existingDirectores = createDirectores();

        directores patchData = new directores();
        patchData.setNombreDirector("Juan_director");

        when(directoresRepository.findById(1)).thenReturn(Optional.of(existingDirectores));
        when(directoresRepository.save(any(directores.class))).thenAnswer(invocation -> invocation.getArgument(0));

        directores updatedDirectores = directoresService.patchDirectores(1, patchData);

        assertNotNull(updatedDirectores);
        assertEquals("Nombre de Genero Actualizado", updatedDirectores.getNombreDirector());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(directoresRepository).deleteById(1);
        directoresService.deleteById(1);
        verify(directoresRepository, times(1)).deleteById(1);
    }

}

