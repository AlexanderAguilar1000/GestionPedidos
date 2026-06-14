package com.Proyecto.ProyectoSematext.Repository;

import com.Proyecto.ProyectoSematext.Entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioCategoria extends JpaRepository<CategoriaEntity,Integer> {
}
