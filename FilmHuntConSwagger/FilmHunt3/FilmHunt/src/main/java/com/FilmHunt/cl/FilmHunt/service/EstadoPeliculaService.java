package com.FilmHunt.cl.FilmHunt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FilmHunt.cl.FilmHunt.model.Peliculas;
import com.FilmHunt.cl.FilmHunt.model.estadoPelicula;
import com.FilmHunt.cl.FilmHunt.repository.EstadoPeliculaRepository;
import com.FilmHunt.cl.FilmHunt.repository.ListaRepository;
import com.FilmHunt.cl.FilmHunt.repository.PeliculaRepository;
import com.FilmHunt.cl.FilmHunt.repository.ResenaRepository;

import jakarta.transaction.Transactional;
    
@Service
@Transactional
public class EstadoPeliculaService {

    @Autowired
    private EstadoPeliculaRepository estadoPeliculaRepository;

    @Autowired
    private PeliculaRepository peliculaRepository;
    
    @Autowired
    private ResenaRepository resenaRepository;

    @Autowired
    private ListaRepository listaRepository;

    public List<estadoPelicula> findAll(){
        return estadoPeliculaRepository.findAll();
    }
    
    public estadoPelicula findById(Integer id){
        return estadoPeliculaRepository.findById(id).orElseThrow();
    }
    
    public estadoPelicula save(estadoPelicula usuario){
        return estadoPeliculaRepository.save(usuario);
    }

    public estadoPelicula create(estadoPelicula estadoPelicula) {
        return estadoPeliculaRepository.save(estadoPelicula);
    }

    public void deleteById(Integer id) {
        estadoPelicula estadoPelicula = estadoPeliculaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("estado de pelicula no encontrada"));

        List<Peliculas> peliculas = peliculaRepository.findByEstado(estadoPelicula);

        for (Peliculas pelicula : peliculas) {
            //Lista delete y resena delete
            resenaRepository.deleteByIdPelicula(pelicula);
            listaRepository.deleteByIdPelicula(pelicula);
            peliculaRepository.delete(pelicula);
        }

        estadoPeliculaRepository.deleteById(id);
    }

    
    public estadoPelicula patchEstadoPelicula(Integer id, estadoPelicula parcialEstadoPelicula){
    
        estadoPelicula usuarioToUpdate = findById(id);
                
            if (parcialEstadoPelicula.getNombreEstado() != null) {
                usuarioToUpdate.setNombreEstado(parcialEstadoPelicula.getNombreEstado());   
            }

            return estadoPeliculaRepository.save(usuarioToUpdate);
        } 
    
}
    



