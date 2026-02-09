package com.backend.proyecto.asesorias.dtos;

import com.backend.proyecto.Horarios.entities.HorarioEntity.Modalidad;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

//DTO para la creación de una nueva asesoría
public class CreateAsesoriaRequestDto {
    @NotNull(message = "El ID del programador es obligatorio")
    private Long programadorId;

    @NotNull(message = "La fecha es obligatoria")
    @Future(message = "La fecha debe ser futura")
    private LocalDate fecha;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;

    @NotBlank(message = "El motivo es obligatorio")
    @Size(max = 1000, message = "El motivo no puede exceder 1000 caracteres")
    private String motivo;

    @NotNull(message = "La modalidad es obligatoria")
    private Modalidad modalidad;

    //Constructor vacío
    public CreateAsesoriaRequestDto() {
    }

    //Constructor completo
    public CreateAsesoriaRequestDto(Long programadorId, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin,
            String motivo, Modalidad modalidad) {
        this.programadorId = programadorId;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.motivo = motivo;
        this.modalidad = modalidad;
    }

    //Getters y Setters
    public Long getProgramadorId() {
        return programadorId;
    }

    public void setProgramadorId(Long programadorId) {
        this.programadorId = programadorId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }
}
