package com.backend.proyecto.PanelDeControl.controllers;

import com.backend.proyecto.PanelDeControl.dtos.DashboardAsesoriaStatsDto;
import com.backend.proyecto.PanelDeControl.dtos.DashboardProyectoStatsDto;
import com.backend.proyecto.PanelDeControl.services.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

//Controlador para el panel de control (Dashboard)
//Provee endpoints para estadísticas y métricas del sistema
@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "Panel de Control", description = "Endpoints para estadísticas y métricas")
@SecurityRequirement(name = "bearer-jwt")
public class DashboardController {

    private final DashboardService dashboardService;

    //Constructor para inyección de dependencias
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    //Obtiene estadísticas de asesorías
    @GetMapping("/asesorias/stats")
    @Operation(summary = "Estadísticas de Asesorías", description = "Obtener métricas de asesorías, filtradas opcionalmente")
    public ResponseEntity<DashboardAsesoriaStatsDto> getAsesoriaStats(
            @RequestParam(required = false) Long programadorId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {

        DashboardAsesoriaStatsDto stats = dashboardService.obtenerEstadisticasAsesorias(programadorId, desde, hasta);
        return ResponseEntity.ok(stats);
    }

    //Obtiene estadísticas de proyectos
    @GetMapping("/proyectos/stats")
    @Operation(summary = "Estadísticas de Proyectos", description = "Obtener métricas de proyectos")
    public ResponseEntity<DashboardProyectoStatsDto> getProyectoStats(
            @RequestParam(required = false) Long programadorId) {

        DashboardProyectoStatsDto stats = dashboardService.obtenerEstadisticasProyectos(programadorId);
        return ResponseEntity.ok(stats);
    }
}
