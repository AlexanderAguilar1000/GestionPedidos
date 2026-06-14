package com.Proyecto.ProyectoSematext.Controller;




import com.Proyecto.ProyectoSematext.Service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Pedido")
@CrossOrigin(origins = "http://localhost:5173")
public class ControladorPedido
{

    @Autowired
    private PedidoService pedidoService;


}
