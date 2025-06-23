package com.FilmHunt.cl.FilmHunt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FilmHunt.cl.FilmHunt.model.Peliculas;
import com.FilmHunt.cl.FilmHunt.model.Usuario;
import com.FilmHunt.cl.FilmHunt.model.resena;

@Repository
public interface ResenaRepository extends JpaRepository<resena, Integer>{

    //propios
    List<resena> findByTituloResena(String tituloResena );

    List<resena> findByAno(int ano);

    List<resena> findByPuntuacionResena(String puntuacionResena);

    //adyacentes

    List<resena> findByIdPelicula(Peliculas idPelicula);

    void deleteByIdPelicula(Peliculas idPelicula);
        
    List<resena> findByIdUsuario(Usuario IdUsuario);

    void deleteByIdUsuario(Usuario IdUsuario);

    List<resena> findByTituloResenaAndAnoAndPuntuacionResena(String tituloResena, int ano, String puntuacionResena);
}
