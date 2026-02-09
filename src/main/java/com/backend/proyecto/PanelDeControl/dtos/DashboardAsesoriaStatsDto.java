package com.backend.proyecto.PanelDeControl.dtos;

import java.util.Map;

//DTO para transferir estadísticas de asesorías
public class DashboardAsesoriaStatsDto {
    private Long totalAsesorias;
    private Long pendientes;
    private Long confirmadas;
    private Long completadas;
    private Long rechazadas;
    private Map<String, Long> asesoriasPorMes;

    public DashboardAsesoriaStatsDto() {
    }

    public DashboardAsesoriaStatsDto(Long totalAsesorias, Long pendientes, Long confirmadas, Long completadas,
            Long rechazadas, Map<String, Long> asesoriasPorMes) {
        this.totalAsesorias = totalAsesorias;
        this.pendientes = pendientes;
        this.confirmadas = confirmadas;
        this.completadas = completadas;
        this.rechazadas = rechazadas;
        this.asesoriasPorMes = asesoriasPorMes;
    }

    //Getters y Setters
    public Long getTotalAsesorias() {
        return totalAsesorias;
    }

    public void setTotalAsesorias(Long totalAsesorias) {
        this.totalAsesorias = totalAsesorias;
    }

    public Long getPendientes() {
        return pendientes;
    }

    public void setPendientes(Long pendientes) {
        this.pendientes = pendientes;
    }

    public Long getConfirmadas() {
        return confirmadas;
    }

    public void setConfirmadas(Long confirmadas) {
        this.confirmadas = confirmadas;
    }

    public Long getCompletadas() {
        return completadas;
    }

    public void setCompletadas(Long completadas) {
        this.completadas = completadas;
    }

    public Long getRechazadas() {
        return rechazadas;
    }

    public void setRechazadas(Long rechazadas) {
        this.rechazadas = rechazadas;
    }

    public Map<String, Long> getAsesoriasPorMes() {
        return asesoriasPorMes;
    }

    public void setAsesoriasPorMes(Map<String, Long> asesoriasPorMes) {
        this.asesoriasPorMes = asesoriasPorMes;
    }
}
