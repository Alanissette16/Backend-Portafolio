package com.backend.proyecto.Usuarios.services.impl;

import com.backend.proyecto.Programadores.entities.ProgramadorPerfilEntity;
import com.backend.proyecto.Programadores.repositories.ProgramadorRepository;
import com.backend.proyecto.Usuarios.dtos.CreateUsuarioRequestDto;
import com.backend.proyecto.Usuarios.dtos.UpdateUsuarioRequestDto;
import com.backend.proyecto.Usuarios.dtos.UsuarioResponseDto;
import com.backend.proyecto.Usuarios.entities.UsuarioEntity;
import com.backend.proyecto.Usuarios.mappers.UsuarioMapper;
import com.backend.proyecto.Usuarios.repositories.UsuarioRepository;
import com.backend.proyecto.Usuarios.services.UsuarioService;
import com.backend.proyecto.exceptions.DuplicateResourceException;
import com.backend.proyecto.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

//Implementación del servicio de Usuario
@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ProgramadorRepository programadorRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    // Constructor para inyección de dependencias
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
            ProgramadorRepository programadorRepository,
            UsuarioMapper usuarioMapper,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.programadorRepository = programadorRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioResponseDto crearUsuario(CreateUsuarioRequestDto dto) {
        // Verificar que no exista usuario con ese email
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Usuario", "email", dto.getEmail());
        }

        // Convertir DTO a Entity
        UsuarioEntity entity = usuarioMapper.toEntity(dto);

        // Encriptar contraseña
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Guardar en base de datos
        UsuarioEntity savedEntity = usuarioRepository.save(entity);

        // Si el rol es PROGRAMMER, crear perfil de programador
        if (savedEntity.getRole() == UsuarioEntity.RolUsuario.PROGRAMMER) {
            crearPerfilProgramadorSiNoExiste(savedEntity);
        }

        // Convertir a ResponseDto y retornar
        return usuarioMapper.toResponseDto(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDto obtenerUsuarioPorId(Long id) {
        // Buscar usuario por ID
        UsuarioEntity entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
        return usuarioMapper.toResponseDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioResponseDto> obtenerTodosLosUsuarios(Pageable pageable) {
        // Listar todos los usuarios y convertirlos a DTO
        return usuarioRepository.findAll(pageable)
                .map(usuarioMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioResponseDto> obtenerProgramadores(Pageable pageable) {
        // Listar solo usuarios con rol PROGRAMMER
        return usuarioRepository.findByRole(UsuarioEntity.RolUsuario.PROGRAMMER, pageable)
                .map(usuarioMapper::toResponseDto);
    }

    @Override
    public UsuarioResponseDto actualizarUsuario(Long id, UpdateUsuarioRequestDto dto) {
        // Buscar usuario existente
        UsuarioEntity entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));

        // Actualizar solo los campos que vienen en el DTO
        if (dto.getEmail() != null) {
            String newEmail = dto.getEmail().trim().toLowerCase();
            String currentEmail = entity.getEmail().trim().toLowerCase();

            if (!newEmail.equals(currentEmail)) {
                // Verificar que el nuevo email no esté en uso por OTRO usuario (ignorando
                // mayúsculas)
                usuarioRepository.findByEmailIgnoreCase(dto.getEmail())
                        .ifPresent(existingUser -> {
                            if (!existingUser.getId().equals(id)) {
                                throw new DuplicateResourceException("Usuario", "email", dto.getEmail());
                            }
                        });
                entity.setEmail(dto.getEmail().trim());
            }
        }

        if (dto.getPassword() != null) {
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getDisplayName() != null) {
            entity.setDisplayName(dto.getDisplayName());
        }

        if (dto.getPhotoURL() != null) {
            entity.setPhotoURL(dto.getPhotoURL());
        }

        if (dto.getLastName() != null) {
            entity.setLastName(dto.getLastName());
        }

        if (dto.getLocation() != null) {
            entity.setLocation(dto.getLocation());
        }

        if (dto.getSpecialty() != null) {
            entity.setSpecialty(dto.getSpecialty());
        }

        if (dto.getBio() != null) {
            entity.setBio(dto.getBio());
        }

        if (dto.getQuote() != null) {
            entity.setQuote(dto.getQuote());
        }

        if (dto.getSkills() != null) {
            entity.setSkills(dto.getSkills());
        }

        if (dto.getHeadline() != null) {
            entity.setHeadline(dto.getHeadline());
        }

        if (dto.getAbout() != null) {
            entity.setAbout(dto.getAbout());
        }

        if (dto.getTags() != null) {
            entity.setTags(dto.getTags());
        }

        if (dto.getTheme() != null) {
            entity.setTheme(dto.getTheme());
        }

        if (dto.getSocials() != null) {
            entity.setSocials(dto.getSocials());
        }

        if (dto.getStats() != null) {
            entity.setStats(dto.getStats());
        }

        if (dto.getRole() != null) {
            try {
                UsuarioEntity.RolUsuario newRole = UsuarioEntity.RolUsuario.valueOf(dto.getRole().toUpperCase());
                entity.setRole(newRole);

                // Si cambia a PROGRAMMER, asegurar que tenga perfil
                if (newRole == UsuarioEntity.RolUsuario.PROGRAMMER) {
                    crearPerfilProgramadorSiNoExiste(entity);
                }
            } catch (IllegalArgumentException e) {
                // Ignore invalid roles or log warning
            }
        }

        // Guardar cambios
        UsuarioEntity updatedEntity = usuarioRepository.save(entity);
        return usuarioMapper.toResponseDto(updatedEntity);
    }

    private void crearPerfilProgramadorSiNoExiste(UsuarioEntity usuario) {
        if (!programadorRepository.existsByUsuarioId(usuario.getId())) {
            ProgramadorPerfilEntity perfil = new ProgramadorPerfilEntity();
            perfil.setUsuario(usuario);
            perfil.setEspecialidad(usuario.getSpecialty() != null ? usuario.getSpecialty() : "Desarrollador");
            perfil.setBiografia(usuario.getBio() != null ? usuario.getBio() : "");
            perfil.setTarifa(BigDecimal.ZERO);
            // Inicializar otros campos si es necesario
            programadorRepository.save(perfil);
        }
    }

    @Override
    public void eliminarUsuario(Long id) {
        // Verificar si existe
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario", "id", id);
        }

        usuarioRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDto obtenerUsuarioPorEmail(String email) {
        // Buscar por email y convertir a DTO
        UsuarioEntity entity = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "email", email));

        return usuarioMapper.toResponseDto(entity);
    }

}
