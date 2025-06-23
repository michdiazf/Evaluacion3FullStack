package com.FilmHunt.cl.FilmHunt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FilmHunt.cl.FilmHunt.model.TipoUsuario;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Integer> {

    List<TipoUsuario> findByIdTipoUsuario(Integer idTipoUsuario);

    List<TipoUsuario> findBytipoUsuario (String tipoUsuario);
    
    List<TipoUsuario> findByTipoUsuarioAndDescripcion(String tipoUsuario, String descripcion);

}