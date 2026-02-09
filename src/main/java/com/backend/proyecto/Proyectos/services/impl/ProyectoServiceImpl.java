package com.backend.proyecto.Proyectos.services.impl;

import com.backend.proyecto.Proyectos.dtos.CreateProyectoRequestDto;
import com.backend.proyecto.Proyectos.dtos.UpdateProyectoRequestDto;
import com.backend.proyecto.Proyectos.dtos.ProyectoResponseDto;
import com.backend.proyecto.Proyectos.entities.ProyectoEntity;
import com.backend.proyecto.Proyectos.mappers.ProyectoMapper;
import com.backend.proyecto.Proyectos.repositories.ProyectoRepository;
import com.backend.proyecto.Proyectos.services.ProyectoService;
import com.backend.proyecto.Programadores.entities.ProgramadorPerfilEntity;
import com.backend.proyecto.Programadores.repositories.ProgramadorRepository;
import com.backend.proyecto.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Implementación del servicio para la gestión de proyectos
@Service
@Transactional
public class ProyectoServiceImpl implements ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final ProgramadorRepository programadorRepository;
    private final ProyectoMapper proyectoMapper;

    // Constructor para inyección de dependencias
    public ProyectoServiceImpl(ProyectoRepository proyectoRepository,
            ProgramadorRepository programadorRepository,
            ProyectoMapper proyectoMapper) {
        this.proyectoRepository = proyectoRepository;
        this.programadorRepository = programadorRepository;
        this.proyectoMapper = proyectoMapper;
    }

    @Override
    public ProyectoResponseDto crearProyecto(CreateProyectoRequestDto dto) {
        // Buscar programador por ID de perfil o por ID de usuario
        ProgramadorPerfilEntity programador = programadorRepository.findById(dto.getProgramadorId())
                .orElseGet(() -> programadorRepository.findByUsuarioId(dto.getProgramadorId())
                        .orElseThrow(() -> new ResourceNotFoundException("Programador", "id o usuarioId",
                                dto.getProgramadorId())));

        // Convertir DTO a entidad y guardar
        ProyectoEntity entity = proyectoMapper.toEntity(dto, programador);
        ProyectoEntity savedEntity = proyectoRepository.save(entity);
        return proyectoMapper.toResponseDto(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public ProyectoResponseDto obtenerProyectoPorId(Long id) {
        // Buscar proyecto por ID
        ProyectoEntity entity = proyectoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto", "id", id));
        return proyectoMapper.toResponseDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProyectoResponseDto> obtenerProyectosPorProgramador(Long programadorId, Pageable pageable) {
        // Buscar proyectos por programador y convertir a DTOs con paginación
        return proyectoRepository.findByProgramadorId(programadorId, pageable)
                .map(proyectoMapper::toResponseDto);
    }

    @Override
    public ProyectoResponseDto actualizarProyecto(Long id, UpdateProyectoRequestDto dto) {
        // Buscar proyecto existente por ID
        ProyectoEntity entity = proyectoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto", "id", id));

        // Actualizar campos que vienen en el DTO
        if (dto.getNombre() != null)
            entity.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null)
            entity.setDescripcion(dto.getDescripcion());
        if (dto.getTecnologias() != null)
            entity.setTecnologias(dto.getTecnologias());
        if (dto.getUrlRepositorio() != null)
            entity.setUrlRepositorio(dto.getUrlRepositorio());
        if (dto.getUrlDemo() != null)
            entity.setUrlDemo(dto.getUrlDemo());
        if (dto.getImagenUrl() != null)
            entity.setImagenUrl(dto.getImagenUrl());
        if (dto.getCategoria() != null)
            entity.setCategoria(dto.getCategoria());
        if (dto.getActivo() != null)
            entity.setActivo(dto.getActivo());
        if (dto.getDestacado() != null)
            entity.setDestacado(dto.getDestacado());

        // Guardar cambios
        ProyectoEntity updatedEntity = proyectoRepository.save(entity);
        return proyectoMapper.toResponseDto(updatedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProyectoResponseDto> obtenerTodosLosProyectos(Pageable pageable) {
        // Obtener todos los proyectos y convertir a DTOs con paginación
        return proyectoRepository.findAll(pageable)
                .map(proyectoMapper::toResponseDto);
    }

    @Override
    public void eliminarProyecto(Long id) {
        // Verificar si existe
        if (!proyectoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Proyecto", "id", id);
        }
        proyectoRepository.deleteById(id);
    }
}
