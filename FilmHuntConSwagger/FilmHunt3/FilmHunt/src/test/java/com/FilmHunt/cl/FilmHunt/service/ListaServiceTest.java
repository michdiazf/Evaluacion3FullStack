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

import com.FilmHunt.cl.FilmHunt.model.Lista;
import com.FilmHunt.cl.FilmHunt.model.Peliculas;
import com.FilmHunt.cl.FilmHunt.model.Usuario;
import com.FilmHunt.cl.FilmHunt.repository.ListaRepository;


@SpringBootTest
public class ListaServiceTest {

    @Autowired
    private ListaService listaService;

    @MockBean
    private ListaRepository listaRepository;


    private Lista createLista() {
        return new Lista(1, "accion", new Peliculas(),new Usuario());
    }

    @Test
    public void testFindAll(){
        when(listaRepository.findAll()).thenReturn(List.of(createLista()));
        List<Lista> lista = listaService.findAll();
        assertNotNull(lista);
        assertEquals(1, lista.size());
    }

    @Test
    public void testFindById() {
        when(listaRepository.findById(1)).thenReturn(Optional.of(createLista()));
        Lista lista = listaService.findById(1);
        assertNotNull(lista);
        assertEquals("accion", lista.getTituloLista());
    }

    @Test
    public void testSave() {
        Lista lista = createLista();
        when(listaRepository.save(lista)).thenReturn(lista);
        Lista savedLista = listaService.save(lista);
        assertNotNull(savedLista);
        assertEquals("accion", savedLista.getTituloLista());
    }

    @Test
    public void testPatchLista() {
        Lista existingLista = createLista();

        Lista patchData = new Lista();
        patchData.setTituloLista("accion");

        when(listaRepository.findById(1)).thenReturn(Optional.of(existingLista));
        when(listaRepository.save(any(Lista.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Lista updatedLista = listaService.patchLista(1, patchData);

        assertNotNull(updatedLista);
        assertEquals("Nombre de lista Actualizado", updatedLista.getTituloLista());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(listaRepository).deleteById(1);
        listaService.deleteById(1);
        verify(listaRepository, times(1)).deleteById(1);
    }

}

