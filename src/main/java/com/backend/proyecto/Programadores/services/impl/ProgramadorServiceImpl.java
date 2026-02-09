package com.backend.proyecto.Programadores.services.impl;

import com.backend.proyecto.Programadores.dtos.CreateProgramadorRequestDto;
import com.backend.proyecto.Programadores.dtos.UpdateProgramadorRequestDto;
import com.backend.proyecto.Programadores.dtos.ProgramadorResponseDto;
import com.backend.proyecto.Programadores.entities.ProgramadorPerfilEntity;
import com.backend.proyecto.Programadores.mappers.ProgramadorMapper;
import com.backend.proyecto.Programadores.repositories.ProgramadorRepository;
import com.backend.proyecto.Programadores.services.ProgramadorService;
import com.backend.proyecto.Usuarios.entities.UsuarioEntity;
import com.backend.proyecto.Usuarios.repositories.UsuarioRepository;
import com.backend.proyecto.exceptions.DuplicateResourceException;
import com.backend.proyecto.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Implementación del servicio de Programador
@Service
@Transactional
public class ProgramadorServiceImpl implements ProgramadorService {

    private final ProgramadorRepository programadorRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProgramadorMapper programadorMapper;

    // Constructor para inyección de dependencias
    public ProgramadorServiceImpl(ProgramadorRepository programadorRepository,
            UsuarioRepository usuarioRepository,
            ProgramadorMapper programadorMapper) {
        this.programadorRepository = programadorRepository;
        this.usuarioRepository = usuarioRepository;
        this.programadorMapper = programadorMapper;
    }

    @Override
    public ProgramadorResponseDto crearProgramador(CreateProgramadorRequestDto dto) {
        // Verificar que existe el usuario
        UsuarioEntity usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", dto.getUsuarioId()));

        // Verificar que no exista perfil de programador para ese usuario
        if (programadorRepository.existsByUsuarioId(dto.getUsuarioId())) {
            throw new DuplicateResourceException("Ya existe un perfil de programador para este usuario");
        }

        // Convertir y guardar
        ProgramadorPerfilEntity entity = programadorMapper.toEntity(dto, usuario);
        ProgramadorPerfilEntity savedEntity = programadorRepository.save(entity);

        return programadorMapper.toResponseDto(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public ProgramadorResponseDto obtenerProgramadorPorId(Long id) {
        // Buscar programador por ID
        ProgramadorPerfilEntity entity = programadorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Programador", "id", id));

        return programadorMapper.toResponseDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProgramadorResponseDto> obtenerTodosProgramadores(Pageable pageable) {
        // Obtener todos los perfiles de programador y convertir a DTOs
        return programadorRepository.findAll(pageable)
                .map(programadorMapper::toResponseDto);
    }

    @Override
    public ProgramadorResponseDto actualizarProgramador(Long id, UpdateProgramadorRequestDto dto) {
        // Buscar perfil de programador existente
        ProgramadorPerfilEntity entity = programadorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Programador", "id", id));

        // Actualizar campos que vienen en el DTO
        if (dto.getEspecialidad() != null) {
            entity.setEspecialidad(dto.getEspecialidad());
        }
        if (dto.getBiografia() != null) {
            entity.setBiografia(dto.getBiografia());
        }
        if (dto.getLinkedinUrl() != null) {
            entity.setLinkedinUrl(dto.getLinkedinUrl());
        }
        if (dto.getGithubUrl() != null) {
            entity.setGithubUrl(dto.getGithubUrl());
        }
        if (dto.getSitioWeb() != null) {
            entity.setSitioWeb(dto.getSitioWeb());
        }
        if (dto.getTarifa() != null) {
            entity.setTarifa(dto.getTarifa());
        }

        // Guardar cambios
        ProgramadorPerfilEntity updatedEntity = programadorRepository.save(entity);
        return programadorMapper.toResponseDto(updatedEntity);
    }

    @Override
    public void eliminarProgramador(Long id) {
        // Verificar si existe
        if (!programadorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Programador", "id", id);
        }

        programadorRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ProgramadorResponseDto obtenerProgramadorPorUsuarioId(Long usuarioId) {
        // Buscar programador por ID de usuario y convertir a DTO
        ProgramadorPerfilEntity entity = programadorRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Programador", "usuarioId", usuarioId));

        return programadorMapper.toResponseDto(entity);
    }
}
