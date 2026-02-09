package com.backend.proyecto.asesorias.dtos;

import com.backend.proyecto.Horarios.entities.HorarioEntity.Modalidad;
import com.backend.proyecto.asesorias.entities.AsesoriaEntity.EstadoAsesoria;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AsesoriaResponseDto {
    private Long id;
    private Long programadorId;
    private String programadorNombre;
    private Long usuarioExternoId;
    private String usuarioExternoNombre;

    private String usuarioExternoEmail;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String motivo;
    private EstadoAsesoria estado;
    private Modalidad modalidad;
    private String notasAdicionales;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaRespuesta;

    //Constructor vacío
    public AsesoriaResponseDto() {
    }

    //Constructor completo
    public AsesoriaResponseDto(Long id, Long programadorId, String programadorNombre, Long usuarioExternoId,
            String usuarioExternoNombre, String usuarioExternoEmail, LocalDate fecha, LocalTime horaInicio,
            LocalTime horaFin, String motivo,
            EstadoAsesoria estado, Modalidad modalidad, String notasAdicionales, LocalDateTime fechaSolicitud,
            LocalDateTime fechaRespuesta) {
        this.id = id;
        this.programadorId = programadorId;
        this.programadorNombre = programadorNombre;
        this.usuarioExternoId = usuarioExternoId;
        this.usuarioExternoNombre = usuarioExternoNombre;
        this.usuarioExternoEmail = usuarioExternoEmail;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.motivo = motivo;
        this.estado = estado;
        this.modalidad = modalidad;
        this.notasAdicionales = notasAdicionales;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaRespuesta = fechaRespuesta;
    }

    //Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProgramadorId() {
        return programadorId;
    }

    public void setProgramadorId(Long programadorId) {
        this.programadorId = programadorId;
    }

    public String getProgramadorNombre() {
        return programadorNombre;
    }

    public void setProgramadorNombre(String programadorNombre) {
        this.programadorNombre = programadorNombre;
    }

    public Long getUsuarioExternoId() {
        return usuarioExternoId;
    }

    public void setUsuarioExternoId(Long usuarioExternoId) {
        this.usuarioExternoId = usuarioExternoId;
    }

    public String getUsuarioExternoNombre() {
        return usuarioExternoNombre;
    }

    public void setUsuarioExternoNombre(String usuarioExternoNombre) {
        this.usuarioExternoNombre = usuarioExternoNombre;
    }

    public String getUsuarioExternoEmail() {
        return usuarioExternoEmail;
    }

    public void setUsuarioExternoEmail(String usuarioExternoEmail) {
        this.usuarioExternoEmail = usuarioExternoEmail;
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

    public EstadoAsesoria getEstado() {
        return estado;
    }

    public void setEstado(EstadoAsesoria estado) {
        this.estado = estado;
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

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public LocalDateTime getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(LocalDateTime fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }
}
