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

    /**
     * Registra un nuevo producto en el sistema.
     * 
     * Propósito:
     * Esta función toma los datos de un producto (representados en DTOProducto), valida
     * que la categoría y la unidad de medida asociadas existan en la base de datos, 
     * crea la entidad ProductoEntity persistible con estado activo por defecto, 
     * la almacena en la base de datos y retorna un DTO de respuesta con la información 
     * del producto creado, incluyendo su ID generado.
     * 
     * Manejo de Errores:
     * - Si la categoría (idcategoria) no existe en la base de datos, se lanza una RuntimeException con un mensaje descriptivo.
     * - Si la unidad de medida (idunidadmedida) no existe en la base de datos, se lanza una RuntimeException con un mensaje descriptivo.
     * - Cualquier error durante el proceso de persistencia en la base de datos (e.g., violación de restricciones) 
     *   será propagado como la excepción correspondiente de Spring/JPA.
     * 
     * @param productodto DTO que contiene la información del producto a registrar.
     * @return DTOProducto con la información del producto registrado, incluyendo su nuevo ID.
     * @throws RuntimeException si la categoría o la unidad de medida no son encontradas en la base de datos.
     */
    @Override
    public DTOProducto registrarProducto(DTOProducto productodto)
    {
        // 1. Manejo de errores y validación de la Categoría
        // Busca la categoría por su ID en el repositorio. Si no existe, lanza una RuntimeException.
        CategoriaEntity categoria=repositorioCategoria.findById(productodto.getIdcategoria())
                .orElseThrow(()->new RuntimeException("Categoria no encontrada con id: " + productodto.getIdcategoria()));

        // 2. Manejo de errores y validación de la Unidad de Medida
        // Busca la unidad de medida por su ID en el repositorio. Si no existe, lanza una RuntimeException.
        UnidadMedidaEntity unidadMedida=repositorioUnidadMedida.findById(productodto.getIdunidadmedida())
                .orElseThrow(()->new RuntimeException("Unidad de medida no encontrada con id: " + productodto.getIdunidadmedida()));

        // 3. Mapeo de DTO a Entidad Producto
        // Se instancia un nuevo ProductoEntity y se le asignan las relaciones y propiedades correspondientes.
        ProductoEntity producto=new ProductoEntity();
        producto.setCategoria(categoria);
        producto.setUnidadMedidaEntity(unidadMedida);
        producto.setNombre(productodto.getNombreProducto());
        producto.setDescripcion(productodto.getDescripcion());
        producto.setActivo(true); // Por defecto se registra como activo.

        // 4. Guardado en Base de Datos (Persistencia)
        // Se persiste el producto mapeado usando el RepositorioProducto.
        ProductoEntity productoGuardado=repositorioProducto.save(producto);

        // 5. Mapeo de la Entidad Guardada al DTO de Respuesta
        // Se prepara el objeto DTOProducto con los datos del producto persistido para retornar al cliente.
        DTOProducto respuesta =new DTOProducto();
        respuesta.setIdproducto(productoGuardado.getIdproducto() );
        respuesta.setNombreProducto(productoGuardado.getNombre());
        respuesta.setDescripcion(productoGuardado.getDescripcion());

        return respuesta;
    }
}
