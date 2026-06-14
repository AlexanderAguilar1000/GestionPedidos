package com.Proyecto.ProyectoSematext.Controller;

import com.Proyecto.ProyectoSematext.DTO.RegistroBasicoDTO;
import com.Proyecto.ProyectoSematext.Entity.UsuarioEntity;
import com.Proyecto.ProyectoSematext.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/registro")
@CrossOrigin(origins = "http://localhost:5173")
public class ControladorRegistro {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Registra un nuevo usuario con nombre de usuario y contraseña.
     * Asigna el rol por defecto (id=1). La contraseña se almacena encriptada con BCrypt.
     *
     * @param dto Cuerpo JSON con los campos {@code nombreusuario} y {@code contrasena}
     * @return 201 Created con {@code { success, data: { nombreusuario }, error }}
     *         400 Bad Request si los campos son inválidos o el usuario ya existe
     *         500 Internal Server Error si ocurre un error inesperado
     */
    @PostMapping("/registrar")
    public ResponseEntity<Map<String, Object>> registrarUsuario(@RequestBody RegistroBasicoDTO dto) {
        Map<String, Object> response = new HashMap<>();

        if (dto.getNombreusuario() == null || dto.getNombreusuario().isBlank()) {
            response.put("success", false);
            response.put("data", null);
            response.put("error", "El nombre de usuario es requerido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (dto.getContrasena() == null || dto.getContrasena().isBlank()) {
            response.put("success", false);
            response.put("data", null);
            response.put("error", "La contraseña es requerida");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            UsuarioEntity guardado = usuarioService.registrarBasico(
                    dto.getNombreusuario(), dto.getContrasena());

            Map<String, Object> data = new HashMap<>();
            data.put("nombreusuario", guardado.getNombreusuario());

            response.put("success", true);
            response.put("data", data);
            response.put("error", null);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("data", null);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("data", null);
            response.put("error", "Error interno al registrar el usuario");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
