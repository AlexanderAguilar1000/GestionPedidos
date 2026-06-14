package com.Proyecto.ProyectoSematext.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="productos")
public class ProductoEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//para que ingrese el id automaticamente
    private Integer idproducto;
    private String nombre;
    private String descripcion;
    private boolean activo;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="idcategoria")
    private CategoriaEntity categoria;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idunidadmedida")
    private UnidadMedidaEntity unidadMedidaEntity;

    //metodos getter and setter

    public Integer getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public CategoriaEntity getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaEntity categoria) {
        this.categoria = categoria;
    }

    public UnidadMedidaEntity getUnidadMedidaEntity() {
        return unidadMedidaEntity;
    }

    public void setUnidadMedidaEntity(UnidadMedidaEntity unidadMedidaEntity) {
        this.unidadMedidaEntity = unidadMedidaEntity;
    }
}
