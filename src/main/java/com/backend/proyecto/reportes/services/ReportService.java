package com.backend.proyecto.reportes.services;

import java.time.LocalDate;

//Interfaz de servicio para la generación de reportes
public interface ReportService {
    //Generar reporte de asesorías filtrado por programador y rango de fechas
    byte[] generarReporteAsesoriasPdf(Long programadorId, LocalDate desde, LocalDate hasta);

    //Generar reporte de proyectos filtrado por programador y estado
    //activo/inactivo
    byte[] generarReporteProyectosPdf(Long programadorId, Boolean activo);
}
