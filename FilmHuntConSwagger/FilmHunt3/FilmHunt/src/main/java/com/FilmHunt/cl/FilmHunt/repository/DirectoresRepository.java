package com.FilmHunt.cl.FilmHunt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.FilmHunt.cl.FilmHunt.model.directores;

@Repository
public interface DirectoresRepository extends JpaRepository<directores, Integer> {

    List<directores> findByNombreDirector (String nombreDirector);
    
    List<directores> findByNombreDirectorAndId(
        String nombreDirector,
        Integer id
    );


    //Query para buscar directores por título de película
    // Une la tabla directores con la tabla peliculas
    // y filtra por el título de la película
    // ej: George Lucas, Star Wars
    @Query(
        "SELECT d FROM directores d JOIN d.peliculas p WHERE p.titulo = :tituloPelicula"
        )
    List<directores> findDirectoresByTituloPelicula(@Param("tituloPelicula") String tituloPelicula);
}
