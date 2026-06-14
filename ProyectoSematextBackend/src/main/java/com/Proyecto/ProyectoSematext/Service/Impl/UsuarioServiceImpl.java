package com.Proyecto.ProyectoSematext.Service.Impl;

import com.Proyecto.ProyectoSematext.DTO.DTOUsuario;
import com.Proyecto.ProyectoSematext.DTO.RegistrarUsuarioDTO;
import com.Proyecto.ProyectoSematext.Entity.RolEntity;
import com.Proyecto.ProyectoSematext.Entity.UsuarioEntity;
import com.Proyecto.ProyectoSematext.Repository.RepositorioRol;
import com.Proyecto.ProyectoSematext.Repository.RepositorioUsuario;
import com.Proyecto.ProyectoSematext.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService
{
    @Autowired//Pide un objeto
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RepositorioRol repositorioRol;

    /**
     * Realiza la autenticación de un usuario mediante la verificación de sus credenciales.
     * <p>
     * <b>Flujo funcional:</b>
     * <ol>
     *     <li>Busca al usuario en la base de datos por su nombre de usuario único. Si no existe, se aborta el flujo con una excepción.</li>
     *     <li>Compara la contraseña en texto plano ingresada con el hash criptográfico almacenado utilizando {@link PasswordEncoder}.</li>
     *     <li>Si la autenticación es exitosa, realiza el mapeo de la entidad de dominio a un DTO seguro para su retorno.</li>
     * </ol>
     * 
     * <b>Escenarios de error contemplados:</b>
     * <ul>
     *     <li><b>Usuario no encontrado:</b> Se lanza {@link RuntimeException} ("Credenciales individuales") para evitar valores nulos.</li>
     *     <li><b>Falla de correspondencia de credenciales:</b> Se lanza {@link RuntimeException} ("Credenciales inválidas") si la firma del password encoder falla.</li>
     * </ul>
     * 
     * <i>Nota de mantenimiento:</i> Actualmente existen impresiones redundantes de contraseñas/hashes en consola (System.out) y
     * una doble evaluación con matches() que deberían refactorizarse para evitar fallas de seguridad y optimizar el consumo de CPU.
     *
     * @param nombreusuario Identificador único del usuario que intenta ingresar.
     * @param contrasena Contraseña en texto plano suministrada por el cliente.
     * @return {@link DTOUsuario} que contiene la información mínima segura del usuario autenticado.
     * @throws RuntimeException en caso de credenciales inválidas o ausencia del usuario en la BD.
     */
    @Override
    public DTOUsuario login(String nombreusuario, String contrasena)
    {
        // Registro temporal para trazabilidad en consola (Precaución: evitar en producción para contraseñas)
        System.out.println("Usuario enviado: " + nombreusuario);
        System.out.println("Password enviada: " + contrasena);

        System.out.println("[" + nombreusuario + "]");
        
        // Obtención de la entidad desde BD. El Optional previene retornos nulos controlando el flujo con una excepción
        UsuarioEntity usuario=repositorioUsuario.findByNombreusuario(nombreusuario).orElseThrow(()->new RuntimeException("Credenciales individuales"));

        System.out.println("Password BD: " + usuario.getContrasena());

        // Verificación inicial de la firma de contraseña
        boolean resultado =
                passwordEncoder.matches(contrasena, usuario.getContrasena());

        System.out.println("MATCH: " + resultado);
        System.out.println(usuario.getNombreusuario());
        System.out.println(usuario.getContrasena());

        // Validación estricta. Si la contraseña no coincide, aborta el proceso de autenticación
        if(!passwordEncoder.matches(contrasena, usuario.getContrasena())){ 
            throw new RuntimeException("Credenciales inválidas");
        }

        // Mapeo seguro de datos hacia el Data Transfer Object (DTO)
        DTOUsuario dto = new DTOUsuario();
        dto.setNombreusuario(usuario.getNombreusuario());
        
        return dto;
    }

    @Override
    public UsuarioEntity registrarBasico(String nombreusuario, String contrasena) {
        if (repositorioUsuario.findByNombreusuario(nombreusuario).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }
        // Rol por defecto: id=1 (rol base asignado a nuevos usuarios sin rol específico)
        RolEntity rolDefecto = repositorioRol.findById(1)
                .orElseThrow(() -> new RuntimeException("Rol por defecto no encontrado"));

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNombreusuario(nombreusuario);
        usuario.setContrasena(passwordEncoder.encode(contrasena));
        usuario.setRol(rolDefecto);

        return repositorioUsuario.save(usuario);
    }

    @Override
    public UsuarioEntity RegistrarCuenta(RegistrarUsuarioDTO registrarUsuarioDTO)
    {
        //buscando objeto
        RolEntity rolEntity=repositorioRol.findById(registrarUsuarioDTO.getIdrol()).orElseThrow(()->new RuntimeException("Rol no encontrado"));


        String passwordEncriptado=passwordEncoder.encode(registrarUsuarioDTO.getContrasena());
        UsuarioEntity usuario=new UsuarioEntity();
        usuario.setEmail(registrarUsuarioDTO.getEmail());
        usuario.setNombreusuario(registrarUsuarioDTO.getNombreusuario());
        usuario.setApellidopaterno(registrarUsuarioDTO.getApellidopaterno());
        usuario.setApellidomaterno(registrarUsuarioDTO.getApellidomaterno());
        usuario.setRol(rolEntity);
        usuario.setContrasena(passwordEncriptado);
        UsuarioEntity usuarioguardado=repositorioUsuario.save(usuario);
        return usuario;
    }
}
