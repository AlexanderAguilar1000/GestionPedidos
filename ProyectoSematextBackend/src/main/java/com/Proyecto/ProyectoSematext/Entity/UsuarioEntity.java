package com.Proyecto.ProyectoSematext.Entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity //Representa una tabla en la base de datos
@Table(name="usuarios")//nopmbre de la tabla
public class UsuarioEntity
{ @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer idusuario;
   private String email;
   private String nombreusuario;
   private String apellidopaterno;
   private String apellidomaterno;
   private String contrasena;



   // 1 rol tiene muchos usuarios , pero 1 usuario no tiene muchos roles
    //1 a muchos

   @ManyToOne(fetch=FetchType.EAGER)
   @JoinColumn(name="idrol")//le digo que en la base de datos este es el FK paa que lo entienda
   private RolEntity rol;

   //metodos gettwer and setter





}
