package com.backend.proyecto.asesorias.models;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Asesoria {
    
    private Long id;
    private String programadorId;
    private String programadorEmail;
    private String programadorName;
    private String solicitanteName;
    private String solicitanteEmail;
    private String date;
    private String time;
    private String nota;
    private Status status;
    private LocalDateTime createdAt;

    public enum Status {
        pending,
        approved,
        rejected
    }
}
