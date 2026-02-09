package com.backend.proyecto.asesorias.mappers;

import com.backend.proyecto.asesorias.dtos.CreateAsesoriaRequestDto;
import com.backend.proyecto.asesorias.dtos.AsesoriaResponseDto;
import com.backend.proyecto.asesorias.entities.AsesoriaEntity;
import com.backend.proyecto.Programadores.entities.ProgramadorPerfilEntity;
import com.backend.proyecto.Usuarios.entities.UsuarioEntity;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

//Componente para mapear entre Entidades y DTOs de Asesoría
@Component
public class AsesoriaMapper {

    //Convierte de DTO creación a Entidad
    public AsesoriaEntity toEntity(CreateAsesoriaRequestDto dto, ProgramadorPerfilEntity programador,
            UsuarioEntity usuarioExterno) {
        if (dto == null)
            return null;

        AsesoriaEntity entity = new AsesoriaEntity();
        entity.setProgramador(programador);
        entity.setUsuarioExterno(usuarioExterno);
        entity.setFecha(dto.getFecha());
        entity.setHoraInicio(dto.getHoraInicio());
        entity.setHoraFin(dto.getHoraFin());
        entity.setMotivo(dto.getMotivo());
        entity.setModalidad(dto.getModalidad());
        entity.setEstado(AsesoriaEntity.EstadoAsesoria.PENDIENTE);
        entity.setFechaSolicitud(LocalDateTime.now());

        return entity;
    }

    //Convierte de Entidad a DTO respuesta
    public AsesoriaResponseDto toResponseDto(AsesoriaEntity entity) {
        if (entity == null)
            return null;

        AsesoriaResponseDto dto = new AsesoriaResponseDto();
        dto.setId(entity.getId());
        dto.setProgramadorId(entity.getProgramador().getId());
        dto.setProgramadorNombre(entity.getProgramador().getUsuario().getDisplayName());
        dto.setUsuarioExternoId(entity.getUsuarioExterno().getId());
        dto.setUsuarioExternoNombre(entity.getUsuarioExterno().getDisplayName());
        dto.setUsuarioExternoEmail(entity.getUsuarioExterno().getEmail());
        dto.setFecha(entity.getFecha());
        dto.setHoraInicio(entity.getHoraInicio());
        dto.setHoraFin(entity.getHoraFin());
        dto.setMotivo(entity.getMotivo());
        dto.setEstado(entity.getEstado());
        dto.setModalidad(entity.getModalidad());
        dto.setNotasAdicionales(entity.getNotasAdicionales());
        dto.setFechaSolicitud(entity.getFechaSolicitud());
        dto.setFechaRespuesta(entity.getFechaRespuesta());

        return dto;
    }
}
