package com.backend.proyecto.Horarios.dtos;

import com.backend.proyecto.Horarios.entities.HorarioEntity.DiaSemana;
import com.backend.proyecto.Horarios.entities.HorarioEntity.Modalidad;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

//DTO para la creación de un nuevo horario
public class CreateHorarioRequestDto {
    @NotNull(message = "El ID del programador es obligatorio")
    private Long programadorId;

    @NotNull(message = "El día de la semana es obligatorio")
    private DiaSemana diaSemana;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;

    @NotNull(message = "La modalidad es obligatoria")
    private Modalidad modalidad;

    private Boolean activo = true;

    //Constructor vacío
    public CreateHorarioRequestDto() {
    }

    //Constructor con parámetros
    public CreateHorarioRequestDto(Long programadorId, DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFin,
            Modalidad modalidad, Boolean activo) {
        this.programadorId = programadorId;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.modalidad = modalidad;
        this.activo = activo;
    }

    //Getters y Setters
    public Long getProgramadorId() {
        return programadorId;
    }

    public void setProgramadorId(Long programadorId) {
        this.programadorId = programadorId;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
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

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
