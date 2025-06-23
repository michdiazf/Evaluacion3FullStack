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
import com.FilmHunt.cl.FilmHunt.model.directores;
import com.FilmHunt.cl.FilmHunt.model.estadoPelicula;
import com.FilmHunt.cl.FilmHunt.model.genero;
import com.FilmHunt.cl.FilmHunt.repository.PeliculaRepository;



@SpringBootTest
public class PeliculasServiceTest {

    @Autowired
    private PeliculasService peliculaService;

    @MockBean
    private PeliculaRepository peliculaRepository;


    private Peliculas createPeliculas() {
        return new Peliculas(1, "Juan_pelicula",new genero(),new directores(),"","",1950,new estadoPelicula());
    }

    @Test
    public void testFindAll(){
        when(peliculaRepository.findAll()).thenReturn(List.of(createPeliculas()));
        List<Peliculas> peliculas = peliculaService.findAll();
        assertNotNull(peliculas);
        assertEquals(1, peliculas.size());
    }

    @Test
    public void testFindById() {
        when(peliculaRepository.findById(1)).thenReturn(Optional.of(createPeliculas()));
        Peliculas peliculas = peliculaService.findById(1);
        assertNotNull(peliculas);
        assertEquals("Juan_pelicula", peliculas.getTituloPelicula());
    }

    @Test
    public void testSave() {
        Peliculas peliculas = createPeliculas();
        when(peliculaRepository.save(peliculas)).thenReturn(peliculas);
        Peliculas savedPeliculas = peliculaService.save(peliculas);
        assertNotNull(savedPeliculas);
        assertEquals("Juan_pelicula", savedPeliculas.getTituloPelicula());
    }

    @Test
    public void testPatchPelicula() {
        Peliculas existingPeliculas = createPeliculas();

        Peliculas patchData = new Peliculas();
        patchData.setTituloPelicula("Juan_pelicula");

        when(peliculaRepository.findById(1)).thenReturn(Optional.of(existingPeliculas));
        when(peliculaRepository.save(any(Peliculas.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Peliculas updatedPeliculas = peliculaService.patchPelicula(1, patchData);

        assertNotNull(updatedPeliculas);
        assertEquals("Nombre de Pelicula Actualizado", updatedPeliculas.getTituloPelicula());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(peliculaRepository).deleteById(1);
        peliculaService.deleteById(1);
        verify(peliculaRepository, times(1)).deleteById(1);
    }

}

