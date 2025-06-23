package com.FilmHunt.cl.FilmHunt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FilmHunt.cl.FilmHunt.model.Lista;
import com.FilmHunt.cl.FilmHunt.model.Peliculas;
import com.FilmHunt.cl.FilmHunt.model.Usuario;


@Repository
public interface ListaRepository extends JpaRepository<Lista, Integer> {

    //propios
    List<Lista> findByTituloLista(String tituloLista);

    //adyacentes
        
    List<Lista> findByIdPelicula(Peliculas IdPelicula);

    void deleteByIdPelicula(Peliculas IdPelicula);
        
    List<Lista> findByIdUsuario(Usuario IdUsuario);

    void deleteByIdUsuario(Usuario IdUsuario);

    // Query que busca todas listas junto con su usuario y película
    // une la tabla Lista con la tabla Usuario y Peliculas
    @org.springframework.data.jpa.repository.Query(
        "SELECT l FROM Lista l JOIN FETCH l.idUsuario u JOIN FETCH l.idPelicula p"
        )
    List<Lista> findAllWithUsuarioAndPelicula();
}
