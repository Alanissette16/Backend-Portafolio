package com.backend.proyecto.Horarios.services;

import com.backend.proyecto.Horarios.dtos.CreateHorarioRequestDto;
import com.backend.proyecto.Horarios.dtos.UpdateHorarioRequestDto;
import com.backend.proyecto.Horarios.dtos.HorarioResponseDto;
import java.util.List;

//Interfaz para el servicio de gestión de horarios
public interface HorarioService {
    //Crea un nuevo horario
    HorarioResponseDto crearHorario(CreateHorarioRequestDto dto);

    //Obtiene un horario por su ID
    HorarioResponseDto obtenerHorarioPorId(Long id);

    //Obtiene todos los horarios de un programador
    List<HorarioResponseDto> obtenerHorariosPorProgramador(Long programadorId);

    //Actualiza un horario existente
    HorarioResponseDto actualizarHorario(Long id, UpdateHorarioRequestDto dto);

    //Elimina un horario por su ID
    void eliminarHorario(Long id);

    //Reemplaza todos los horarios de un programador
    List<HorarioResponseDto> reemplazarHorarios(Long programadorId, List<CreateHorarioRequestDto> dtos);
}
