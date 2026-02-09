package com.backend.proyecto.Proyectos.dtos;

import java.time.LocalDateTime;

public class ProyectoResponseDto {
    private Long id;
    private Long programadorId;
    private String programadorNombre;
    private String nombre;
    private String descripcion;
    private String tecnologias;
    private String urlRepositorio;
    private String urlDemo;
    private String imagenUrl;
    private String categoria;
    private Boolean activo;
    private Boolean destacado;
    private LocalDateTime fechaCreacion;

    //Constructor vacío
    public ProyectoResponseDto() {
    }

    //Constructor con parámetros
    public ProyectoResponseDto(Long id, Long programadorId, String programadorNombre, String nombre, String descripcion,
            String tecnologias, String urlRepositorio, String urlDemo, String imagenUrl, Boolean activo,
            Boolean destacado, LocalDateTime fechaCreacion) {
        this.id = id;
        this.programadorId = programadorId;
        this.programadorNombre = programadorNombre;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTecnologias() {
        return tecnologias;
    }

    public void setTecnologias(String tecnologias) {
        this.tecnologias = tecnologias;
    }

    public String getUrlRepositorio() {
        return urlRepositorio;
    }

    public void setUrlRepositorio(String urlRepositorio) {
        this.urlRepositorio = urlRepositorio;
    }

    public String getUrlDemo() {
        return urlDemo;
    }

    public void setUrlDemo(String urlDemo) {
        this.urlDemo = urlDemo;
    }

    public String getImagenUrl() {
        return imagenUrl;
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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Boolean getDestacado() {
        return destacado;
    }

    public void setDestacado(Boolean destacado) {
        this.destacado = destacado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
