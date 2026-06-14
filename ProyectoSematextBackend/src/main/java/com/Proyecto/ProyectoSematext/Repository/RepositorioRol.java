package com.Proyecto.ProyectoSematext.Repository;

import com.Proyecto.ProyectoSematext.Entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioRol  extends JpaRepository<RolEntity,Integer> {
}
