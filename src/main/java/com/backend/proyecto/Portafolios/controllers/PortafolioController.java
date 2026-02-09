package com.backend.proyecto.Portafolios.controllers;

import com.backend.proyecto.Portafolios.dtos.CreatePortafolioRequestDto;
import com.backend.proyecto.Portafolios.dtos.UpdatePortafolioRequestDto;
import com.backend.proyecto.Portafolios.dtos.PortafolioResponseDto;
import com.backend.proyecto.Portafolios.services.PortafolioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Controlador REST para la gestión de secciones del portafolio
@RestController
@RequestMapping("/api/portafolios")
@Tag(name = "Portafolios", description = "API para gestión de secciones de portafolio")
@SecurityRequirement(name = "bearer-jwt")
public class PortafolioController {

    private final PortafolioService portafolioService;

    //Constructor para inyección de dependencias
    public PortafolioController(PortafolioService portafolioService) {
        this.portafolioService = portafolioService;
    }

    //Crea una nueva sección en el portafolio
    @PostMapping
    @Operation(summary = "Crear sección de portafolio")
    public ResponseEntity<PortafolioResponseDto> crearPortafolio(@Valid @RequestBody CreatePortafolioRequestDto dto) {
        PortafolioResponseDto response = portafolioService.crearPortafolio(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //Obtiene una sección del portafolio por su ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener portafolio por ID")
    public ResponseEntity<PortafolioResponseDto> obtenerPortafolioPorId(@PathVariable Long id) {
        PortafolioResponseDto response = portafolioService.obtenerPortafolioPorId(id);
        return ResponseEntity.ok(response);
    }

    //Obtiene todas las secciones del portafolio de un programador
    @GetMapping("/programador/{programadorId}")
    @Operation(summary = "Obtener portafolios de un programador")
    public ResponseEntity<List<PortafolioResponseDto>> obtenerPortafoliosPorProgramador(
            @PathVariable Long programadorId) {
        List<PortafolioResponseDto> response = portafolioService.obtenerPortafoliosPorProgramador(programadorId);
        return ResponseEntity.ok(response);
    }

    //Obtiene todas las secciones de portafolios visibles (público)
    @GetMapping("/publicos")
    @Operation(summary = "Obtener todos los portafolios públicos")
    public ResponseEntity<List<PortafolioResponseDto>> obtenerPortafoliosPublicos() {
        List<PortafolioResponseDto> response = portafolioService.obtenerTodosLosPortafolios();
        return ResponseEntity.ok(response);
    }

    //Actualiza una sección del portafolio existente
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar portafolio")
    public ResponseEntity<PortafolioResponseDto> actualizarPortafolio(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePortafolioRequestDto dto) {
        PortafolioResponseDto response = portafolioService.actualizarPortafolio(id, dto);
        return ResponseEntity.ok(response);
    }

    //Elimina una sección del portafolio
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar portafolio")
    public ResponseEntity<Void> eliminarPortafolio(@PathVariable Long id) {
        portafolioService.eliminarPortafolio(id);
        return ResponseEntity.noContent().build();
    }
}
