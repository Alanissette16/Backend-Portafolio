package com.backend.proyecto.Horarios.controllers;

import com.backend.proyecto.Horarios.dtos.CreateHorarioRequestDto;
import com.backend.proyecto.Horarios.dtos.UpdateHorarioRequestDto;
import com.backend.proyecto.Horarios.dtos.HorarioResponseDto;
import com.backend.proyecto.Horarios.services.HorarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Controlador REST para la gestión de horarios
//API para gestión de disponibilidad de programadores
@RestController
@RequestMapping("/api/horarios")
public class HorarioController {

    private final HorarioService horarioService;

    // Constructor para inyección de dependencias
    public HorarioController(HorarioService horarioService) {
        this.horarioService = horarioService;
    }

    // Crea un nuevo horario de disponibilidad
    @PostMapping
    public ResponseEntity<HorarioResponseDto> crearHorario(@Valid @RequestBody CreateHorarioRequestDto dto) {
        HorarioResponseDto response = horarioService.crearHorario(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Obtiene un horario por su ID
    @GetMapping("/{id}")
    public ResponseEntity<HorarioResponseDto> obtenerHorarioPorId(@PathVariable Long id) {
        HorarioResponseDto response = horarioService.obtenerHorarioPorId(id);
        return ResponseEntity.ok(response);
    }

    // Obtiene todos los horarios de un programador específico
    @GetMapping("/programador/{programadorId}")
    public ResponseEntity<List<HorarioResponseDto>> obtenerHorariosPorProgramador(@PathVariable Long programadorId) {
        List<HorarioResponseDto> response = horarioService.obtenerHorariosPorProgramador(programadorId);
        return ResponseEntity.ok(response);
    }

    // Actualiza un horario existente
    @PutMapping("/{id}")
    public ResponseEntity<HorarioResponseDto> actualizarHorario(
            @PathVariable Long id,
            @Valid @RequestBody UpdateHorarioRequestDto dto) {
        HorarioResponseDto response = horarioService.actualizarHorario(id, dto);
        return ResponseEntity.ok(response);
    }

    // Elimina un horario por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHorario(@PathVariable Long id) {
        horarioService.eliminarHorario(id);
        return ResponseEntity.noContent().build();
    }

    // Reemplaza todos los horarios de un programador
    @PutMapping("/programador/{programadorId}")
    public ResponseEntity<List<HorarioResponseDto>> reemplazarHorarios(
            @PathVariable Long programadorId,
            @Valid @RequestBody List<CreateHorarioRequestDto> dtos) {
        List<HorarioResponseDto> response = horarioService.reemplazarHorarios(programadorId, dtos);
        return ResponseEntity.ok(response);
    }
}
