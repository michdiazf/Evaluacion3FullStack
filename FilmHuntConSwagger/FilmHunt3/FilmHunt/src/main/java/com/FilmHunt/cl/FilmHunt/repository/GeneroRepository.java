package com.FilmHunt.cl.FilmHunt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FilmHunt.cl.FilmHunt.model.genero;

@Repository
public interface GeneroRepository extends JpaRepository<genero, Integer> {

    List<genero> findByNombreGenero (String nombreGenero);


}
