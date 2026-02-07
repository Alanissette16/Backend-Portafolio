package com.backend.proyecto.asesorias.dtos;

import java.time.LocalDateTime;

import com.backend.proyecto.asesorias.entities.AsesoriaEntity;

import lombok.Data;

@Data
public class AsesoriaResponseDto {

    private Long id;
    private String programadorId;
    private String programadorEmail;
    private String programadorName;
    private String solicitanteName;
    private String solicitanteEmail;
    private String date;
    private String time;
    private String nota;
    private AsesoriaEntity.Status status;
    private LocalDateTime createdAt;
}
