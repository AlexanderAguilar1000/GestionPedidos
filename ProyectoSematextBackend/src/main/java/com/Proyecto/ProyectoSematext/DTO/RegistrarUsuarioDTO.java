package com.Proyecto.ProyectoSematext.DTO;

import lombok.Data;

@Data
public class RegistrarUsuarioDTO
{
    private String email;
    private String nombreusuario;
    private String apellidopaterno;
    private String apellidomaterno;
    private String contrasena;
    private Integer idrol;
}
