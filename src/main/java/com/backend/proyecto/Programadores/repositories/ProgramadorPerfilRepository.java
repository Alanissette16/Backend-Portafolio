package com.backend.proyecto.Programadores.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.proyecto.Programadores.entities.ProgramadorPerfilEntity;

//Repositorio JPA para operaciones de base de datos relacionadas con perfiles
//de programadores
@Repository
public interface ProgramadorPerfilRepository extends JpaRepository<ProgramadorPerfilEntity, Long> {

    //Buscar perfil por ID de usuario
    Optional<ProgramadorPerfilEntity> findByUsuarioId(Long usuarioId);

    //Listar perfiles públicos
    //List<ProgramadorPerfilEntity> findByIsPublicTrue();

    //Listar programadores disponibles para asesoría
    //List<ProgramadorPerfilEntity> findByAvailableForAdvisoryTrue();

    //Listar programadores públicos y disponibles para asesoría
    //List<ProgramadorPerfilEntity>
    //findByIsPublicTrueAndAvailableForAdvisoryTrue();

    //Buscar por especialidad
    List<ProgramadorPerfilEntity> findByEspecialidadContainingIgnoreCase(String especialidad);
}
