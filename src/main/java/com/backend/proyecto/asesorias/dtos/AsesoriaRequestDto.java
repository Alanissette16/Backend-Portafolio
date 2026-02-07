package com.backend.proyecto.asesorias.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AsesoriaRequestDto {
    
    @NotBlank(message = "El ID del programador es obligatorio")
    private String programadorId;

    @NotBlank(message = "El email del programador es obligatorio")
    @Email(message = "El email del programador debe ser válido")
    private String programadorEmail;

    @NotBlank(message = "El nombre del programador es obligatorio")
    private String programadorName;

    @NotBlank(message = "El nombre del solicitante es obligatorio")
    private String solicitanteName;

    @NotBlank(message = "El email del solicitante es obligatorio")
    @Email(message = "El email del solicitante debe ser válido")
    private String solicitanteEmail;

    @NotBlank(message = "La fecha de la asesoría es obligatoria")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "La fecha debe tener formato YYYY-MM-DD")
    private String date;

    @NotBlank(message = "La hora de la asesoría es obligatoria")
    @Pattern(regexp = "\\d{2}:\\d{2}", message = "La hora debe tener formato HH:MM")
    private String time;

    @Size(max = 1000, message = "La nota no puede exceder 1000 caracteres")
    private String nota;
}
