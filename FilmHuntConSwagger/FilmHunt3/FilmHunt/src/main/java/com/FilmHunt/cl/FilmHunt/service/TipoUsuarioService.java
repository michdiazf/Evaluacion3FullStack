package com.FilmHunt.cl.FilmHunt.service;

    import java.util.List;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    
import com.FilmHunt.cl.FilmHunt.model.TipoUsuario;
import com.FilmHunt.cl.FilmHunt.model.Usuario;
import com.FilmHunt.cl.FilmHunt.repository.ListaRepository;
import com.FilmHunt.cl.FilmHunt.repository.ResenaRepository;
import com.FilmHunt.cl.FilmHunt.repository.TipoUsuarioRepository;
import com.FilmHunt.cl.FilmHunt.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
    
    @Service
    @Transactional
    public class TipoUsuarioService {

        @Autowired
        private TipoUsuarioRepository tipoUsuarioRepository;

        @Autowired
        private UsuarioRepository usuarioRepository;

        @Autowired
        private ResenaRepository resenaRepository;

        @Autowired
        private ListaRepository listaRepository;

    
        public List<TipoUsuario> findAll(){
            return tipoUsuarioRepository.findAll();
        }
    
        public TipoUsuario findById(Integer id){
            return tipoUsuarioRepository.findById(id).orElseThrow();
        }
    
        public TipoUsuario save(TipoUsuario usuario){
            return tipoUsuarioRepository.save(usuario);
        }

        public TipoUsuario create(TipoUsuario tipoUsuario) {
            return tipoUsuarioRepository.save(tipoUsuario);
        }


        public void deleteById(Integer id) {
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tipo de Usuario no encontrada"));

        List<Usuario> usuarios = usuarioRepository.findByIdTipoUsuario(tipoUsuario);

        for (Usuario usuario : usuarios) {
            //Lista delete y resena delete
            resenaRepository.deleteByIdUsuario(usuario);
            listaRepository.deleteByIdUsuario(usuario);
            usuarioRepository.delete(usuario);
        }

        tipoUsuarioRepository.deleteById(id);
    }

        
        public TipoUsuario patchTipoUsuario(Integer id, TipoUsuario parcialTipoUsuario){
    
            TipoUsuario usuarioToUpdate = findById(id);
                
                if (parcialTipoUsuario.getTipoUsuario() != null) {
                    usuarioToUpdate.setTipoUsuario(parcialTipoUsuario.getTipoUsuario());   
                }

                return tipoUsuarioRepository.save(usuarioToUpdate);
            } 
    
    }
    

