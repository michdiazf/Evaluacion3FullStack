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

import com.FilmHunt.cl.FilmHunt.model.Peliculas;
import com.FilmHunt.cl.FilmHunt.model.Usuario;
import com.FilmHunt.cl.FilmHunt.model.resena;
import com.FilmHunt.cl.FilmHunt.repository.ResenaRepository;


@SpringBootTest
public class ResenaServiceTest {

    @Autowired
    private ResenaService resenaService;

    @MockBean
    private ResenaRepository resenaRepository;


    private resena createResena() {
        return new resena(1, "resena_buena",2025,"90", new Usuario(), new Peliculas());
    }

    @Test
    public void testFindAll(){
        when(resenaRepository.findAll()).thenReturn(List.of(createResena()));
        List<resena> resena = resenaService.findAll();
        assertNotNull(resena);
        assertEquals(1, resena.size());
    }

    @Test
    public void testFindById() {
        when(resenaRepository.findById(1)).thenReturn(Optional.of(createResena()));
        resena resena = resenaService.findById(1);
        assertNotNull(resena);
        assertEquals("resena_buena", resena.getTituloResena());
    }

    @Test
    public void testSave() {
        resena resena = createResena();
        when(resenaRepository.save(resena)).thenReturn(resena);
        resena savedResena = resenaService.save(resena);
        assertNotNull(savedResena);
        assertEquals("resena_buena", savedResena.getTituloResena());
    }

    @Test
    public void testPatchResena() {
        resena existingResena = createResena();

        resena patchData = new resena();
        patchData.setTituloResena("resena_buena");

        when(resenaRepository.findById(1)).thenReturn(Optional.of(existingResena));
        when(resenaRepository.save(any(resena.class))).thenAnswer(invocation -> invocation.getArgument(0));

        resena updatedResena = resenaService.patchResena(1, patchData);

        assertNotNull(updatedResena);
        assertEquals("Nombre de resena Actualizado", updatedResena.getTituloResena());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(resenaRepository).deleteById(1);
        resenaService.deleteById(1);
        verify(resenaRepository, times(1)).deleteById(1);
    }

}