package com.backend.proyecto.Programadores.entities;

import com.backend.proyecto.Usuarios.entities.UsuarioEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

//Entidad que representa el perfil profesional de un programador
@Entity
@Table(name = "programador_perfil")
public class ProgramadorPerfilEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Relación uno a uno con Usuario
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private UsuarioEntity usuario;

    //Especialidad del programador
    @Column(length = 200)
    private String especialidad;

    //Biografía profesional
    @Column(length = 1000)
    private String biografia;

    //URL de LinkedIn
    @Column(name = "linkedin_url", length = 500)
    private String linkedinUrl;

    //URL de GitHub
    @Column(name = "github_url", length = 500)
    private String githubUrl;

    //Sitio web personal
    @Column(name = "sitio_web", length = 500)
    private String sitioWeb;

    //Tarifa por hora de asesoría
    @Column(precision = 10, scale = 2)
    private BigDecimal tarifa;

    //Constructor vacío (JPA)
    public ProgramadorPerfilEntity() {
    }

    //Constructor con parámetros
    public ProgramadorPerfilEntity(Long id, UsuarioEntity usuario, String especialidad,
            String biografia, String linkedinUrl, String githubUrl,
            String sitioWeb, BigDecimal tarifa) {
        this.id = id;
        this.usuario = usuario;
        this.especialidad = especialidad;
        this.biografia = biografia;
        this.linkedinUrl = linkedinUrl;
        this.githubUrl = githubUrl;
        this.sitioWeb = sitioWeb;
        this.tarifa = tarifa;
    }

    //Getters
    public Long getId() {
        return id;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public String getBiografia() {
        return biografia;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public BigDecimal getTarifa() {
        return tarifa;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public void setTarifa(BigDecimal tarifa) {
        this.tarifa = tarifa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ProgramadorPerfilEntity that = (ProgramadorPerfilEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
