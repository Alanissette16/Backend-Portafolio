package com.backend.proyecto.Portafolios.dtos;

//DTO para respuesta de portafolio
public class PortafolioResponseDto {

    private Long id;
    private Long programadorId;
    private String programadorNombre;
    private String titulo;
    private String descripcion;
    private String contenido;
    private Integer orden;
    private Boolean visible;

    //Constructor vacío
    public PortafolioResponseDto() {
    }

    //Constructor con parámetros
    public PortafolioResponseDto(Long id, Long programadorId, String programadorNombre,
            String titulo, String descripcion, String contenido,
            Integer orden, Boolean visible) {
        this.id = id;
        this.programadorId = programadorId;
        this.programadorNombre = programadorNombre;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.contenido = contenido;
        this.orden = orden;
        this.visible = visible;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
