package com.Proyecto.ProyectoSematext.Controller;

import com.Proyecto.ProyectoSematext.DTO.DTOUsuario;
import com.Proyecto.ProyectoSematext.DTO.RegistrarUsuarioDTO;
import com.Proyecto.ProyectoSematext.Entity.UsuarioEntity;
import com.Proyecto.ProyectoSematext.Service.Impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/Usuario")
//@CrossOrigin(origins = "http://localhost:5173")
public class ControladorUsuario
{
    @Autowired
    private UsuarioServiceImpl usuarioService;

    /**
     * Endpoint para iniciar sesión (Login) de un usuario en el sistema.
     * 
     * Propósito:
     * Recibe las credenciales de inicio de sesión (nombre de usuario y contraseña),
     * delega la lógica de negocio y autenticación al servicio de usuario, y retorna
     * una respuesta HTTP exitosa (200 OK) si las credenciales son válidas.
     * 
     * Manejo de Errores:
     * - Si las credenciales no son válidas o el usuario no existe, el servicio lanza una
     *   RuntimeException. El bloque 'catch' captura esta excepción y retorna una respuesta
     *   HTTP 401 Unauthorized con un mensaje indicando credenciales inválidas.
     * 
     * @param dtoUsuario DTO que contiene el nombre de usuario y la contraseña a validar.
     * @return ResponseEntity<?> Mapa con un mensaje de éxito y el nombre de usuario si es correcto,
     *         o un mapa con mensaje de error y estatus 401 Unauthorized si es incorrecto.
     */
    @PostMapping("/login")                   // El request body convierte el json en objeto java
    public ResponseEntity<?> BuscarUsuario(@RequestBody DTOUsuario dtoUsuario) // Es la respuesta HTTP
    {
        try {
            // Se realiza la autenticación invocando al servicio de inicio de sesión.
            DTOUsuario usuarioLogueado = usuarioService.login(dtoUsuario.getNombreusuario(), dtoUsuario.getContrasena());
            System.out.println(usuarioLogueado.getNombreusuario());

            // Si el inicio de sesión es exitoso, se retorna un estatus 200 OK con los datos correspondientes.
            return ResponseEntity.ok(
                    Map.of(
                            "message", "Login correcto",
                            "nombreusuario", usuarioLogueado.getNombreusuario()
                    )
            );
        } catch (RuntimeException e) {
            // Manejo de errores: Si ocurre una excepción de credenciales incorrectas, se responde con 401 Unauthorized.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Credenciales invalidas"));
        }
    }

    /**
     * Endpoint para registrar una nueva cuenta de usuario en el sistema.
     * 
     * Propósito:
     * Recibe los datos necesarios para registrar un nuevo usuario (representados en RegistrarUsuarioDTO),
     * delega la creación e inserción en la base de datos al servicio, y retorna una respuesta
     * HTTP exitosa (200 OK) con la información del usuario creado.
     * 
     * Manejo de Errores:
     * - Si ocurre algún error en la validación de negocio (e.g. usuario duplicado, campos obligatorios vacíos),
     *   el servicio lanza una RuntimeException. El controlador la captura y responde con una
     *   HTTP 400 Bad Request que incluye el mensaje explicativo de la excepción.
     * 
     * @param registrarUsuarioDTO DTO con la información de registro del nuevo usuario.
     * @return ResponseEntity<?> Mapa con mensaje de éxito y nombre del usuario registrado,
     *         o un mapa con el mensaje del error y estatus 400 Bad Request.
     */
    @PostMapping("/Registrarusuario")
    public ResponseEntity<?> Registrarusuario(@RequestBody RegistrarUsuarioDTO registrarUsuarioDTO)
    {
        try {
            // Invoca al servicio para registrar la cuenta de usuario con los datos suministrados.
            UsuarioEntity usuarioEntity1 = usuarioService.RegistrarCuenta(registrarUsuarioDTO);

            // Si el registro se completa con éxito, se responde con un estatus 200 OK.
            return ResponseEntity.ok(
                    Map.of(
                            "message", "Registro correcto",
                            "nombreusuario", usuarioEntity1.getNombreusuario()
                    )
            );
        } catch (RuntimeException e) {
            // Manejo de errores: En caso de error de validación o conflicto, responde con 400 Bad Request y el mensaje del error.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }



}
