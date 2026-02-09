package com.backend.proyecto.Proyectos.repositories;

import com.backend.proyecto.Proyectos.entities.ProyectoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Repositorio JPA para la entidad Proyecto
@Repository
public interface ProyectoRepository extends JpaRepository<ProyectoEntity, Long> {
    // Busca proyectos por ID del programador
    List<ProyectoEntity> findByProgramadorId(Long programadorId);

    // Busca proyectos por ID del programador con paginación
    Page<ProyectoEntity> findByProgramadorId(Long programadorId, Pageable pageable);

    // Busca proyectos activos de un programador
    List<ProyectoEntity> findByProgramadorIdAndActivoTrue(Long programadorId);
}
