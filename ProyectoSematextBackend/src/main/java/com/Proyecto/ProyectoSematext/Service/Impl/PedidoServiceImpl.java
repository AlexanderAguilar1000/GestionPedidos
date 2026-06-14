package com.Proyecto.ProyectoSematext.Service.Impl;

import com.Proyecto.ProyectoSematext.Entity.PedidosEntity;

import com.Proyecto.ProyectoSematext.Repository.RepositorioPedido;
import com.Proyecto.ProyectoSematext.Service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl implements PedidoService
{
    @Autowired
    private RepositorioPedido repositorioPedido;


    @Override
    public PedidosEntity RegistrarPedido(PedidosEntity pedidosEntity)
    {
        PedidosEntity pedidosEntity1=repositorioPedido.save(pedidosEntity);
        return pedidosEntity1;
    }
}
