package com.backend.proyecto.Portafolios.repositories;

import com.backend.proyecto.Portafolios.entities.PortafolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Repositorio JPA para la entidad Portafolio
@Repository
public interface PortafolioRepository extends JpaRepository<PortafolioEntity, Long> {

    //Busca las secciones del portafolio de un programador ordenadas
    List<PortafolioEntity> findByProgramadorIdOrderByOrdenAsc(Long programadorId);

    //Busca las secciones visibles del portafolio de un programador ordenadas
    List<PortafolioEntity> findByProgramadorIdAndVisibleTrueOrderByOrdenAsc(Long programadorId);

    //Busca todas las secciones visibles de todos los portafolios ordenadas
    List<PortafolioEntity> findByVisibleTrueOrderByOrdenAsc();
}
