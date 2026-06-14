package com.Proyecto.ProyectoSematext.Repository;

import com.Proyecto.ProyectoSematext.Entity.PedidosEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioPedido extends JpaRepository<PedidosEntity,Integer> {


}
