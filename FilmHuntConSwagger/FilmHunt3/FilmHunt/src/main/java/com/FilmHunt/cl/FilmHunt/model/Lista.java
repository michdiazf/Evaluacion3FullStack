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
@Table( name = "Lista")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lista {
    
    @Id
    @GeneratedValue (strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column (nullable = false)
    private String tituloLista;

    @ManyToOne 
    @JoinColumn(name = "idPelicula", nullable = false)
    private Peliculas idPelicula;

    @ManyToOne 
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario idUsuario;

}
