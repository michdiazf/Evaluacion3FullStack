package com.FilmHunt.cl.FilmHunt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FilmHunt.cl.FilmHunt.model.TipoUsuario;
import com.FilmHunt.cl.FilmHunt.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    //propios
    List<Usuario> findByNombreUsuario(String nombreUsuario);

    List<Usuario> findByCorreo(String correo);

    List<Usuario> findByContrasena(String correo);

    //adyacentes
    List<Usuario> findByIdTipoUsuario(TipoUsuario idTipoUsuario);

    void deleteByIdTipoUsuario(TipoUsuario idTipoUsuario);

    


    // Query busca usuarios junto con su tipo de usuario 
    //une la tabla Usuario con la tabla TipoUsuario
    //el tipo de usuario debe coincidir con el nombre ingresado
    //ejemplo: admin, usuario, moderador
    @org.springframework.data.jpa.repository.Query(
        "SELECT u FROM Usuario u JOIN FETCH u.idTipoUsuario t WHERE t.nombre = :nombreTipo"
        )
    List<Usuario> findUsuariosByNombreTipoUsuario(String nombreTipo);

}
