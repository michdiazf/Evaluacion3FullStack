package com.FilmHunt.cl.FilmHunt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FilmHunt.cl.FilmHunt.model.Usuario;
import com.FilmHunt.cl.FilmHunt.repository.ListaRepository;
import com.FilmHunt.cl.FilmHunt.repository.ResenaRepository;
import com.FilmHunt.cl.FilmHunt.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ResenaRepository resenaRepository;

    @Autowired
    private ListaRepository listaRepository;


    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public Usuario findById(Integer id){
        return usuarioRepository.findById(id).orElseThrow();
    }

    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public Usuario create(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }


    public void deleteById(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("usuario no encontrada"));

        resenaRepository.deleteByIdUsuario(usuario);
        listaRepository.deleteByIdUsuario(usuario);

        usuarioRepository.deleteById(id);
    }



    public Usuario patchUsuario(Integer id, Usuario parcialUsuario){

            Usuario usuarioToUpdate = findById(id);
            
            if (parcialUsuario.getNombreUsuario() != null) {
                usuarioToUpdate.setNombreUsuario(parcialUsuario.getNombreUsuario());   
            }

            if(parcialUsuario.getCorreo() != null) {
                usuarioToUpdate.setCorreo(parcialUsuario.getCorreo());
            }

            if(parcialUsuario.getContrasena() != null) {
                usuarioToUpdate.setContrasena(parcialUsuario.getContrasena());
            }

            return usuarioRepository.save(usuarioToUpdate);
        } 

}
