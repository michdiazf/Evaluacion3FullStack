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

import com.FilmHunt.cl.FilmHunt.model.genero;
import com.FilmHunt.cl.FilmHunt.repository.GeneroRepository;


@SpringBootTest
public class GeneroServiceTest {

    @Autowired
    private GeneroService generoService;

    @MockBean
    private GeneroRepository generoRepository;


    private genero createGenero() {
        return new genero(1, "accion");
    }

    @Test
    public void testFindAll(){
        when(generoRepository.findAll()).thenReturn(List.of(createGenero()));
        List<genero> generos = generoService.findAll();
        assertNotNull(generos);
        assertEquals(1, generos.size());
    }

    @Test
    public void testFindById() {
        when(generoRepository.findById(1)).thenReturn(Optional.of(createGenero()));
        genero genero = generoService.findById(1);
        assertNotNull(genero);
        assertEquals("accion", genero.getNombreGenero());
    }

    @Test
    public void testSave() {
        genero genero = createGenero();
        when(generoRepository.save(genero)).thenReturn(genero);
        genero savedGenero = generoService.save(genero);
        assertNotNull(savedGenero);
        assertEquals("accion", savedGenero.getNombreGenero());
    }

    @Test
    public void testPatchGenero() {
        genero existingGenero = createGenero();

        genero patchData = new genero();
        patchData.setNombreGenero("accion");

        when(generoRepository.findById(1)).thenReturn(Optional.of(existingGenero));
        when(generoRepository.save(any(genero.class))).thenAnswer(invocation -> invocation.getArgument(0));

        genero updatedGenero = generoService.patchGenero(1, patchData);

        assertNotNull(updatedGenero);
        assertEquals("Nombre de Genero Actualizado", updatedGenero.getNombreGenero());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(generoRepository).deleteById(1);
        generoService.deleteById(1);
        verify(generoRepository, times(1)).deleteById(1);
    }

}