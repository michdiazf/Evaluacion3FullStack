package com.FilmHunt.cl.FilmHunt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FilmHunt.cl.FilmHunt.model.estadoPelicula;

@Repository
public interface EstadoPeliculaRepository extends JpaRepository<estadoPelicula, Integer> {

    List<estadoPelicula> findByNombreEstado (String nombreEstado);
    List<estadoPelicula> findByNombreEstadoAndDescripcion(String nombreEstado, String descripcion);
}
