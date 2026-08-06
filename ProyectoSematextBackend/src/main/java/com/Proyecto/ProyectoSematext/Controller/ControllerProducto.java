package com.Proyecto.ProyectoSematext.Controller;

import com.Proyecto.ProyectoSematext.DTO.DTOProducto;
import com.Proyecto.ProyectoSematext.DTO.common.ApiResponse;
import com.Proyecto.ProyectoSematext.DTO.request.ProductoUpdateRequest;
import com.Proyecto.ProyectoSematext.DTO.response.ProductoDetailResponse;
import com.Proyecto.ProyectoSematext.DTO.response.ProductoResponse;
import com.Proyecto.ProyectoSematext.Entity.ProductoEntity;
import com.Proyecto.ProyectoSematext.Entity.UsuarioEntity;
import com.Proyecto.ProyectoSematext.Repository.RepositorioProducto;
import com.Proyecto.ProyectoSematext.Service.ProductoService;
import com.Proyecto.ProyectoSematext.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    /**
     * Actualiza los datos de un producto existente.
     *
     * Propósito:
     * Recibe el id del producto por la ruta y los nuevos datos en el cuerpo de la petición,
     * delega la validación y actualización al servicio de producto, y retorna el producto
     * actualizado envuelto en el formato estándar de respuesta { success, data, error }.
     *
     * Manejo de Errores:
     * - Si los datos son inválidos, el producto no existe, está anulado, o la categoría/unidad
     *   de medida referenciadas no existen, el servicio lanza una ResponseStatusException que
     *   se traduce al código HTTP correspondiente (400/404) con un mensaje seguro para el cliente.
     * - Cualquier otro error inesperado se responde como 500 sin exponer detalles internos.
     *
     * @param idProducto id del producto a actualizar, tomado de la ruta.
     * @param pro datos a actualizar: nombreproducto, descripcion, categoria, unidadMedida, activo.
     * @return 200 con ApiResponse.ok(ProductoResponse) en éxito; 400/404/500 con
     *         ApiResponse.fail(mensaje) en error.
     */
    @PutMapping("/{idProducto}")
    public ResponseEntity<ApiResponse<ProductoResponse>> update(@PathVariable Integer idProducto, @RequestBody ProductoUpdateRequest pro)
    {
        try
        {
            ProductoResponse resultado = productoService.update(idProducto, pro);
            return ResponseEntity.ok(ApiResponse.ok(resultado));
        }
        catch (ResponseStatusException e)
        {
            return ResponseEntity.status(e.getStatusCode()).body(ApiResponse.fail(e.getReason()));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail("Error interno del servidor"));
        }
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
