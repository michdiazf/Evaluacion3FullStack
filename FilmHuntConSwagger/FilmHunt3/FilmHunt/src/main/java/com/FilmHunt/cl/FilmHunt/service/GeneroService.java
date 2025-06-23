package com.FilmHunt.cl.FilmHunt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FilmHunt.cl.FilmHunt.model.Peliculas;
import com.FilmHunt.cl.FilmHunt.model.genero;
import com.FilmHunt.cl.FilmHunt.repository.GeneroRepository;
import com.FilmHunt.cl.FilmHunt.repository.ListaRepository;
import com.FilmHunt.cl.FilmHunt.repository.PeliculaRepository;
import com.FilmHunt.cl.FilmHunt.repository.ResenaRepository;

import jakarta.transaction.Transactional;
    
@Service
@Transactional
public class GeneroService {

    @Autowired
    private GeneroRepository generoRepository;
    
    @Autowired
    private PeliculaRepository peliculaRepository;
    
    @Autowired
    private ResenaRepository resenaRepository;

    @Autowired
    private ListaRepository listaRepository;

    public List<genero> findAll(){
        return generoRepository.findAll();
    }
    
    public genero findById(Integer id){
        return generoRepository.findById(id).orElseThrow();
    }
    
    public genero save(genero usuario){
        return generoRepository.save(usuario);
    }

        public genero create(genero genero) {
        return generoRepository.save(genero);
    }

    
    public void deleteById(Integer id) {
        genero genero = generoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("genero no encontrada"));

        List<Peliculas> peliculas = peliculaRepository.findByGenero(genero);

        for (Peliculas pelicula : peliculas) {
            //Lista delete y resena delete
            resenaRepository.deleteByIdPelicula(pelicula);
            listaRepository.deleteByIdPelicula(pelicula);
            peliculaRepository.delete(pelicula);
        }

        generoRepository.deleteById(id);
    }


    
    public genero patchGenero(Integer id, genero parcialGenero){
    
        genero usuarioToUpdate = findById(id);
                
            if (parcialGenero.getNombreGenero() != null) {
                usuarioToUpdate.setNombreGenero(parcialGenero.getNombreGenero());   
            }

            return generoRepository.save(usuarioToUpdate);
        } 
    
}
    

