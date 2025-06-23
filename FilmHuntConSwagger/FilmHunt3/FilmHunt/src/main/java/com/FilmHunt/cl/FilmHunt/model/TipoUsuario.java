package com.FilmHunt.cl.FilmHunt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "tipoUsuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoUsuario {
    @Id
    //se quita el generador automatico para
    //que los datos de esta tabla queden fijos
    private Integer idTipoUsuario;
    
    @Column (nullable = false)
    private String tipoUsuario;

    

}
