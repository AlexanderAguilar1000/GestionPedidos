package com.Proyecto.ProyectoSematext.Service;

import com.Proyecto.ProyectoSematext.DTO.DTOProducto;
import com.Proyecto.ProyectoSematext.DTO.request.ProductoUpdateRequest;
import com.Proyecto.ProyectoSematext.DTO.response.ProductoDetailResponse;
import com.Proyecto.ProyectoSematext.DTO.response.ProductoResponse;
import com.Proyecto.ProyectoSematext.Entity.ProductoEntity;

public interface ProductoService
{
    public DTOProducto registrarProducto(DTOProducto productodto);

    /**
     * Actualiza un producto existente.
     *
     * @param id id del producto a actualizar.
     * @param productoUpdateRequest datos a aplicar.
     * @return ProductoResponse con el producto actualizado.
     * @throws org.springframework.web.server.ResponseStatusException con NOT_FOUND si el producto no existe,
     *         o BAD_REQUEST si los datos son inválidos, el producto está anulado, o la categoría/unidad
     *         de medida referenciadas no existen.
     */
    public ProductoResponse update(Integer id, ProductoUpdateRequest productoUpdateRequest);

    public ProductoDetailResponse getInformacionProducto(Integer idproducto);

    public void  annularProducto(Integer idproducto);
}
