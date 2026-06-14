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

    @PostMapping("/login")                   //El request body convierte el json en objeto javaa
    public ResponseEntity<?> BuscarUsuario(@RequestBody DTOUsuario dtoUsuario) //Es la respuesta HTTP
    {
        try {

            DTOUsuario usuarioLogueado = usuarioService.login(dtoUsuario.getNombreusuario(), dtoUsuario.getContrasena());
            System.out.println(usuarioLogueado.getNombreusuario());

            return ResponseEntity.ok(
                    Map.of(
                            "message", "Login correcto",
                            "nombreusuario", usuarioLogueado.getNombreusuario()
                    )
            );
        }catch(RuntimeException e){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message","Credenciales invalidas"));
        }

    }

    @PostMapping("/Registrarusuario")
    public ResponseEntity<?>Registrarusuario(@RequestBody RegistrarUsuarioDTO registrarUsuarioDTO)
    {
        try{
            UsuarioEntity usuarioEntity1=usuarioService.RegistrarCuenta(registrarUsuarioDTO);

            return ResponseEntity.ok(
                    Map.of(
                            "message","Registro correcto",
                            "nombreusuario",usuarioEntity1.getNombreusuario()

                    )
            );
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",e.getMessage()));
        }
    }



}
