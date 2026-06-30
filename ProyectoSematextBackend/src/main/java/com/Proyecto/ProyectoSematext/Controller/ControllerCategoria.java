package com.Proyecto.ProyectoSematext.Controller;

import com.Proyecto.ProyectoSematext.DTO.DTOCategoria;
import com.Proyecto.ProyectoSematext.Entity.CategoriaEntity;
import com.Proyecto.ProyectoSematext.Repository.RepositorioCategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class ControllerCategoria
{
    @Autowired
    private RepositorioCategoria repositorioCategoria;

    @GetMapping("listacategorias")
    public ResponseEntity<List<DTOCategoria>>getAll()
    {
        //stream permite procesarlo
        return ResponseEntity.ok(repositorioCategoria.findAll().stream().map(DTOCategoria :: new ).toList());
    }


}
