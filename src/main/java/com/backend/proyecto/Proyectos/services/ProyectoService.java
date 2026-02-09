package com.backend.proyecto.Proyectos.services;

import com.backend.proyecto.Proyectos.dtos.CreateProyectoRequestDto;
import com.backend.proyecto.Proyectos.dtos.UpdateProyectoRequestDto;
import com.backend.proyecto.Proyectos.dtos.ProyectoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//Interfaz para el servicio de gestión de proyectos
public interface ProyectoService {
    // Crea un nuevo proyecto para un programador
    ProyectoResponseDto crearProyecto(CreateProyectoRequestDto dto);

    // Obtiene un proyecto por su ID
    ProyectoResponseDto obtenerProyectoPorId(Long id);

    // Lista todos los proyectos de un programador específico (Paginado)
    Page<ProyectoResponseDto> obtenerProyectosPorProgramador(Long programadorId, Pageable pageable);

    // Actualiza un proyecto existente
    ProyectoResponseDto actualizarProyecto(Long id, UpdateProyectoRequestDto dto);

    // Obtiene todos los proyectos del sistema (Paginado)
    Page<ProyectoResponseDto> obtenerTodosLosProyectos(Pageable pageable);

    // Elimina un proyecto por su ID
    void eliminarProyecto(Long id);
}
