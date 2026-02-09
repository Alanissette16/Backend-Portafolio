package com.backend.proyecto.Horarios.services.impl;

import com.backend.proyecto.Horarios.dtos.CreateHorarioRequestDto;
import com.backend.proyecto.Horarios.dtos.UpdateHorarioRequestDto;
import com.backend.proyecto.Horarios.dtos.HorarioResponseDto;
import com.backend.proyecto.Horarios.entities.HorarioEntity;
import com.backend.proyecto.Horarios.mappers.HorarioMapper;
import com.backend.proyecto.Horarios.repositories.HorarioRepository;
import com.backend.proyecto.Horarios.services.HorarioService;
import com.backend.proyecto.Programadores.entities.ProgramadorPerfilEntity;
import com.backend.proyecto.Programadores.repositories.ProgramadorRepository;
import com.backend.proyecto.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//Implementación del servicio de gestión de horarios
@Service
@Transactional
public class HorarioServiceImpl implements HorarioService {

    private final HorarioRepository horarioRepository;
    private final ProgramadorRepository programadorRepository;
    private final HorarioMapper horarioMapper;

    //Constructor para inyección de dependencias
    public HorarioServiceImpl(HorarioRepository horarioRepository,
            ProgramadorRepository programadorRepository,
            HorarioMapper horarioMapper) {
        this.horarioRepository = horarioRepository;
        this.programadorRepository = programadorRepository;
        this.horarioMapper = horarioMapper;
    }

    @Override
    public HorarioResponseDto crearHorario(CreateHorarioRequestDto dto) {
        //Buscar programador por ID de Usuario (Frontend envía Usuario ID)
        ProgramadorPerfilEntity programador = programadorRepository.findByUsuarioId(dto.getProgramadorId())
                .orElseThrow(() -> new ResourceNotFoundException("Programador", "usuario_id", dto.getProgramadorId()));

        //Convertir DTO a entidad y guardar
        HorarioEntity entity = horarioMapper.toEntity(dto, programador);
        HorarioEntity savedEntity = horarioRepository.save(entity);
        return horarioMapper.toResponseDto(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public HorarioResponseDto obtenerHorarioPorId(Long id) {
        //Buscar horario por ID
        HorarioEntity entity = horarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Horario", "id", id));
        return horarioMapper.toResponseDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HorarioResponseDto> obtenerHorariosPorProgramador(Long usuarioId) {
        //Buscar perfil de programador por ID de Usuario
        return programadorRepository.findByUsuarioId(usuarioId)
                .map(perfil -> horarioRepository.findByProgramadorId(perfil.getId()).stream()
                        .map(horarioMapper::toResponseDto)
                        .collect(Collectors.toList()))
                .orElse(List.of()); //Retornar lista vacía si no existe perfil
    }

    @Override
    public HorarioResponseDto actualizarHorario(Long id, UpdateHorarioRequestDto dto) {
        //Buscar horario existente
        HorarioEntity entity = horarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Horario", "id", id));

        if (dto.getDiaSemana() != null)
            entity.setDiaSemana(dto.getDiaSemana());
        if (dto.getHoraInicio() != null)
            entity.setHoraInicio(dto.getHoraInicio());
        if (dto.getHoraFin() != null)
            entity.setHoraFin(dto.getHoraFin());
        if (dto.getModalidad() != null)
            entity.setModalidad(dto.getModalidad());
        if (dto.getActivo() != null)
            entity.setActivo(dto.getActivo());

        //Guardar cambios
        HorarioEntity updatedEntity = horarioRepository.save(entity);
        return horarioMapper.toResponseDto(updatedEntity);
    }

    @Override
    public void eliminarHorario(Long id) {
        //Verificar si existe
        if (!horarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Horario", "id", id);
        }
        horarioRepository.deleteById(id);
    }

    @Override
    public List<HorarioResponseDto> reemplazarHorarios(Long programadorId, List<CreateHorarioRequestDto> dtos) {
        //Verificar programador por ID de Usuario
        ProgramadorPerfilEntity programador = programadorRepository.findByUsuarioId(programadorId)
                .orElseThrow(() -> new ResourceNotFoundException("Programador", "usuario_id", programadorId));

        //Eliminar horarios anteriores usando el ID del perfil de programador
        horarioRepository.deleteByProgramadorId(programador.getId());

        //Crear nuevos horarios
        List<HorarioEntity> nuevasEntidades = dtos.stream()
                .map(dto -> {
                    //Asegurar que el programadorId del DTO sea el correcto
                    dto.setProgramadorId(programadorId);
                    return horarioMapper.toEntity(dto, programador);
                })
                .collect(Collectors.toList());

        List<HorarioEntity> guardados = horarioRepository.saveAll(nuevasEntidades);

        return guardados.stream()
                .map(horarioMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
