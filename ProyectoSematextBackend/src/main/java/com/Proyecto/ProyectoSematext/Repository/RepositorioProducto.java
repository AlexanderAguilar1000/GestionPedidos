package com.Proyecto.ProyectoSematext.Repository;

import com.Proyecto.ProyectoSematext.Entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioProducto extends JpaRepository<ProductoEntity,Integer> {
    
    @Query("SELECT p FROM ProductoEntity p WHERE p.deleted = false")
    List<ProductoEntity> findAllNotDeleted();
}
