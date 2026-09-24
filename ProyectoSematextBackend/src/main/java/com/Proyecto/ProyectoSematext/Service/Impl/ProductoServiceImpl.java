package com.Proyecto.ProyectoSematext.Service.Impl;

import com.Proyecto.ProyectoSematext.DTO.DTOProducto;
import com.Proyecto.ProyectoSematext.DTO.request.ProductoUpdateRequest;
import com.Proyecto.ProyectoSematext.DTO.response.ProductoDetailResponse;
import com.Proyecto.ProyectoSematext.DTO.response.ProductoResponse;
import com.Proyecto.ProyectoSematext.Entity.CategoriaEntity;
import com.Proyecto.ProyectoSematext.Entity.ProductoEntity;
import com.Proyecto.ProyectoSematext.Entity.UnidadMedidaEntity;
import com.Proyecto.ProyectoSematext.Repository.RepositorioCategoria;
import com.Proyecto.ProyectoSematext.Repository.RepositorioProducto;
import com.Proyecto.ProyectoSematext.Repository.RepositorioUnidadMedida;
import com.Proyecto.ProyectoSematext.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ProductoServiceImpl implements ProductoService
{
    /** Longitud máxima de nombre y descripción (varchar(255) por defecto en la tabla productos). */
    private static final int MAX_LONGITUD_TEXTO = 255;

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
     * - Datos inválidos (cuerpo nulo, nombre o descripción vacíos/en blanco o demasiado largos,
     *   categoría o unidad de medida sin informar): ResponseStatusException BAD_REQUEST.
     * - Categoría o unidad de medida inexistentes: ResponseStatusException BAD_REQUEST.
     * - Cualquier error durante el proceso de persistencia en la base de datos (e.g., violación de restricciones)
     *   será propagado como la excepción correspondiente de Spring/JPA.
     *
     * @param productodto DTO que contiene la información del producto a registrar.
     * @return DTOProducto con la información del producto registrado, incluyendo su nuevo ID.
     * @throws ResponseStatusException si la validación falla o la categoría/unidad de medida no existen.
     */
    @Override
    public DTOProducto registrarProducto(DTOProducto productodto)
    {
        // 0. Validación de campos obligatorios (antes de cualquier consulta a la base de datos)
        validarRegistro(productodto);

        // 1. Manejo de errores y validación de la Categoría
        // Busca la categoría por su ID en el repositorio. Si no existe, lanza BAD_REQUEST.
        CategoriaEntity categoria=repositorioCategoria.findById(productodto.getIdcategoria())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria no encontrada"));

        // 2. Manejo de errores y validación de la Unidad de Medida
        // Busca la unidad de medida por su ID en el repositorio. Si no existe, lanza BAD_REQUEST.
        UnidadMedidaEntity unidadMedida=repositorioUnidadMedida.findById(productodto.getIdunidadmedida())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unidad de medida no encontrada"));

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

    /**
     * Valida los campos obligatorios de un producto a registrar.
     *
     * @param productodto DTO recibido del cliente.
     * @throws ResponseStatusException BAD_REQUEST si el cuerpo es nulo, el nombre o la descripción
     *         están vacíos/en blanco o exceden {@value #MAX_LONGITUD_TEXTO} caracteres, o si la
     *         categoría o la unidad de medida no fueron informadas.
     */
    private void validarRegistro(DTOProducto productodto)
    {
        if (productodto == null)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los datos del producto son obligatorios");
        }
        if (productodto.getNombreProducto() == null || productodto.getNombreProducto().isBlank())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del producto es obligatorio");
        }
        if (productodto.getNombreProducto().length() > MAX_LONGITUD_TEXTO)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del producto no puede superar los " + MAX_LONGITUD_TEXTO + " caracteres");
        }
        if (productodto.getDescripcion() == null || productodto.getDescripcion().isBlank())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La descripcion del producto es obligatoria");
        }
        if (productodto.getDescripcion().length() > MAX_LONGITUD_TEXTO)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La descripcion del producto no puede superar los " + MAX_LONGITUD_TEXTO + " caracteres");
        }
        if (productodto.getIdcategoria() == null)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La categoria es obligatoria");
        }
        if (productodto.getIdunidadmedida() == null)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La unidad de medida es obligatoria");
        }
    }

    /**
     * Actualiza los datos de un producto existente.
     *
     * Propósito:
     * Valida los datos recibidos, localiza el producto por su id (fuente de verdad:
     * el parámetro de ruta, no el campo idproducto del cuerpo), verifica que no esté
     * anulado y que la categoría y unidad de medida referenciadas existan, aplica los
     * cambios y persiste el producto actualizado.
     *
     * Manejo de Errores:
     * - Datos inválidos (nombre vacío, categoria/unidadMedida nulos, id del cuerpo
     *   distinto al de la ruta): ResponseStatusException BAD_REQUEST.
     * - Producto no encontrado: ResponseStatusException NOT_FOUND.
     * - Producto anulado (deleted = true): ResponseStatusException BAD_REQUEST.
     * - Categoría o unidad de medida no encontradas: ResponseStatusException BAD_REQUEST.
     *
     * @param id id del producto a actualizar (tomado de la ruta).
     * @param productoUpdateRequest datos a aplicar sobre el producto.
     * @return ProductoResponse con el estado actualizado del producto.
     * @throws ResponseStatusException si la validación falla o alguna entidad referenciada no existe.
     */
    @Override
    public ProductoResponse update(Integer id, ProductoUpdateRequest productoUpdateRequest)
    {
        if (productoUpdateRequest.getNombreproducto() == null || productoUpdateRequest.getNombreproducto().isBlank())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del producto es obligatorio");
        }
        if (productoUpdateRequest.getCategoria() == null)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La categoria es obligatoria");
        }
        if (productoUpdateRequest.getUnidadMedida() == null)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La unidad de medida es obligatoria");
        }
        if (productoUpdateRequest.getIdproducto() != null && !productoUpdateRequest.getIdproducto().equals(id))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El id del producto no coincide con la ruta");
        }

        ProductoEntity productoEntity=repositorioProducto.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        if (productoEntity.isDeleted())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede editar un producto anulado");
        }

        CategoriaEntity categoriaEntity=repositorioCategoria.findById(productoUpdateRequest.getCategoria()).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria no encontrada"));

        UnidadMedidaEntity unidadMedidaEntity=repositorioUnidadMedida.findById(productoUpdateRequest.getUnidadMedida()).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unidad no encontrada"));


        productoEntity.setNombre(productoUpdateRequest.getNombreproducto());
        productoEntity.setDescripcion(productoUpdateRequest.getDescripcion());
        productoEntity.setCategoria(categoriaEntity);
        productoEntity.setUnidadMedidaEntity(unidadMedidaEntity);
        productoEntity.setActivo(productoUpdateRequest.isActivo());

        ProductoEntity productoactualizado= repositorioProducto.save(productoEntity);


         return toResponse(productoactualizado);

    }

    public ProductoDetailResponse getInformacionProducto(Integer idproducto)
    {
       ProductoEntity productoEntity=repositorioProducto.findById(idproducto).orElseThrow(()->new RuntimeException("NO se encontro el Producto"));

       ProductoDetailResponse obj=new ProductoDetailResponse();
       obj.setIdproducto(productoEntity.getIdproducto());
       obj.setNombreproducto(productoEntity.getNombre());
       obj.setDescripcion(productoEntity.getDescripcion());
       obj.setCategoria(productoEntity.getCategoria().getNombrecategoria());
       obj.setUnidadMedida(productoEntity.getUnidadMedidaEntity().getNombre());
       obj.setActivo(productoEntity.isActivo());
       return  obj;

    }

    public void  annularProducto(Integer idproducto)
    {
        ProductoEntity productoEntity=repositorioProducto.findById(idproducto).orElseThrow(()->new RuntimeException("No se encontro el Producto"));
        productoEntity.setDeleted(true);

        repositorioProducto.save(productoEntity);

    }

    public ProductoResponse toResponse(ProductoEntity entity) {
        ProductoResponse response=new ProductoResponse();
        response.setIdproducto(entity.getIdproducto());
        response.setNombreproducto(entity.getNombre());
        response.setDescripcion(entity.getDescripcion());
        response.setCategoria(entity.getCategoria().getIdcategoria());
        response.setUnidadMedida(entity.getUnidadMedidaEntity().getIdunidadmedida());
        response.setActivo(entity.isActivo());
        return response;
    }


}
