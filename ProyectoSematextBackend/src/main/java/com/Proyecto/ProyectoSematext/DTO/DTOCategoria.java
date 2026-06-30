package com.Proyecto.ProyectoSematext.DTO;

import com.Proyecto.ProyectoSematext.Entity.CategoriaEntity;

public class DTOCategoria
{
    private Integer idcategoria;
    private String nombrecategoria;

    public DTOCategoria(CategoriaEntity categoria) {
        this.idcategoria = categoria.getIdcategoria();
        this.nombrecategoria = categoria.getNombrecategoria();
    }


    //metodos getter and setter

    public Integer getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(Integer idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getNombrecategoria() {
        return nombrecategoria;
    }

    public void setNombrecategoria(String nombrecategoria) {
        this.nombrecategoria = nombrecategoria;
    }
}
