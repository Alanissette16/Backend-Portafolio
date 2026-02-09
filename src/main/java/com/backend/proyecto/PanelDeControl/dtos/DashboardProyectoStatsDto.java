package com.backend.proyecto.PanelDeControl.dtos;

//DTO para transferir estadísticas de proyectos
public class DashboardProyectoStatsDto {
    private Long totalProyectos;
    private Long activos;
    private Long inactivos;
    private Long destacados;

    public DashboardProyectoStatsDto() {
    }

    public DashboardProyectoStatsDto(Long totalProyectos, Long activos, Long inactivos, Long destacados) {
        this.totalProyectos = totalProyectos;
        this.activos = activos;
        this.inactivos = inactivos;
        this.destacados = destacados;
    }

    //Getters y Setters
    public Long getTotalProyectos() {
        return totalProyectos;
    }

    public void setTotalProyectos(Long totalProyectos) {
        this.totalProyectos = totalProyectos;
    }

    public Long getActivos() {
        return activos;
    }

    public void setActivos(Long activos) {
        this.activos = activos;
    }

    public Long getInactivos() {
        return inactivos;
    }

    public void setInactivos(Long inactivos) {
        this.inactivos = inactivos;
    }

    public Long getDestacados() {
        return destacados;
    }

    public void setDestacados(Long destacados) {
        this.destacados = destacados;
    }
}
