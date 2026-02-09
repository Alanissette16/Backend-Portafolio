package com.backend.proyecto.Proyectos.dtos;

public class UpdateProyectoRequestDto {
    private String nombre;
    private String descripcion;
    private String tecnologias;
    private String urlRepositorio;
    private String urlDemo;
    private String imagenUrl;
    private String categoria;
    private Boolean activo;
    private Boolean destacado;

    //Constructor vacío
    public UpdateProyectoRequestDto() {
    }

    //Constructor con parámetros
    public UpdateProyectoRequestDto(String nombre, String descripcion, String tecnologias, String urlRepositorio,
            String urlDemo, String imagenUrl, Boolean activo, Boolean destacado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tecnologias = tecnologias;
        this.urlRepositorio = urlRepositorio;
        this.urlDemo = urlDemo;
        this.imagenUrl = imagenUrl;
        this.activo = activo;
        this.destacado = destacado;
    }

    //Getters y Setters
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
}
