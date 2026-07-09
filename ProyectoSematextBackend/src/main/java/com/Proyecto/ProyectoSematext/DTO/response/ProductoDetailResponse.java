package com.Proyecto.ProyectoSematext.DTO.response;

import lombok.Data;

@Data
public class ProductoDetailResponse
{
    private Integer idproducto;
    private String nombreproducto;
    private String descripcion;

    private String categoria;

    private String unidadMedida;

    private boolean activo;
}
