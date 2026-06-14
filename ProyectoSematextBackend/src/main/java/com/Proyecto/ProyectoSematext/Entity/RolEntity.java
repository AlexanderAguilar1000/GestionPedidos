package com.Proyecto.ProyectoSematext.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name="roles")
public class RolEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Con esto le digo que cree automaticamente el ID
    private Integer idrol;
    private String  nombrerol;


    //este objeto debe tener muchos usuarios

    @OneToMany(mappedBy="rol", cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    private List<UsuarioEntity> usuarioEntity;



}
