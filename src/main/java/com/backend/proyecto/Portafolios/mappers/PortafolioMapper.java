package com.backend.proyecto.Portafolios.mappers;

import com.backend.proyecto.Portafolios.dtos.CreatePortafolioRequestDto;
import com.backend.proyecto.Portafolios.dtos.PortafolioResponseDto;
import com.backend.proyecto.Portafolios.entities.PortafolioEntity;
import com.backend.proyecto.Programadores.entities.ProgramadorPerfilEntity;
import org.springframework.stereotype.Component;

//Mapper manual para Portafolio
@Component
public class PortafolioMapper {

    //Convierte de DTO creación a Entidad
    public PortafolioEntity toEntity(CreatePortafolioRequestDto dto, ProgramadorPerfilEntity programador) {
        if (dto == null)
            return null;

        PortafolioEntity entity = new PortafolioEntity();
        entity.setProgramador(programador);
        entity.setTitulo(dto.getTitulo());
        entity.setDescripcion(dto.getDescripcion());
        entity.setContenido(dto.getContenido());
        entity.setOrden(dto.getOrden());
        entity.setVisible(dto.getVisible());

        return entity;
    }

    //Convierte de Entidad a DTO respuesta
    public PortafolioResponseDto toResponseDto(PortafolioEntity entity) {
        if (entity == null)
            return null;

        PortafolioResponseDto dto = new PortafolioResponseDto();
        dto.setId(entity.getId());
        dto.setProgramadorId(entity.getProgramador().getId());
        dto.setProgramadorNombre(entity.getProgramador().getUsuario().getDisplayName());
        dto.setTitulo(entity.getTitulo());
        dto.setDescripcion(entity.getDescripcion());
        dto.setContenido(entity.getContenido());
        dto.setOrden(entity.getOrden());
        dto.setVisible(entity.getVisible());

        return dto;
    }
}
