package com.backend.proyecto.Usuarios.repositories;

import com.backend.proyecto.Usuarios.entities.UsuarioEntity;
import com.backend.proyecto.Usuarios.entities.UsuarioEntity.RolUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//Repositorio para acceso a datos de Usuario
@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    //Busca usuario por email
    Optional<UsuarioEntity> findByEmail(String email);

    //Verifica si existe un usuario con ese email
    boolean existsByEmail(String email);

    //Busca usuario por email (ignorando mayúsculas/minúsculas)
    Optional<UsuarioEntity> findByEmailIgnoreCase(String email);

    //Busca todos los usuarios por rol
    List<UsuarioEntity> findByRole(RolUsuario role);
}
