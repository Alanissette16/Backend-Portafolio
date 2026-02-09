package com.backend.proyecto.Proyectos.entities;

import com.backend.proyecto.Programadores.entities.ProgramadorPerfilEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

//Entidad que representa un proyecto de un programador
@Entity
@Table(name = "proyectos")
public class ProyectoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Relación con el programador
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programador_id", nullable = false)
    private ProgramadorPerfilEntity programador;

    //Nombre del proyecto
    @Column(nullable = false, length = 200)
    private String nombre;

    //Descripción del proyecto
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    //Tecnologías utilizadas (separadas por comas)
    @Column(columnDefinition = "TEXT")
    private String tecnologias;

    //URL del repositorio
    @Column(name = "url_repositorio", length = 500)
    private String urlRepositorio;

    //URL de la demo
    @Column(name = "url_demo", length = 500)
    private String urlDemo;

    //URL de imagen del proyecto (soporta Base64)
    @Column(name = "imagen_url", columnDefinition = "TEXT")
    private String imagenUrl;

    //Categoría del proyecto (académico o laboral)
    @Column(length = 50)
    private String categoria;

    //Proyecto activo o inactivo
    @Column(nullable = false)
    private Boolean activo = true;

    //Proyecto destacado
    @Column(nullable = false)
    private Boolean destacado = false;

    //Fecha de creación
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    //Constructor vacío (JPA)
    public ProyectoEntity() {
    }

    //Constructor con parámetros
    public ProyectoEntity(Long id, ProgramadorPerfilEntity programador, String nombre,
            String descripcion, String tecnologias, String urlRepositorio,
            String urlDemo, String imagenUrl, Boolean activo, Boolean destacado,
            LocalDateTime fechaCreacion) {
        this.id = id;
        this.programador = programador;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tecnologias = tecnologias;
        this.urlRepositorio = urlRepositorio;
        this.urlDemo = urlDemo;
        this.imagenUrl = imagenUrl;
        this.activo = activo;
        this.destacado = destacado;
        this.fechaCreacion = fechaCreacion;
    }

    //Getters
    public Long getId() {
        return id;
    }

    public ProgramadorPerfilEntity getProgramador() {
        return programador;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTecnologias() {
        return tecnologias;
    }

    public String getUrlRepositorio() {
        return urlRepositorio;
    }

    public String getUrlDemo() {
        return urlDemo;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public Boolean getActivo() {
        return activo;
    }

    public Boolean getDestacado() {
        return destacado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setProgramador(ProgramadorPerfilEntity programador) {
        this.programador = programador;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTecnologias(String tecnologias) {
        this.tecnologias = tecnologias;
    }

    public void setUrlRepositorio(String urlRepositorio) {
        this.urlRepositorio = urlRepositorio;
    }

    public void setUrlDemo(String urlDemo) {
        this.urlDemo = urlDemo;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public void setDestacado(Boolean destacado) {
        this.destacado = destacado;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ProyectoEntity that = (ProyectoEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
