package com.backend.proyecto.Programadores.mappers;

import com.backend.proyecto.Programadores.dtos.CreateProgramadorRequestDto;
import com.backend.proyecto.Programadores.dtos.ProgramadorResponseDto;
import com.backend.proyecto.Programadores.entities.ProgramadorPerfilEntity;
import com.backend.proyecto.Usuarios.entities.UsuarioEntity;
import com.backend.proyecto.Usuarios.mappers.UsuarioMapper;
import org.springframework.stereotype.Component;

//Mapper manual para Programador
@Component
public class ProgramadorMapper {

    private final UsuarioMapper usuarioMapper;

    //Constructor para inyección de dependencias
    public ProgramadorMapper(UsuarioMapper usuarioMapper) {
        this.usuarioMapper = usuarioMapper;
    }

    //Convertir DTO a Entity
    public ProgramadorPerfilEntity toEntity(CreateProgramadorRequestDto dto, UsuarioEntity usuario) {
        if (dto == null) {
            return null;
        }

        ProgramadorPerfilEntity entity = new ProgramadorPerfilEntity();
        entity.setUsuario(usuario);
        entity.setEspecialidad(dto.getEspecialidad());
        entity.setBiografia(dto.getBiografia());
        entity.setLinkedinUrl(dto.getLinkedinUrl());
        entity.setGithubUrl(dto.getGithubUrl());
        entity.setSitioWeb(dto.getSitioWeb());
        entity.setTarifa(dto.getTarifa());

        return entity;
    }

    //Convertir Entity a ResponseDto
    public ProgramadorResponseDto toResponseDto(ProgramadorPerfilEntity entity) {
        if (entity == null) {
            return null;
        }

        ProgramadorResponseDto dto = new ProgramadorResponseDto();
        dto.setId(entity.getId());
        dto.setUsuario(usuarioMapper.toResponseDto(entity.getUsuario()));
        dto.setEspecialidad(entity.getEspecialidad());
        dto.setBiografia(entity.getBiografia());
        dto.setLinkedinUrl(entity.getLinkedinUrl());
        dto.setGithubUrl(entity.getGithubUrl());
        dto.setSitioWeb(entity.getSitioWeb());
        dto.setTarifa(entity.getTarifa());

        return dto;
    }
}
