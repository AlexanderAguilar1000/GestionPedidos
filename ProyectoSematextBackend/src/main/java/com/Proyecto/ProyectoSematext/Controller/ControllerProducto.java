package com.Proyecto.ProyectoSematext.Controller;

import com.Proyecto.ProyectoSematext.DTO.DTOProducto;
import com.Proyecto.ProyectoSematext.DTO.request.ProductoUpdateRequest;
import com.Proyecto.ProyectoSematext.DTO.response.ProductoDetailResponse;
import com.Proyecto.ProyectoSematext.DTO.response.ProductoResponse;
import com.Proyecto.ProyectoSematext.Entity.ProductoEntity;
import com.Proyecto.ProyectoSematext.Entity.UsuarioEntity;
import com.Proyecto.ProyectoSematext.Repository.RepositorioProducto;
import com.Proyecto.ProyectoSematext.Service.ProductoService;
import com.Proyecto.ProyectoSematext.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
        return ResponseEntity.ok(repositorioProducto.findAllNotDeleted().stream().map(
                DTOProducto.ListaProductos::new
        ).toList());
    }

    @PutMapping("/{idProducto}")
    public ResponseEntity<ProductoResponse>update(@PathVariable Integer idProducto , @RequestBody ProductoUpdateRequest pro)
    {
        return ResponseEntity.ok(productoService.update(idProducto,pro));
    }


    @GetMapping("/{id}/productodetalle")
    public ResponseEntity<ProductoDetailResponse>getProducto(@PathVariable Integer id)
    {
        return ResponseEntity.ok(productoService.getInformacionProducto(id));
    }

    @PutMapping("/{id}/cancelProducto")
    public ResponseEntity<Map<String, String>>cancelDocument(@PathVariable Integer id )
    {
        productoService.annularProducto(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Producto anulado correctamente");
        return ResponseEntity.ok(response);
    }


}
