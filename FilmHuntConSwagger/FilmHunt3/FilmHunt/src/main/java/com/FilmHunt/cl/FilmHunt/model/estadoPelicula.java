package com.FilmHunt.cl.FilmHunt.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "estadoPelicula")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class estadoPelicula {
    @Id
    @GeneratedValue (strategy= GenerationType.IDENTITY)
    private Integer id;
    
    @Column (nullable = false)
    private String nombreEstado;
}
