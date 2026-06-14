package com.Proyecto.ProyectoSematext.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="unidades_medida")
public class UnidadMedidaEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idunidadmedida;
    private String nombre;

    //metodos getter ande setter


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
