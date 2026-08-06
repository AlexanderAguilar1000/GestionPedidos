package com.Proyecto.ProyectoSematext.DTO.request;

import lombok.Data;

@Data
public class ProductoUpdateRequest
{
    // Opcional: si se envía, debe coincidir con el {idProducto} de la ruta (validado en el servicio).
    private Integer idproducto;
    private String nombreproducto;
    private String descripcion;

    private Integer categoria;

    private Integer unidadMedida;

    private boolean activo;



}
