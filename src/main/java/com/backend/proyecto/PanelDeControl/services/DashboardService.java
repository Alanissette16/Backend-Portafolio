package com.backend.proyecto.PanelDeControl.services;

import com.backend.proyecto.PanelDeControl.dtos.DashboardAsesoriaStatsDto;
import com.backend.proyecto.PanelDeControl.dtos.DashboardProyectoStatsDto;

import java.time.LocalDate;

//Interfaz para el servicio de dashboard
public interface DashboardService {
    //Obtiene estadísticas de asesorías con filtros opcionales
    DashboardAsesoriaStatsDto obtenerEstadisticasAsesorias(Long programadorId, LocalDate desde, LocalDate hasta);

    //Obtiene estadísticas de proyectos filtradas por programador (opcional)
    DashboardProyectoStatsDto obtenerEstadisticasProyectos(Long programadorId);
}
