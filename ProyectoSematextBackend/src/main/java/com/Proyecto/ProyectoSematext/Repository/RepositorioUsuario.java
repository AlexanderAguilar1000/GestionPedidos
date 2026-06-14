package com.Proyecto.ProyectoSematext.Repository;

import com.Proyecto.ProyectoSematext.Entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioUsuario  extends JpaRepository<UsuarioEntity,Integer>
{
        Optional<UsuarioEntity> findByNombreusuario(String nombreusuario);//Este es un objeto que permite
                                                            //tener el valor del usuario como no tenerlo


    //AUTENTIFICAR POR OUSUARIO  Y CONTRASEÑA

}
