package com.backend.proyecto.asesorias.dtos;

import com.backend.proyecto.Horarios.entities.HorarioEntity.Modalidad;
import com.backend.proyecto.asesorias.entities.AsesoriaEntity.EstadoAsesoria;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

//DTO para actualizar una asesoría
public class UpdateAsesoriaRequestDto {
    @Future(message = "La fecha debe ser futura")
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Modalidad modalidad;

    @Size(max = 1000)
    private String notasAdicionales;

    private EstadoAsesoria estado;

    //Constructor vacío
    public UpdateAsesoriaRequestDto() {
    }

    //Constructor con parámetros
    public UpdateAsesoriaRequestDto(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, Modalidad modalidad,
            String notasAdicionales, EstadoAsesoria estado) {
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.modalidad = modalidad;
        this.notasAdicionales = notasAdicionales;
        this.estado = estado;
    }

    //Getters y Setters
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

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public String getNotasAdicionales() {
        return notasAdicionales;
    }

    public void setNotasAdicionales(String notasAdicionales) {
        this.notasAdicionales = notasAdicionales;
    }

    public EstadoAsesoria getEstado() {
        return estado;
    }

    public void setEstado(EstadoAsesoria estado) {
        this.estado = estado;
    }
}
