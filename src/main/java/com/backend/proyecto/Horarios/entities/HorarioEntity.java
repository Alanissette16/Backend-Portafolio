package com.backend.proyecto.Horarios.entities;

import com.backend.proyecto.Programadores.entities.ProgramadorPerfilEntity;
import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.Objects;

//Entidad que representa la disponibilidad horaria de un programador
@Entity
@Table(name = "horarios")
public class HorarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Referencia al programador
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programador_id", nullable = false)
    private ProgramadorPerfilEntity programador;

    //Día de la semana
    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana", nullable = false)
    private DiaSemana diaSemana;

    //Hora de inicio de disponibilidad
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    //Hora de fin de disponibilidad
    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    //Modalidad de atención
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Modalidad modalidad;

    //Activo/Inactivo
    @Column(nullable = false)
    private Boolean activo = true;

    //Enum para días de la semana
    public enum DiaSemana {
        LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO
    }

    //Enum para modalidad de atención
    public enum Modalidad {
        PRESENCIAL, VIRTUAL, HIBRIDA
    }

    //Constructor vacío (requerido por JPA)
    public HorarioEntity() {
    }

    //Constructor con parámetros
    public HorarioEntity(Long id, ProgramadorPerfilEntity programador, DiaSemana diaSemana,
            LocalTime horaInicio, LocalTime horaFin, Modalidad modalidad, Boolean activo) {
        this.id = id;
        this.programador = programador;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.modalidad = modalidad;
        this.activo = activo;
    }

    //Getters
    public Long getId() {
        return id;
    }

    public ProgramadorPerfilEntity getProgramador() {
        return programador;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public Boolean getActivo() {
        return activo;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setProgramador(ProgramadorPerfilEntity programador) {
        this.programador = programador;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        HorarioEntity that = (HorarioEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
