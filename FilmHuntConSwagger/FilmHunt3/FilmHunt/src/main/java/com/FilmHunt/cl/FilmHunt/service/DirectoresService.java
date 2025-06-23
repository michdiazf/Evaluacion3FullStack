package com.FilmHunt.cl.FilmHunt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FilmHunt.cl.FilmHunt.model.Peliculas;

import com.FilmHunt.cl.FilmHunt.model.directores;
import com.FilmHunt.cl.FilmHunt.repository.DirectoresRepository;
import com.FilmHunt.cl.FilmHunt.repository.PeliculaRepository;
import com.FilmHunt.cl.FilmHunt.repository.ResenaRepository;
import com.FilmHunt.cl.FilmHunt.repository.ListaRepository;

import jakarta.transaction.Transactional;
    
@Service
@Transactional
public class DirectoresService {

    @Autowired
    private DirectoresRepository directoresRepository;

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private ResenaRepository resenaRepository;

    @Autowired
    private ListaRepository listaRepository;

    public List<directores> findAll(){
        return directoresRepository.findAll();
    }
    
    public directores findById(Integer id){
        return directoresRepository.findById(id).orElseThrow();
    }

    public directores save(directores usuario){
        return directoresRepository.save(usuario);
    }

    
        public directores create(directores directores) {
        return directoresRepository.save(directores);
    }

    
    public void deleteById(Integer id) {
        directores directores = directoresRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Director no encontrada"));

        List<Peliculas> peliculas = peliculaRepository.findByDirector(directores);

        for (Peliculas pelicula : peliculas) {
            //Lista delete y resena delete
            resenaRepository.deleteByIdPelicula(pelicula);
            listaRepository.deleteByIdPelicula(pelicula);
            peliculaRepository.delete(pelicula);
        }

        directoresRepository.deleteById(id);
    }


    
    public directores patchDirectores(Integer id, directores parcialDirectores){
    
        directores usuarioToUpdate = findById(id);
            
            if (parcialDirectores.getNombreDirector() != null) {
                usuarioToUpdate.setNombreDirector(parcialDirectores.getNombreDirector());   
            }

            return directoresRepository.save(usuarioToUpdate);
        } 
    
}
    

