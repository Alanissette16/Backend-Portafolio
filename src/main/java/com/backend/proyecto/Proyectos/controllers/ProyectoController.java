package com.backend.proyecto.Proyectos.controllers;

import com.backend.proyecto.Proyectos.dtos.CreateProyectoRequestDto;
import com.backend.proyecto.Proyectos.dtos.UpdateProyectoRequestDto;
import com.backend.proyecto.Proyectos.dtos.ProyectoResponseDto;
import com.backend.proyecto.Proyectos.services.ProyectoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Controlador REST para la gestión de proyectos
@RestController
@RequestMapping("/api/proyectos")
@Tag(name = "Proyectos", description = "API para gestión de proyectos de programadores")
@SecurityRequirement(name = "bearer-jwt")
public class ProyectoController {

    private final ProyectoService proyectoService;

    //Constructor para inyección de dependencias
    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    //Crea un nuevo proyecto
    @PostMapping
    @Operation(summary = "Crear proyecto")
    public ResponseEntity<ProyectoResponseDto> crearProyecto(@Valid @RequestBody CreateProyectoRequestDto dto) {
        ProyectoResponseDto response = proyectoService.crearProyecto(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //Obtiene un proyecto por su ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener proyecto por ID")
    public ResponseEntity<ProyectoResponseDto> obtenerProyectoPorId(@PathVariable Long id) {
        ProyectoResponseDto response = proyectoService.obtenerProyectoPorId(id);
        return ResponseEntity.ok(response);
    }

    //Obtiene todos los proyectos de un programador
    @GetMapping("/programador/{programadorId}")
    @Operation(summary = "Obtener proyectos de un programador")
    public ResponseEntity<List<ProyectoResponseDto>> obtenerProyectosPorProgramador(@PathVariable Long programadorId) {
        List<ProyectoResponseDto> response = proyectoService.obtenerProyectosPorProgramador(programadorId);
        return ResponseEntity.ok(response);
    }

    //Obtiene todos los proyectos públicos (sin autenticación requerida)
    @GetMapping("/publicos")
    @Operation(summary = "Obtener todos los proyectos públicos")
    public ResponseEntity<List<ProyectoResponseDto>> obtenerProyectosPublicos() {
        List<ProyectoResponseDto> response = proyectoService.obtenerTodosLosProyectos();
        return ResponseEntity.ok(response);
    }

    //Actualiza un proyecto existente
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar proyecto")
    public ResponseEntity<ProyectoResponseDto> actualizarProyecto(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProyectoRequestDto dto) {
        ProyectoResponseDto response = proyectoService.actualizarProyecto(id, dto);
        return ResponseEntity.ok(response);
    }

    //Elimina un proyecto por su ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar proyecto")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        proyectoService.eliminarProyecto(id);
        return ResponseEntity.noContent().build();
    }
}
