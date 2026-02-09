package com.backend.proyecto.asesorias.repositories;

import com.backend.proyecto.asesorias.entities.AsesoriaEntity;
import com.backend.proyecto.asesorias.entities.AsesoriaEntity.EstadoAsesoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

//Repositorio JPA para la entidad Asesoria
@Repository
public interface AsesoriaRepository extends JpaRepository<AsesoriaEntity, Long> {
    //Busca asesorías por ID del programador
    List<AsesoriaEntity> findByProgramadorId(Long programadorId);

    //Busca asesorías por ID del usuario externo (solicitante)
    List<AsesoriaEntity> findByUsuarioExternoId(Long usuarioExternoId);

    //Busca solapamientos (mismo programador, fecha, hora y estado)
    List<AsesoriaEntity> findByProgramadorIdAndFechaAndEstado(Long programadorId, LocalDate fecha,
            EstadoAsesoria estado);

    //Busca para el scheduler (recordatorios)
    List<AsesoriaEntity> findByFechaAndEstado(LocalDate fecha, EstadoAsesoria estado);

    //Busca asesorías de un programador por estado
    List<AsesoriaEntity> findByProgramadorIdAndEstado(Long programadorId, EstadoAsesoria estado);

    //Busca asesorías de un usuario externo por estado
    List<AsesoriaEntity> findByUsuarioExternoIdAndEstado(Long usuarioExternoId, EstadoAsesoria estado);
}
