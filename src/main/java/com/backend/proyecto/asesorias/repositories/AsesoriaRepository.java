package com.backend.proyecto.asesorias.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.proyecto.PaneldeControl.EstadoAsesoria;
import com.backend.proyecto.asesorias.entities.AsesoriaEntity;

@Repository
public interface AsesoriaRepository extends JpaRepository<AsesoriaEntity, Long> {

    Page<AsesoriaEntity> findByProgramadorId(String programadorId, Pageable pageable);

    Page<AsesoriaEntity> findBySolicitanteEmail(String email, Pageable pageable);

    Page<AsesoriaEntity> findByStatus(AsesoriaEntity.Status status, Pageable pageable);

    long countByStatus(AsesoriaEntity.Status status);

    List<AsesoriaEntity> findByDate(String date);

    // Lenguaje POSTGRES IMPORTANTE
   @Query("SELECT SUBSTRING(a.date, 1, 7) AS month, COUNT(a) AS count FROM AsesoriaEntity a GROUP BY month ORDER BY month") 
   List<EstadoAsesoria>countAsesoriasByMonth();
   @Query("SELECT new EstadoAsesoria(a.programadorId, COUNT(a)) FROM AsesoriaEntity a GROUP BY a.programadorId") 
   List<EstadoAsesoria>countAsesoriasByProgramador();
}
