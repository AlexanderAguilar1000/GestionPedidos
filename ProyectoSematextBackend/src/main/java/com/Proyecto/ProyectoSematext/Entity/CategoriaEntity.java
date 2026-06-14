package com.Proyecto.ProyectoSematext.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="categorias")
public class CategoriaEntity
{
    //Es programación orientada a objetos necetio trabjar con objetos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcategoria;
    private String nombrecategoria;



    //Metodos getter and setter



}
