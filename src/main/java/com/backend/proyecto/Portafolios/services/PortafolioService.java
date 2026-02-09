package com.backend.proyecto.Portafolios.services;

import com.backend.proyecto.Portafolios.dtos.CreatePortafolioRequestDto;
import com.backend.proyecto.Portafolios.dtos.UpdatePortafolioRequestDto;
import com.backend.proyecto.Portafolios.dtos.PortafolioResponseDto;
import java.util.List;

//Interfaz para el servicio de gestión de portafolios
public interface PortafolioService {
    //Crea una nueva sección de portafolio para un programador
    PortafolioResponseDto crearPortafolio(CreatePortafolioRequestDto dto);

    //Obtiene una sección de portafolio por su ID
    PortafolioResponseDto obtenerPortafolioPorId(Long id);

    //Lista todas las secciones de portafolio de un programador específico
    List<PortafolioResponseDto> obtenerPortafoliosPorProgramador(Long programadorId);

    //Actualiza una sección de portafolio existente
    PortafolioResponseDto actualizarPortafolio(Long id, UpdatePortafolioRequestDto dto);

    //Elimina una sección de portafolio por su ID
    void eliminarPortafolio(Long id);

    //Obtiene todas las secciones de portafolio visibles de todos los programadores
    List<PortafolioResponseDto> obtenerTodosLosPortafolios();
}
