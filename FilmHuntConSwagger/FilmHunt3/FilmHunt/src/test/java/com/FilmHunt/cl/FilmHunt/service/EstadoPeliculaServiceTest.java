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

import com.FilmHunt.cl.FilmHunt.model.estadoPelicula;
import com.FilmHunt.cl.FilmHunt.repository.EstadoPeliculaRepository;


@SpringBootTest
public class EstadoPeliculaServiceTest {

    @Autowired
    private EstadoPeliculaService estadoPeliculaService;

    @MockBean
    private EstadoPeliculaRepository estadoPeliculaRepository;


    private estadoPelicula createEstadoPelicula() {
        return new estadoPelicula(1, "accion");
    }

    @Test
    public void testFindAll(){
        when(estadoPeliculaRepository.findAll()).thenReturn(List.of(createEstadoPelicula()));
        List<estadoPelicula> estadoPelicula = estadoPeliculaService.findAll();
        assertNotNull(estadoPelicula);
        assertEquals(1, estadoPelicula.size());
    }

    @Test
    public void testFindById() {
        when(estadoPeliculaRepository.findById(1)).thenReturn(Optional.of(createEstadoPelicula()));
        estadoPelicula estadoPelicula = estadoPeliculaService.findById(1);
        assertNotNull(estadoPelicula);
        assertEquals("accion", estadoPelicula.getNombreEstado());
    }

    @Test
    public void testSave() {
        estadoPelicula estadoPelicula = createEstadoPelicula();
        when(estadoPeliculaRepository.save(estadoPelicula)).thenReturn(estadoPelicula);
        estadoPelicula savedEstadoPelicula = estadoPeliculaService.save(estadoPelicula);
        assertNotNull(savedEstadoPelicula);
        assertEquals("accion", savedEstadoPelicula.getNombreEstado());
    }

    @Test
    public void testpatchEstadoPelicula() {
        estadoPelicula existingEstadoPelicula = createEstadoPelicula();

        estadoPelicula patchData = new estadoPelicula();
        patchData.setNombreEstado("accion");

        when(estadoPeliculaRepository.findById(1)).thenReturn(Optional.of(existingEstadoPelicula));
        when(estadoPeliculaRepository.save(any(estadoPelicula.class))).thenAnswer(invocation -> invocation.getArgument(0));

        estadoPelicula updatedEstadoPelicula = estadoPeliculaService.patchEstadoPelicula(1, patchData);

        assertNotNull(updatedEstadoPelicula);
        assertEquals("estado de pelicula Actualizado", updatedEstadoPelicula.getNombreEstado());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(estadoPeliculaRepository).deleteById(1);
        estadoPeliculaService.deleteById(1);
        verify(estadoPeliculaRepository, times(1)).deleteById(1);
    }

}

