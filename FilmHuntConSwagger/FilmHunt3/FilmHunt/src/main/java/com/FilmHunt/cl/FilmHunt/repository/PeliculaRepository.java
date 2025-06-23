package com.FilmHunt.cl.FilmHunt.repository;

import com.FilmHunt.cl.FilmHunt.model.Peliculas;
import com.FilmHunt.cl.FilmHunt.model.directores;
import com.FilmHunt.cl.FilmHunt.model.estadoPelicula;
import com.FilmHunt.cl.FilmHunt.model.genero;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeliculaRepository extends JpaRepository<Peliculas, Integer>{

    //propios
    List<Peliculas> findByTituloPelicula(String tituloPelicula);

    List<Peliculas> findByPuntuacion(String puntuacion);

    List<Peliculas> findByDuracion(String duracion);

    List<Peliculas> findByAno(Integer ano);

    //adyacentes
    
    List<Peliculas> findByGenero(genero genero);

    void deleteByGenero(genero genero);
    
    List<Peliculas> findByDirector(directores director);

    void deleteByDirector(directores director);
    
    List<Peliculas> findByEstado(estadoPelicula estado);

    void deleteByEstado(estadoPelicula estado);
    
    //Query para buscar peliculas por título
    // Conecta 3 tablas Peliculas, genero, directores, tiene que cumplir simultaneamente los 3 datos
    // ej: star , ciencia ficcion, george lucas
    @org.springframework.data.jpa.repository.Query(
        "SELECT p FROM Peliculas p " +          //selecciona objetos de peliculas
        "JOIN p.genero g " +                    //une tabla genero
        "JOIN p.director d " +                  //une tabla directores
        "WHERE g.nombre = :nombreGenero " +     //where filtra resultados
        "AND d.nombre = :nombreDirector " +
        "AND p.tituloPelicula LIKE %:titulo%"
    )
    List<Peliculas> buscarPorMultiplesCriterios(
        @org.springframework.data.repository.query.Param("nombreGenero") String nombreGenero,
        @org.springframework.data.repository.query.Param("nombreDirector") String nombreDirector,
        @org.springframework.data.repository.query.Param("titulo") String titulo
    );


}
