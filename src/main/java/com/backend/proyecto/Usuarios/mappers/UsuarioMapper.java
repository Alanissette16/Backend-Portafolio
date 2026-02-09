package com.backend.proyecto.Usuarios.mappers;

import com.backend.proyecto.Usuarios.dtos.CreateUsuarioRequestDto;
import com.backend.proyecto.Usuarios.dtos.UsuarioResponseDto;
import com.backend.proyecto.Usuarios.entities.UsuarioEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//Mapper manual para convertir entre Entity y DTOs
//NO usar Lombok ni MapStruct
@Component
public class UsuarioMapper {

    //Convierte CreateRequestDto a Entity
    public UsuarioEntity toEntity(CreateUsuarioRequestDto dto) {
        if (dto == null) {
            return null;
        }

        UsuarioEntity entity = new UsuarioEntity();
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword()); //Se encriptará en el servicio
        entity.setDisplayName(dto.getDisplayName());
        entity.setPhotoURL(dto.getPhotoURL());
        entity.setRole(dto.getRole());
        entity.setLastName(dto.getLastName());
        entity.setLocation(dto.getLocation());
        entity.setSpecialty(dto.getSpecialty());
        entity.setBio(dto.getBio());
        entity.setQuote(dto.getQuote());
        entity.setSkills(dto.getSkills());
        entity.setSocials(dto.getSocials());
        entity.setStats(dto.getStats());
        entity.setCreatedAt(LocalDateTime.now());

        return entity;
    }

    //Convierte Entity a ResponseDto
    public UsuarioResponseDto toResponseDto(UsuarioEntity entity) {
        if (entity == null) {
            return null;
        }

        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setDisplayName(entity.getDisplayName());
        dto.setPhotoURL(entity.getPhotoURL());
        dto.setLastName(entity.getLastName());
        dto.setLocation(entity.getLocation());
        dto.setSpecialty(entity.getSpecialty());
        dto.setBio(entity.getBio());
        dto.setQuote(entity.getQuote());
        dto.setSkills(entity.getSkills());
        dto.setSocials(entity.getSocials());
        dto.setStats(entity.getStats());
        dto.setRole(entity.getRole());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setLastLogin(entity.getLastLogin());

        //Portfolio fields
        dto.setHeadline(entity.getHeadline());
        dto.setAbout(entity.getAbout());
        dto.setTags(entity.getTags());
        dto.setTheme(entity.getTheme());

        return dto;
    }
}
