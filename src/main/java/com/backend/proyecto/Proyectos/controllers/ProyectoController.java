package com.backend.proyecto.Proyectos.controllers;

import com.backend.proyecto.Proyectos.dtos.CreateProyectoRequestDto;
import com.backend.proyecto.Proyectos.dtos.UpdateProyectoRequestDto;
import com.backend.proyecto.Proyectos.dtos.ProyectoResponseDto;
import com.backend.proyecto.Proyectos.services.ProyectoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Controlador REST para la gestión de proyectos
@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    private final ProyectoService proyectoService;

    // Constructor para inyección de dependencias
    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    // Crea un nuevo proyecto
    @PostMapping
    public ResponseEntity<ProyectoResponseDto> crearProyecto(@Valid @RequestBody CreateProyectoRequestDto dto) {
        ProyectoResponseDto response = proyectoService.crearProyecto(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Obtiene un proyecto por su ID
    @GetMapping("/{id}")
    public ResponseEntity<ProyectoResponseDto> obtenerProyectoPorId(@PathVariable Long id) {
        ProyectoResponseDto response = proyectoService.obtenerProyectoPorId(id);
        return ResponseEntity.ok(response);
    }

    // Obtiene todos los proyectos de un programador (Paginado)
    @GetMapping("/programador/{programadorId}")
    public ResponseEntity<Page<ProyectoResponseDto>> obtenerProyectosPorProgramador(
            @PathVariable Long programadorId,
            Pageable pageable) {
        Page<ProyectoResponseDto> response = proyectoService.obtenerProyectosPorProgramador(programadorId, pageable);
        return ResponseEntity.ok(response);
    }

    // Obtiene todos los proyectos públicos (sin autenticación requerida) (Paginado)
    @GetMapping("/publicos")
    public ResponseEntity<Page<ProyectoResponseDto>> obtenerProyectosPublicos(Pageable pageable) {
        Page<ProyectoResponseDto> response = proyectoService.obtenerTodosLosProyectos(pageable);
        return ResponseEntity.ok(response);
    }

    // Actualiza un proyecto existente
    @PutMapping("/{id}")
    public ResponseEntity<ProyectoResponseDto> actualizarProyecto(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProyectoRequestDto dto) {
        ProyectoResponseDto response = proyectoService.actualizarProyecto(id, dto);
        return ResponseEntity.ok(response);
    }

    // Elimina un proyecto por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        proyectoService.eliminarProyecto(id);
        return ResponseEntity.noContent().build();
    }
}
