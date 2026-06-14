package com.Proyecto.ProyectoSematext.Service.Impl;

import com.Proyecto.ProyectoSematext.DTO.DTOProducto;
import com.Proyecto.ProyectoSematext.Entity.CategoriaEntity;
import com.Proyecto.ProyectoSematext.Entity.ProductoEntity;
import com.Proyecto.ProyectoSematext.Entity.UnidadMedidaEntity;
import com.Proyecto.ProyectoSematext.Repository.RepositorioCategoria;
import com.Proyecto.ProyectoSematext.Repository.RepositorioProducto;
import com.Proyecto.ProyectoSematext.Repository.RepositorioUnidadMedida;
import com.Proyecto.ProyectoSematext.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ProductoServiceImpl implements ProductoService
{
    @Autowired
    private RepositorioProducto repositorioProducto;

    @Autowired
    private RepositorioCategoria repositorioCategoria;

    @Autowired
    private RepositorioUnidadMedida repositorioUnidadMedida;

    @Override
    public DTOProducto registrarProducto(DTOProducto productodto)
    {
        CategoriaEntity categoria=repositorioCategoria.findById(productodto.getIdcategoria()).orElseThrow(()->new RuntimeException("Categoria no encontrada "));
        UnidadMedidaEntity unidadMedida=repositorioUnidadMedida.findById(productodto.getIdunidadmedida()).orElseThrow(()->new RuntimeException("Unidad de medida"));
        ProductoEntity producto=new ProductoEntity();
        producto.setCategoria(categoria);
        producto.setUnidadMedidaEntity(unidadMedida);
        producto.setNombre(productodto.getNombreProducto());
        producto.setDescripcion(productodto.getDescripcion());
        producto.setActivo(true);

        ProductoEntity productoGuardado=repositorioProducto.save(producto);

        DTOProducto respuesta =new DTOProducto();
        respuesta.setIdproducto(productoGuardado.getIdproducto() );
        respuesta.setNombreProducto(productoGuardado.getNombre());
        respuesta.setDescripcion(productoGuardado.getDescripcion());

        return respuesta;

    }
}
