package com.backend.proyecto.Proyectos.mappers;

import com.backend.proyecto.Proyectos.dtos.CreateProyectoRequestDto;
import com.backend.proyecto.Proyectos.dtos.ProyectoResponseDto;
import com.backend.proyecto.Proyectos.entities.ProyectoEntity;
import com.backend.proyecto.Programadores.entities.ProgramadorPerfilEntity;
import org.springframework.stereotype.Component;

//Componente para mapear entre Entidades y DTOs de Proyecto
@Component
public class ProyectoMapper {

    //Convierte de DTO creación a Entidad
    public ProyectoEntity toEntity(CreateProyectoRequestDto dto, ProgramadorPerfilEntity programador) {
        if (dto == null)
            return null;

        ProyectoEntity entity = new ProyectoEntity();
        entity.setProgramador(programador);
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setTecnologias(dto.getTecnologias());
        entity.setUrlRepositorio(dto.getUrlRepositorio());
        entity.setUrlDemo(dto.getUrlDemo());
        entity.setImagenUrl(dto.getImagenUrl());
        entity.setCategoria(dto.getCategoria());
        entity.setActivo(dto.getActivo());
        entity.setDestacado(dto.getDestacado());

        return entity;
    }

    //Convierte de Entidad a DTO respuesta
    public ProyectoResponseDto toResponseDto(ProyectoEntity entity) {
        if (entity == null)
            return null;

        ProyectoResponseDto dto = new ProyectoResponseDto();
        dto.setId(entity.getId());
        dto.setProgramadorId(entity.getProgramador().getId());
        dto.setProgramadorNombre(entity.getProgramador().getUsuario().getDisplayName());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setTecnologias(entity.getTecnologias());
        dto.setUrlRepositorio(entity.getUrlRepositorio());
        dto.setUrlDemo(entity.getUrlDemo());
        dto.setImagenUrl(entity.getImagenUrl());
        dto.setCategoria(entity.getCategoria());
        dto.setActivo(entity.getActivo());
        dto.setDestacado(entity.getDestacado());
        dto.setFechaCreacion(entity.getFechaCreacion());

        return dto;
    }
}
