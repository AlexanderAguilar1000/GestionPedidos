package com.Proyecto.ProyectoSematext.Controller;

import com.Proyecto.ProyectoSematext.DTO.DTOProducto;
import com.Proyecto.ProyectoSematext.DTO.common.ApiResponse;
import com.Proyecto.ProyectoSematext.Service.ProductoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ControllerProductoTest {

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ControllerProducto controllerProducto;

    @Test
    void agregarProducto_conDatosValidos_retornaCreated() {
        DTOProducto entrada = new DTOProducto();
        DTOProducto creado = new DTOProducto();
        creado.setIdproducto(10);
        when(productoService.registrarProducto(entrada)).thenReturn(creado);

        ResponseEntity<ApiResponse<DTOProducto>> response = controllerProducto.AgregarProducto(entrada);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals(10, response.getBody().getData().getIdproducto());
        assertNull(response.getBody().getError());
    }

    @Test
    void agregarProducto_conDatosInvalidos_retornaBadRequestConMensaje() {
        DTOProducto entrada = new DTOProducto();
        when(productoService.registrarProducto(entrada))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del producto es obligatorio"));

        ResponseEntity<ApiResponse<DTOProducto>> response = controllerProducto.AgregarProducto(entrada);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertNull(response.getBody().getData());
        assertEquals("El nombre del producto es obligatorio", response.getBody().getError());
    }

    @Test
    void agregarProducto_conErrorInesperado_retorna500SinDetallesInternos() {
        DTOProducto entrada = new DTOProducto();
        when(productoService.registrarProducto(entrada))
                .thenThrow(new RuntimeException("could not execute statement; SQL [insert into productos ...]"));

        ResponseEntity<ApiResponse<DTOProducto>> response = controllerProducto.AgregarProducto(entrada);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Error interno del servidor", response.getBody().getError());
    }
}
