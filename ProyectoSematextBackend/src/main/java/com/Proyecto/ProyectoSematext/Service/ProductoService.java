package com.Proyecto.ProyectoSematext.Service;

import com.Proyecto.ProyectoSematext.DTO.DTOProducto;
import com.Proyecto.ProyectoSematext.DTO.request.ProductoUpdateRequest;
import com.Proyecto.ProyectoSematext.DTO.response.ProductoDetailResponse;
import com.Proyecto.ProyectoSematext.DTO.response.ProductoResponse;
import com.Proyecto.ProyectoSematext.Entity.ProductoEntity;

public interface ProductoService
{
    public DTOProducto registrarProducto(DTOProducto productodto);

    public ProductoResponse update(Integer id, ProductoUpdateRequest productoUpdateRequest);

    public ProductoDetailResponse getInformacionProducto(Integer idproducto);

    public void  annularProducto(Integer idproducto);
}
