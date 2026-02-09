package com.backend.proyecto.Horarios.mappers;

import com.backend.proyecto.Horarios.dtos.CreateHorarioRequestDto;
import com.backend.proyecto.Horarios.dtos.HorarioResponseDto;
import com.backend.proyecto.Horarios.entities.HorarioEntity;
import com.backend.proyecto.Programadores.entities.ProgramadorPerfilEntity;
import org.springframework.stereotype.Component;

//Componente para mapear entre Entidades y DTOs de Horario
@Component
public class HorarioMapper {

    //Convierte de DTO creación a Entidad
    public HorarioEntity toEntity(CreateHorarioRequestDto dto, ProgramadorPerfilEntity programador) {
        if (dto == null)
            return null;

        HorarioEntity entity = new HorarioEntity();
        entity.setProgramador(programador);
        entity.setDiaSemana(dto.getDiaSemana());
        entity.setHoraInicio(dto.getHoraInicio());
        entity.setHoraFin(dto.getHoraFin());
        entity.setModalidad(dto.getModalidad());
        entity.setActivo(dto.getActivo());

        return entity;
    }

    //Convierte de Entidad a DTO respuesta
    public HorarioResponseDto toResponseDto(HorarioEntity entity) {
        if (entity == null)
            return null;

        HorarioResponseDto dto = new HorarioResponseDto();
        dto.setId(entity.getId());
        dto.setProgramadorId(entity.getProgramador().getId());
        dto.setDiaSemana(entity.getDiaSemana());
        dto.setHoraInicio(entity.getHoraInicio());
        dto.setHoraFin(entity.getHoraFin());
        dto.setModalidad(entity.getModalidad());
        dto.setActivo(entity.getActivo());

        return dto;
    }
}
