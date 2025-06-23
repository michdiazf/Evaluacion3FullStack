package com.FilmHunt.cl.FilmHunt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FilmHunt.cl.FilmHunt.model.Peliculas;
import com.FilmHunt.cl.FilmHunt.repository.ListaRepository;
import com.FilmHunt.cl.FilmHunt.repository.PeliculaRepository;
import com.FilmHunt.cl.FilmHunt.repository.ResenaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PeliculasService {

    @Autowired
    private PeliculaRepository peliculaRepository;
    
    @Autowired
    private ResenaRepository resenaRepository;

    @Autowired
    private ListaRepository listaRepository;

    public List<Peliculas> findAll(){
        return peliculaRepository.findAll();
    }

    public Peliculas findById(Integer id){
        return peliculaRepository.findById(id).orElseThrow();
    }

    public Peliculas save(Peliculas peliculas){
        return peliculaRepository.save(peliculas);
    }

    public Peliculas create(Peliculas peliculas) {
        return peliculaRepository.save(peliculas);
    }


    public void deleteById(Integer id) {
        Peliculas peliculas = peliculaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("pelicula no encontrada"));

        resenaRepository.deleteByIdPelicula(peliculas);
        listaRepository.deleteByIdPelicula(peliculas);

        peliculaRepository.deleteById(id);
    }



    public Peliculas patchPelicula(Integer id, Peliculas parcialPelicula){

            Peliculas peliculaToUpdate = findById(id);
            
            if (parcialPelicula.getTituloPelicula() != null) {
                peliculaToUpdate.setTituloPelicula(parcialPelicula.getTituloPelicula());   
            }

            if(parcialPelicula.getPuntuacion() != null) {
                peliculaToUpdate.setPuntuacion(parcialPelicula.getPuntuacion());
            }

            if(parcialPelicula.getDuracion() != null) {
                peliculaToUpdate.setDuracion(parcialPelicula.getDuracion());
            }
            
            if(parcialPelicula.getAno() != 0) {
                peliculaToUpdate.setAno(parcialPelicula.getAno());
            }

            return peliculaRepository.save(peliculaToUpdate);
        } 

    }


