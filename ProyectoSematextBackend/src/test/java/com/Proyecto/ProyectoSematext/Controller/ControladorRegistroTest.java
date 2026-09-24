package com.Proyecto.ProyectoSematext.Controller;

import com.Proyecto.ProyectoSematext.DTO.RegistroBasicoDTO;
import com.Proyecto.ProyectoSematext.Entity.UsuarioEntity;
import com.Proyecto.ProyectoSematext.Service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ControladorRegistroTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private ControladorRegistro controladorRegistro;

    @Test
    void registrarUsuario_conNombreUsuarioVacio_retornaBadRequest() {
        RegistroBasicoDTO dto = new RegistroBasicoDTO();
        dto.setNombreusuario("   ");
        dto.setContrasena("123456");

        ResponseEntity<Map<String, Object>> response = controladorRegistro.registrarUsuario(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse((Boolean) response.getBody().get("success"));
        assertEquals("El nombre de usuario es requerido", response.getBody().get("error"));
        verifyNoInteractions(usuarioService);
    }

    @Test
    void registrarUsuario_conContrasenaVacia_retornaBadRequest() {
        RegistroBasicoDTO dto = new RegistroBasicoDTO();
        dto.setNombreusuario("usuario1");
        dto.setContrasena("   ");

        ResponseEntity<Map<String, Object>> response = controladorRegistro.registrarUsuario(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse((Boolean) response.getBody().get("success"));
        assertEquals("La contraseña es requerida", response.getBody().get("error"));
        verifyNoInteractions(usuarioService);
    }

    @Test
    void registrarUsuario_conUsuarioDuplicado_retornaBadRequest() {
        RegistroBasicoDTO dto = new RegistroBasicoDTO();
        dto.setNombreusuario("usuario1");
        dto.setContrasena("123456");

        when(usuarioService.registrarBasico("usuario1", "123456"))
                .thenThrow(new RuntimeException("El nombre de usuario ya está en uso"));

        ResponseEntity<Map<String, Object>> response = controladorRegistro.registrarUsuario(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse((Boolean) response.getBody().get("success"));
        assertEquals("El nombre de usuario ya está en uso", response.getBody().get("error"));
        verify(usuarioService).registrarBasico("usuario1", "123456");
    }

    @Test
    void registrarUsuario_conDatosValidos_retornaCreated() {
        RegistroBasicoDTO dto = new RegistroBasicoDTO();
        dto.setNombreusuario("usuario1");
        dto.setContrasena("123456");

        UsuarioEntity usuarioGuardado = new UsuarioEntity();
        usuarioGuardado.setNombreusuario("usuario1");

        when(usuarioService.registrarBasico("usuario1", "123456")).thenReturn(usuarioGuardado);

        ResponseEntity<Map<String, Object>> response = controladorRegistro.registrarUsuario(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("success"));
        assertEquals("usuario1", ((Map<String, Object>) response.getBody().get("data")).get("nombreusuario"));
        verify(usuarioService).registrarBasico("usuario1", "123456");
    }
}
