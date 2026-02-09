package com.backend.proyecto.Horarios.dtos;

import com.backend.proyecto.Horarios.entities.HorarioEntity.DiaSemana;
import com.backend.proyecto.Horarios.entities.HorarioEntity.Modalidad;
import java.time.LocalTime;

//DTO para actualizar un horario existente
public class UpdateHorarioRequestDto {
    private DiaSemana diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Modalidad modalidad;
    private Boolean activo;

    //Constructor vacío
    public UpdateHorarioRequestDto() {
    }

    //Constructor con parámetros
    public UpdateHorarioRequestDto(DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFin, Modalidad modalidad,
            Boolean activo) {
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.modalidad = modalidad;
        this.activo = activo;
    }

    //Getters y Setters
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
