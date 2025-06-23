package com.FilmHunt.cl.FilmHunt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FilmHunt.cl.FilmHunt.model.Lista;
import com.FilmHunt.cl.FilmHunt.repository.ListaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ListaService {
    @Autowired
    private ListaRepository listaRepository;

    public List<Lista> findAll(){
        return listaRepository.findAll();
    }

    public Lista findById(Integer id){
        return listaRepository.findById(id).orElseThrow();
    }

    public Lista save(Lista lista){
        return listaRepository.save(lista);
    }

        public Lista create(Lista lista) {
        return listaRepository.save(lista);
    }


    public void deleteById(Integer id){
        listaRepository.deleteById(id);
    }

    public Lista patchLista(Integer id, Lista parcialLista){

            Lista listaToUpdate =findById(id);;
            
            if (parcialLista.getTituloLista() != null) {
                listaToUpdate.setTituloLista(parcialLista.getTituloLista());   
            }

            return listaRepository.save(listaToUpdate);
        } 

    }    

