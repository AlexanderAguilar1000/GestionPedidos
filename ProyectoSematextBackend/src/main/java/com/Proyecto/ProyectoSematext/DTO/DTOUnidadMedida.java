package com.Proyecto.ProyectoSematext.DTO;


import com.Proyecto.ProyectoSematext.Entity.UnidadMedidaEntity;

public class DTOUnidadMedida
{
    private Integer idunidadmedida;
    private String nombre;


    public DTOUnidadMedida(UnidadMedidaEntity unidadMedidaEntity)
    {
        this.idunidadmedida= unidadMedidaEntity.getIdunidadmedida();
        this.nombre= unidadMedidaEntity.getNombre();
    }

    //metodos getter and setter

    public Integer getIdunidadmedida() {
        return idunidadmedida;
    }

    public void setIdunidadmedida(Integer idunidadmedida) {
        this.idunidadmedida = idunidadmedida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
