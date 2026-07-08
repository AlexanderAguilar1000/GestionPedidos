package com.Proyecto.ProyectoSematext.Controller;

import com.Proyecto.ProyectoSematext.DTO.DTOProducto;
import com.Proyecto.ProyectoSematext.Entity.ProductoEntity;
import com.Proyecto.ProyectoSematext.Entity.UsuarioEntity;
import com.Proyecto.ProyectoSematext.Repository.RepositorioProducto;
import com.Proyecto.ProyectoSematext.Service.ProductoService;
import com.Proyecto.ProyectoSematext.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Productos")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerProducto
{
    @Autowired
    private ProductoService productoService;

    @Autowired
    private RepositorioProducto repositorioProducto;

    @PostMapping("/productosagregar")
    public ResponseEntity<?>AgregarProducto(@RequestBody DTOProducto dtoproducto)
    {

        return ResponseEntity.ok(productoService.registrarProducto(dtoproducto));
    }

    @GetMapping("/listaProductos")
    public ResponseEntity<List<DTOProducto.ListaProductos>>getAllProductos()
    {
        return ResponseEntity.ok(repositorioProducto.findAll().stream().map(
                DTOProducto.ListaProductos::new
        ).toList());
    }


}
