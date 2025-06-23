package com.FilmHunt.cl.FilmHunt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "Peliculas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Peliculas {

    @Id
    @GeneratedValue (strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column (nullable = false)
    private String tituloPelicula;

    @ManyToOne 
    @JoinColumn(name = "idGenero", nullable = false)
    private genero genero;

    @ManyToOne
    @JoinColumn(name = "idDirector", nullable = false)
    private directores director;

    @Column (nullable = false)
    private String puntuacion;

    @Column (nullable = false)
    private String duracion;

    @Column (nullable = false)
    private Integer ano ;

    @ManyToOne
    @JoinColumn(name = "idEstado", nullable = false)
    private estadoPelicula estado;
    
}
