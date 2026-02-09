package com.backend.proyecto.Portafolios.services.impl;

import com.backend.proyecto.Portafolios.dtos.CreatePortafolioRequestDto;
import com.backend.proyecto.Portafolios.dtos.UpdatePortafolioRequestDto;
import com.backend.proyecto.Portafolios.dtos.PortafolioResponseDto;
import com.backend.proyecto.Portafolios.entities.PortafolioEntity;
import com.backend.proyecto.Portafolios.mappers.PortafolioMapper;
import com.backend.proyecto.Portafolios.repositories.PortafolioRepository;
import com.backend.proyecto.Portafolios.services.PortafolioService;
import com.backend.proyecto.Programadores.entities.ProgramadorPerfilEntity;
import com.backend.proyecto.Programadores.repositories.ProgramadorRepository;
import com.backend.proyecto.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//Implementación del servicio para la gestión de portafolios
@Service
@Transactional
public class PortafolioServiceImpl implements PortafolioService {

    private final PortafolioRepository portafolioRepository;
    private final ProgramadorRepository programadorRepository;
    private final PortafolioMapper portafolioMapper;

    //Constructor para inyección de dependencias
    public PortafolioServiceImpl(PortafolioRepository portafolioRepository,
            ProgramadorRepository programadorRepository,
            PortafolioMapper portafolioMapper) {
        this.portafolioRepository = portafolioRepository;
        this.programadorRepository = programadorRepository;
        this.portafolioMapper = portafolioMapper;
    }

    @Override
    public PortafolioResponseDto crearPortafolio(CreatePortafolioRequestDto dto) {
        //Buscar programador por ID
        ProgramadorPerfilEntity programador = programadorRepository.findById(dto.getProgramadorId())
                .orElseThrow(() -> new ResourceNotFoundException("Programador", "id", dto.getProgramadorId()));

        //Convertir DTO a entidad y guardar
        PortafolioEntity entity = portafolioMapper.toEntity(dto, programador);
        PortafolioEntity savedEntity = portafolioRepository.save(entity);
        return portafolioMapper.toResponseDto(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public PortafolioResponseDto obtenerPortafolioPorId(Long id) {
        //Buscar sección del portafolio por ID
        PortafolioEntity entity = portafolioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Portafolio", "id", id));
        return portafolioMapper.toResponseDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PortafolioResponseDto> obtenerPortafoliosPorProgramador(Long programadorId) {
        //Buscar secciones por programador y convertir a DTOs
        return portafolioRepository.findByProgramadorIdOrderByOrdenAsc(programadorId).stream()
                .map(portafolioMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PortafolioResponseDto actualizarPortafolio(Long id, UpdatePortafolioRequestDto dto) {
        //Buscar sección del portafolio existente
        PortafolioEntity entity = portafolioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Portafolio", "id", id));

        if (dto.getTitulo() != null)
            entity.setTitulo(dto.getTitulo());
        if (dto.getDescripcion() != null)
            entity.setDescripcion(dto.getDescripcion());
        if (dto.getContenido() != null)
            entity.setContenido(dto.getContenido());
        if (dto.getOrden() != null)
            entity.setOrden(dto.getOrden());
        if (dto.getVisible() != null)
            entity.setVisible(dto.getVisible());

        //Guardar cambios
        PortafolioEntity updatedEntity = portafolioRepository.save(entity);
        return portafolioMapper.toResponseDto(updatedEntity);
    }

    @Override
    public void eliminarPortafolio(Long id) {
        //Verificar si existe
        if (!portafolioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Portafolio", "id", id);
        }
        portafolioRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PortafolioResponseDto> obtenerTodosLosPortafolios() {
        return portafolioRepository.findByVisibleTrueOrderByOrdenAsc().stream()
                .map(portafolioMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
