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
@Table( name = "Resena")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class resena {

    @Id
    @GeneratedValue (strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column (nullable = false)
    private String tituloResena;

    @Column (nullable = false)
    private Integer ano;

    @Column (length = 3,nullable = false)
    private String puntuacionResena;

    @ManyToOne 
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario idUsuario;

    @ManyToOne 
    @JoinColumn(name = "idPelicula", nullable = false)
    private Peliculas idPelicula;

}