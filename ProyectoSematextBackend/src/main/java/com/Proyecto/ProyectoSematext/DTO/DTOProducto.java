package com.Proyecto.ProyectoSematext.DTO;

import com.Proyecto.ProyectoSematext.Entity.ProductoEntity;

public class DTOProducto
{
    private Integer idproducto;
   private Integer idcategoria;
   private Integer idunidadmedida;

    private String nombreProducto;
    private String descripcion;

    private String nombreCategoria;
    private String unidadmedida;
    private boolean activo;

    //metodo getter and setter


    public Integer getIdproducto() {
        return idproducto;
    }

    public record ListaProductos(
            Integer idproducto,
            String nombreProducto,
            String nombreCategoria,
            String descripcion,
            String unidadmedida,
            boolean activo
    ) {
        public ListaProductos(ProductoEntity producto) {
            this(
                    producto.getIdproducto(),
                    producto.getNombre(),
                    producto.getCategoria().getNombrecategoria(),
                    producto.getDescripcion(),
                    producto.getUnidadMedidaEntity().getNombre(),
                    producto.isActivo()
            );
        }
    }








    public void setIdproducto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Integer getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(Integer idcategoria) {
        this.idcategoria = idcategoria;
    }

    public Integer getIdunidadmedida() {
        return idunidadmedida;
    }

    public void setIdunidadmedida(Integer idunidadmedida) {
        this.idunidadmedida = idunidadmedida;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getUnidadmedida() {
        return unidadmedida;
    }

    public void setUnidadmedida(String unidadmedida) {
        this.unidadmedida = unidadmedida;
    }
}
