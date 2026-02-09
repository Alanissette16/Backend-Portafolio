package com.backend.proyecto.Horarios.repositories;

import com.backend.proyecto.Horarios.entities.HorarioEntity;
import com.backend.proyecto.Horarios.entities.HorarioEntity.DiaSemana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

//Repositorio JPA para operaciones con la entidad Horario
@Repository
public interface HorarioRepository extends JpaRepository<HorarioEntity, Long> {
    //Busca horarios activos de un programador
    List<HorarioEntity> findByProgramadorIdAndActivoTrue(Long programadorId);

    //Busca todos los horarios de un programador
    List<HorarioEntity> findByProgramadorId(Long programadorId);

    //Busca horarios activos de un programador en un día específico
    List<HorarioEntity> findByProgramadorIdAndDiaSemanaAndActivoTrue(Long programadorId, DiaSemana diaSemana);

    //Elimina todos los horarios de un programador
    void deleteByProgramadorId(Long programadorId);
}
