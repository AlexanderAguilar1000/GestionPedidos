package com.Proyecto.ProyectoSematext.DTO.response;

import lombok.Data;

@Data
public class ProductoResponse
{
    private Integer idproducto;
    private String nombreproducto;
    private String descripcion;

    private Integer categoria;

    private Integer unidadMedida;

    private boolean activo;
}
