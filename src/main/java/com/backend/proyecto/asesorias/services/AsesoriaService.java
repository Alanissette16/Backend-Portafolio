package com.backend.proyecto.asesorias.services;

import com.backend.proyecto.asesorias.dtos.CreateAsesoriaRequestDto;
import com.backend.proyecto.asesorias.dtos.UpdateAsesoriaRequestDto;
import com.backend.proyecto.asesorias.dtos.AsesoriaResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//Interfaz para el servicio de gestión de asesorías
public interface AsesoriaService {
    // Un usuario externo solicita una nueva asesoría
    AsesoriaResponseDto solicitarAsesoria(CreateAsesoriaRequestDto dto, Long usuarioExternoId);

    // Obtiene los detalles de una asesoría por su ID
    AsesoriaResponseDto obtenerAsesoriaPorId(Long id);

    // Obtiene todas las asesorías solicitadas por un usuario
    Page<AsesoriaResponseDto> obtenerMisAsesorias(Long usuarioExternoId, Pageable pageable);

    // Obtiene todas las asesorías asignadas a un programador
    Page<AsesoriaResponseDto> obtenerAsesoriasDeProgramador(Long programadorId, Pageable pageable);

    // Un programador actualiza el estado de la asesoría
    // (Confirmar/Rechazar/Completar)
    AsesoriaResponseDto actualizarEstadoAsesoria(Long id, UpdateAsesoriaRequestDto dto, Long usuarioId);

    // Cancela una solicitud de asesoría pendiente
    void cancelarAsesoria(Long id, Long usuarioExternoId);

    // Obtiene todas las asesorías del sistema (Para Admin/Reportes)
    Page<AsesoriaResponseDto> obtenerTodasLasAsesorias(Pageable pageable);

    // Elimina una asesoría del historial (Solo participantes)
    void eliminarAsesoria(Long id, Long usuarioId);
}
