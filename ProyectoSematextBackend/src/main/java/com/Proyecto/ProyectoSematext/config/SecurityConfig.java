package com.Proyecto.ProyectoSematext.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration//DICE QUE ACA HAY CONFIGURACIONES GLOBALES
public class SecurityConfig
{
    @Bean   //Crea un objeto que se encarga de encryptar
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
