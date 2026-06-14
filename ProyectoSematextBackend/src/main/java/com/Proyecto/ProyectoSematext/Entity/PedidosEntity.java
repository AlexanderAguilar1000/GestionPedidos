package com.Proyecto.ProyectoSematext.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="pedidos")
public class PedidosEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idpedido;
    private LocalDateTime fecha;
    private String estado;

    //
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idusuario")
    private UsuarioEntity usuarioEntity;


}
