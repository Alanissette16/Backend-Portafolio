package com.backend.proyecto.Portafolios.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

//DTO para crear sección de portafolio
public class CreatePortafolioRequestDto {

    @NotNull(message = "El ID del programador es obligatorio")
    private Long programadorId;

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    private String titulo;

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;

    @Size(max = 5000, message = "El contenido no puede exceder 5000 caracteres")
    private String contenido;

    private Integer orden = 0;

    private Boolean visible = true;

    //Constructor vacío
    public CreatePortafolioRequestDto() {
    }

    //Constructor con parámetros
    public CreatePortafolioRequestDto(Long programadorId, String titulo, String descripcion,
            String contenido, Integer orden, Boolean visible) {
        this.programadorId = programadorId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.contenido = contenido;
        this.orden = orden;
        this.visible = visible;
    }

    //Getters y Setters
    public Long getProgramadorId() {
        return programadorId;
    }

    public void setProgramadorId(Long programadorId) {
        this.programadorId = programadorId;
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
