package com.Proyecto.ProyectoSematext.DTO.request;

import lombok.Data;

@Data
public class ProductoUpdateRequest
{
    private Integer idproducto;
    private String nombreproducto;
    private String descripcion;

    private Integer categoria;

    private Integer unidadMedida;

    private boolean activo;



}
