package com.backend.proyecto.Programadores.repositories;

import com.backend.proyecto.Programadores.entities.ProgramadorPerfilEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//Repositorio para Programador
@Repository
public interface ProgramadorRepository extends JpaRepository<ProgramadorPerfilEntity, Long> {

    //Buscar programador por ID de usuario
    Optional<ProgramadorPerfilEntity> findByUsuarioId(Long usuarioId);

    //Verificar si existe programador para ese usuario
    boolean existsByUsuarioId(Long usuarioId);
}
