package com.backend.proyecto.PanelDeControl.services.impl;

import com.backend.proyecto.PanelDeControl.dtos.DashboardAsesoriaStatsDto;
import com.backend.proyecto.PanelDeControl.dtos.DashboardProyectoStatsDto;
import com.backend.proyecto.PanelDeControl.services.DashboardService;
import com.backend.proyecto.asesorias.entities.AsesoriaEntity;
import com.backend.proyecto.asesorias.repositories.AsesoriaRepository;
import com.backend.proyecto.Proyectos.entities.ProyectoEntity;
import com.backend.proyecto.Proyectos.repositories.ProyectoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//Implementación del servicio de dashboard
@Service
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final AsesoriaRepository asesoriaRepository;
    private final ProyectoRepository proyectoRepository;

    //Constructor para inyección de dependencias
    public DashboardServiceImpl(AsesoriaRepository asesoriaRepository,
            ProyectoRepository proyectoRepository) {
        this.asesoriaRepository = asesoriaRepository;
        this.proyectoRepository = proyectoRepository;
    }

    @Override
    public DashboardAsesoriaStatsDto obtenerEstadisticasAsesorias(Long programadorId, LocalDate desde,
            LocalDate hasta) {
        //Obtener asesorías del programador o todas si es admin
        List<AsesoriaEntity> asesorias;
        if (programadorId != null) {
            asesorias = asesoriaRepository.findByProgramadorId(programadorId);
        } else {
            asesorias = asesoriaRepository.findAll();
        }

        //Filtrar por fecha si se proporcionan
        if (desde != null) {
            asesorias = asesorias.stream()
                    .filter(a -> !a.getFecha().isBefore(desde))
                    .collect(Collectors.toList());
        }
        if (hasta != null) {
            asesorias = asesorias.stream()
                    .filter(a -> !a.getFecha().isAfter(hasta))
                    .collect(Collectors.toList());
        }

        //Calcular estadísticas
        long total = asesorias.size();
        long pendientes = asesorias.stream().filter(a -> a.getEstado() == AsesoriaEntity.EstadoAsesoria.PENDIENTE)
                .count();
        long confirmadas = asesorias.stream().filter(a -> a.getEstado() == AsesoriaEntity.EstadoAsesoria.CONFIRMADA)
                .count();
        long completadas = asesorias.stream().filter(a -> a.getEstado() == AsesoriaEntity.EstadoAsesoria.COMPLETADA)
                .count();
        long rechazadas = asesorias.stream().filter(a -> a.getEstado() == AsesoriaEntity.EstadoAsesoria.RECHAZADA)
                .count();

        //Agrupar por mes (YYYY-MM)
        Map<String, Long> porMes = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        for (AsesoriaEntity a : asesorias) {
            String mes = a.getFecha().format(formatter);
            porMes.put(mes, porMes.getOrDefault(mes, 0L) + 1);
        }

        return new DashboardAsesoriaStatsDto(total, pendientes, confirmadas, completadas, rechazadas, porMes);
    }

    @Override
    public DashboardProyectoStatsDto obtenerEstadisticasProyectos(Long programadorId) {
        List<ProyectoEntity> proyectos;
        if (programadorId != null) {
            proyectos = proyectoRepository.findByProgramadorId(programadorId);
        } else {
            proyectos = proyectoRepository.findAll();
        }

        long total = proyectos.size();
        long activos = proyectos.stream().filter(ProyectoEntity::getActivo).count();
        long inactivos = total - activos;
        long destacados = proyectos.stream().filter(ProyectoEntity::getDestacado).count();

        return new DashboardProyectoStatsDto(total, activos, inactivos, destacados);
    }
}
