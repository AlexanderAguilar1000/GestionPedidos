package com.Proyecto.ProyectoSematext.Service;

import com.Proyecto.ProyectoSematext.DTO.DTOUsuario;
import com.Proyecto.ProyectoSematext.DTO.RegistrarUsuarioDTO;
import com.Proyecto.ProyectoSematext.Entity.UsuarioEntity;
import org.springframework.stereotype.Service;


public interface UsuarioService
{
   public DTOUsuario login(String nombreusuario,String contrasena);
   public UsuarioEntity RegistrarCuenta(RegistrarUsuarioDTO registrarUsuarioDTO);
   public UsuarioEntity registrarBasico(String nombreusuario, String contrasena);

}
