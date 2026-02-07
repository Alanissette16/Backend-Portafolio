package com.backend.proyecto.asesorias.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="asesorias")
public class AsesoriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Usuario que actúa como programador/mentor en la sesión
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programador_id", insertable = false, updatable = false)
    //private UserEntity programador; --- IMPORTANTE DESCOMENTAR AL CREAR USER ENTITY ---

    // UID del programador asignado
    @Column(name = "programador_id", nullable = false)
    private String programadorId;

    @Column(name = "programmer_email")
    private String programadorEmail;

    @Column(name = "programmer_name")
    private String programadorName;

    // Nombre del usuario que solicita la asesoría
    @Column(name = "requester_name", nullable = false)
    private String solicitanteName;

    @Column(name = "requester_email", nullable = false)
    private String solicitanteEmail;

    // Detalles de agenda de la sesión
    private String date; // Fecha programada para la asesoría
    private String time; // Hora pactada para el encuentro

    @Column(length = 1000)
    private String nota; // Nota o descripción de la asesoría

    @Enumerated(EnumType.STRING)
    private Status status = Status.pending; // Estado de la solicitud

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Enumeración de los estados posibles de una solicitud
    public enum Status {
        pending, // Esperando respuesta del programador
        approved, // Aceptada y programada
        rejected // Denegada por el mentor
    }

    // Constructor por defecto (requerido por JPA)
    public AsesoriaEntity() {
    }

    // Constructor con todos los campos
    public AsesoriaEntity(Long id, String programadorId, String programadorEmail, String programadorName,
                          String solicitanteName, String solicitanteEmail, String date, String time,
                          String nota, Status status, LocalDateTime createdAt) {
        this.id = id;
        this.programadorId = programadorId;
        this.programadorEmail = programadorEmail;
        this.programadorName = programadorName;
        this.solicitanteName = solicitanteName;
        this.solicitanteEmail = solicitanteEmail;
        this.date = date;
        this.time = time;
        this.nota = nota;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getProgramadorId() {
        return programadorId;
    }

    public String getProgramadorEmail() {
        return programadorEmail;
    }

    public String getProgramadorName() {
        return programadorName;
    }

    public String getSolicitanteName() {
        return solicitanteName;
    }

    public String getSolicitanteEmail() {
        return solicitanteEmail;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getNota() {
        return nota;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setProgramadorId(String programadorId) {
        this.programadorId = programadorId;
    }

    public void setProgramadorEmail(String programadorEmail) {
        this.programadorEmail = programadorEmail;
    }

    public void setProgramadorName(String programadorName) {
        this.programadorName = programadorName;
    }

    public void setSolicitanteName(String solicitanteName) {
        this.solicitanteName = solicitanteName;
    }

    public void setSolicitanteEmail(String solicitanteEmail) {
        this.solicitanteEmail = solicitanteEmail;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // equals() basado en el ID (buena práctica JPA)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AsesoriaEntity that = (AsesoriaEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    // hashCode() constante (buena práctica JPA para entidades con ID generado)
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "AsesoriaEntity{" +
                "id=" + id +
                ", programadorId='" + programadorId + '\'' +
                ", programadorEmail='" + programadorEmail + '\'' +
                ", programadorName='" + programadorName + '\'' +
                ", solicitanteName='" + solicitanteName + '\'' +
                ", solicitanteEmail='" + solicitanteEmail + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", nota='" + nota + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
