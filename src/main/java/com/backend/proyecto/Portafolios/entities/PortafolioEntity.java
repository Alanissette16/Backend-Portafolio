package com.backend.proyecto.Portafolios.entities;

import com.backend.proyecto.Programadores.entities.ProgramadorPerfilEntity;
import jakarta.persistence.*;
import java.util.Objects;

//Entidad que representa una sección del portafolio de un programador
@Entity
@Table(name = "portafolios")
public class PortafolioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Relación con el programador
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programador_id", nullable = false)
    private ProgramadorPerfilEntity programador;

    //Título de la sección
    @Column(nullable = false, length = 200)
    private String titulo;

    //Descripción breve
    @Column(length = 500)
    private String descripcion;

    //Contenido detallado (puede ser HTML, Markdown, etc.)
    @Column(length = 5000)
    private String contenido;

    //Orden de visualización
    @Column(nullable = false)
    private Integer orden = 0;

    //Visible o no en el portafolio público
    @Column(nullable = false)
    private Boolean visible = true;

    //Constructor vacío (JPA)
    public PortafolioEntity() {
    }

    //Constructor con parámetros
    public PortafolioEntity(Long id, ProgramadorPerfilEntity programador, String titulo,
            String descripcion, String contenido, Integer orden, Boolean visible) {
        this.id = id;
        this.programador = programador;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.contenido = contenido;
        this.orden = orden;
        this.visible = visible;
    }

    //Getters
    public Long getId() {
        return id;
    }

    public ProgramadorPerfilEntity getProgramador() {
        return programador;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getContenido() {
        return contenido;
    }

    public Integer getOrden() {
        return orden;
    }

    public Boolean getVisible() {
        return visible;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setProgramador(ProgramadorPerfilEntity programador) {
        this.programador = programador;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PortafolioEntity that = (PortafolioEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
