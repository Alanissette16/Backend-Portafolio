package com.backend.proyecto.asesorias.entities;

import com.backend.proyecto.Horarios.entities.HorarioEntity;
import com.backend.proyecto.Programadores.entities.ProgramadorPerfilEntity;
import com.backend.proyecto.Usuarios.entities.UsuarioEntity;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

//Entidad que representa una asesoría solicitada por un usuario externo
@Entity
@Table(name = "asesorias")
public class AsesoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Programador que dará la asesoría
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programador_id", nullable = false)
    private ProgramadorPerfilEntity programador;

    //Usuario externo que solicita la asesoría
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_externo_id", nullable = false)
    private UsuarioEntity usuarioExterno;

    //Fecha de la asesoría
    @Column(nullable = false)
    private LocalDate fecha;

    //Hora de inicio
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    //Hora de fin
    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    //Motivo de la asesoría
    @Column(length = 1000, nullable = false)
    private String motivo;

    //Estado de la asesoría
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoAsesoria estado = EstadoAsesoria.PENDIENTE;

    //Modalidad
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HorarioEntity.Modalidad modalidad;

    //Notas adicionales del programador
    @Column(name = "notas_adicionales", length = 1000)
    private String notasAdicionales;

    //Fecha de solicitud
    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDateTime fechaSolicitud = LocalDateTime.now();

    //Fecha de respuesta (confirmación o rechazo)
    @Column(name = "fecha_respuesta")
    private LocalDateTime fechaRespuesta;

    //Enum para estado de asesoría
    public enum EstadoAsesoria {
        PENDIENTE,
        CONFIRMADA,
        RECHAZADA,
        COMPLETADA,
        CANCELADA
    }

    //Constructor vacío (JPA)
    public AsesoriaEntity() {
    }

    //Constructor con parámetros
    public AsesoriaEntity(Long id, ProgramadorPerfilEntity programador, UsuarioEntity usuarioExterno,
            LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, String motivo,
            EstadoAsesoria estado, HorarioEntity.Modalidad modalidad, String notasAdicionales,
            LocalDateTime fechaSolicitud, LocalDateTime fechaRespuesta) {
        this.id = id;
        this.programador = programador;
        this.usuarioExterno = usuarioExterno;
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

    //Getters
    public Long getId() {
        return id;
    }

    public ProgramadorPerfilEntity getProgramador() {
        return programador;
    }

    public UsuarioEntity getUsuarioExterno() {
        return usuarioExterno;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public String getMotivo() {
        return motivo;
    }

    public EstadoAsesoria getEstado() {
        return estado;
    }

    public HorarioEntity.Modalidad getModalidad() {
        return modalidad;
    }

    public String getNotasAdicionales() {
        return notasAdicionales;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public LocalDateTime getFechaRespuesta() {
        return fechaRespuesta;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setProgramador(ProgramadorPerfilEntity programador) {
        this.programador = programador;
    }

    public void setUsuarioExterno(UsuarioEntity usuarioExterno) {
        this.usuarioExterno = usuarioExterno;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setEstado(EstadoAsesoria estado) {
        this.estado = estado;
    }

    public void setModalidad(HorarioEntity.Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public void setNotasAdicionales(String notasAdicionales) {
        this.notasAdicionales = notasAdicionales;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public void setFechaRespuesta(LocalDateTime fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AsesoriaEntity that = (AsesoriaEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
