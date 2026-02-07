package com.backend.proyecto.asesorias.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.backend.proyecto.asesorias.dtos.AsesoriaRequestDto;
import com.backend.proyecto.asesorias.dtos.AsesoriaResponseDto;
import com.backend.proyecto.asesorias.entities.AsesoriaEntity;
import com.backend.proyecto.asesorias.models.Asesoria;

@Component
public class AsesoriaMapper {
    
    public Asesoria toModel(AsesoriaEntity entity){
        if(entity == null)
            return null;
        Asesoria model = new Asesoria();
        model.setId(entity.getId());
        model.setProgramadorId(entity.getProgramadorId());
        model.setProgramadorName(entity.getProgramadorName());
        model.setProgramadorEmail(entity.getProgramadorEmail());
        model.setSolicitanteEmail(entity.getSolicitanteEmail());
        model.setSolicitanteName(entity.getSolicitanteName());
        model.setDate(entity.getDate());
        model.setTime(entity.getTime());
        model.setNota(entity.getNota());

        if(entity.getStatus() != null)
            model.setStatus(Asesoria.Status.valueOf(entity.getStatus().name()));
        model.setCreatedAt(entity.getCreatedAt());

        return model;
    }

    public AsesoriaEntity toEntity(Asesoria model){
            if(model == null)
            return null;
            AsesoriaEntity entity = new AsesoriaEntity();
            if (model.getId() != null)
                entity.setId(model.getId());
            entity.setProgramadorId(model.getProgramadorId());
            entity.setProgramadorName(model.getProgramadorName());
            entity.setProgramadorEmail(model.getProgramadorEmail());
            entity.setSolicitanteEmail(model.getSolicitanteEmail());
            entity.setSolicitanteName(model.getSolicitanteName());
            entity.setDate(model.getDate());
            entity.setTime(model.getTime());
            entity.setNota(model.getNota());

            if(model.getStatus() != null)

                entity.setStatus(AsesoriaEntity.Status.valueOf(model.getStatus().name()));
            entity.setCreatedAt(model.getCreatedAt());

            return entity;
    }

    public Asesoria toModel(AsesoriaResponseDto dto){
        if(dto == null)
            return null;
        Asesoria model = new Asesoria();
        model.setProgramadorId(dto.getProgramadorId());
        model.setProgramadorEmail(dto.getProgramadorEmail());
        model.setProgramadorName(dto.getProgramadorName());
        model.setSolicitanteName(dto.getSolicitanteName());
        model.setSolicitanteEmail(dto.getSolicitanteEmail());
        model.setDate(dto.getDate());
        model.setTime(dto.getTime());
        model.setNota(dto.getNota());

        return model;
    }

    public Asesoria toModel(AsesoriaRequestDto dto){
        if(dto == null)
            return null;
        Asesoria model = new Asesoria();
        model.setProgramadorId(dto.getProgramadorId());
        model.setProgramadorEmail(dto.getProgramadorEmail());
        model.setProgramadorName(dto.getProgramadorName());
        model.setSolicitanteName(dto.getSolicitanteName());
        model.setSolicitanteEmail(dto.getSolicitanteEmail());
        model.setDate(dto.getDate());
        model.setTime(dto.getTime());
        model.setNota(dto.getNota());

        return model;
    }

    public AsesoriaResponseDto toResponseDto(Asesoria model){
        if(model == null)
            return null;
        AsesoriaResponseDto dto = new AsesoriaResponseDto();
        dto.setId(model.getId());
        dto.setProgramadorId(model.getProgramadorId());
        dto.setProgramadorName(model.getProgramadorName());
        dto.setProgramadorEmail(model.getProgramadorEmail());
        dto.setSolicitanteName(model.getSolicitanteEmail());
        dto.setSolicitanteEmail(model.getSolicitanteEmail());
        dto.setDate(model.getDate());
        dto.setTime(model.getTime());
        dto.setNota(model.getNota());

        if(model.getStatus() != null)
            dto.setStatus(AsesoriaEntity.Status.valueOf(model.getStatus().name()));
        dto.setCreatedAt(model.getCreatedAt());
        return dto;
    }

    public List<AsesoriaResponseDto> toResponseDtoList(List<Asesoria> models){
       return models.stream()
            .map(this::toResponseDto)
            .collect(Collectors.toList());
    }

    public List<Asesoria> toModelList(List<AsesoriaEntity> entities){
        return entities.stream()
            .map(this::toModel)
            .collect(Collectors.toList());
    }
}
