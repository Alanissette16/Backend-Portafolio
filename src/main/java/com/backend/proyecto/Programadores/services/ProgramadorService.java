package com.backend.proyecto.Programadores.services;

import com.backend.proyecto.Programadores.dtos.CreateProgramadorRequestDto;
import com.backend.proyecto.Programadores.dtos.UpdateProgramadorRequestDto;
import com.backend.proyecto.Programadores.dtos.ProgramadorResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//Interfaz de servicio para la gestión de perfiles de programador
public interface ProgramadorService {
    // Crea un nuevo perfil de programador
    ProgramadorResponseDto crearProgramador(CreateProgramadorRequestDto dto);

    // Obtiene un perfil de programador por su ID
    ProgramadorResponseDto obtenerProgramadorPorId(Long id);

    // Obtiene todos los perfiles de programador registrados (Paginado)
    Page<ProgramadorResponseDto> obtenerTodosProgramadores(Pageable pageable);

    // Actualiza un perfil de programador existente
    ProgramadorResponseDto actualizarProgramador(Long id, UpdateProgramadorRequestDto dto);

    // Elimina un perfil de programador por su ID
    void eliminarProgramador(Long id);

    // Obtiene el perfil de programador asociado a un usuario específico
    ProgramadorResponseDto obtenerProgramadorPorUsuarioId(Long usuarioId);
}
