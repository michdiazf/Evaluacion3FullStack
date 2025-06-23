package com.FilmHunt.cl.FilmHunt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FilmHunt.cl.FilmHunt.model.resena;
import com.FilmHunt.cl.FilmHunt.repository.ResenaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    public List<resena> findAll(){
        return resenaRepository.findAll();
    }

    public resena findById(Integer id){
        return resenaRepository.findById(id).orElseThrow();
    }

    public resena save(resena resena){
        return resenaRepository.save(resena);
    }
    
    public resena create(resena resena) {
        return resenaRepository.save(resena);
    }


    public void deleteById(Integer id){
        resenaRepository.deleteById(id);
    }
    public resena patchResena(Integer id, resena parcialResena){

            resena resenaToUpdate = findById(id);
            
            if (parcialResena.getTituloResena() != null) {
                resenaToUpdate.setTituloResena(parcialResena.getTituloResena());   
            }

            if(parcialResena.getAno() != 0) {
                resenaToUpdate.setAno(parcialResena.getAno());
            }

            if(parcialResena.getPuntuacionResena() != null) {
                resenaToUpdate.setPuntuacionResena(parcialResena.getPuntuacionResena());
            }

            return resenaRepository.save(resenaToUpdate);
        } 

    }

