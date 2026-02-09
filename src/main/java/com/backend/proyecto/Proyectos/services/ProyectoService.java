package com.backend.proyecto.Proyectos.services;

import com.backend.proyecto.Proyectos.dtos.CreateProyectoRequestDto;
import com.backend.proyecto.Proyectos.dtos.UpdateProyectoRequestDto;
import com.backend.proyecto.Proyectos.dtos.ProyectoResponseDto;
import java.util.List;

//Interfaz para el servicio de gestión de proyectos
public interface ProyectoService {
    //Crea un nuevo proyecto para un programador
    ProyectoResponseDto crearProyecto(CreateProyectoRequestDto dto);

    //Obtiene un proyecto por su ID
    ProyectoResponseDto obtenerProyectoPorId(Long id);

    //Lista todos los proyectos de un programador específico
    List<ProyectoResponseDto> obtenerProyectosPorProgramador(Long programadorId);

    //Actualiza un proyecto existente
    ProyectoResponseDto actualizarProyecto(Long id, UpdateProyectoRequestDto dto);

    //Obtiene todos los proyectos del sistema
    List<ProyectoResponseDto> obtenerTodosLosProyectos();

    //Elimina un proyecto por su ID
    void eliminarProyecto(Long id);
}
