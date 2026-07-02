package com.Proyecto.ProyectoSematext.Controller;

import com.Proyecto.ProyectoSematext.DTO.DTOCategoria;
import com.Proyecto.ProyectoSematext.DTO.DTOUnidadMedida;
import com.Proyecto.ProyectoSematext.Repository.RepositorioUnidadMedida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/UnidadMedida")
public class ControladorUnidadMedida
{
    @Autowired
    private RepositorioUnidadMedida repositorioUnidadMedida;

    @GetMapping("listaUnidadMedida")
    public ResponseEntity<List<DTOUnidadMedida>> getAll()
    {
        //stream permite procesarlo
        return ResponseEntity.ok(repositorioUnidadMedida.findAll().stream().map(DTOUnidadMedida :: new ).toList());
    }
}
