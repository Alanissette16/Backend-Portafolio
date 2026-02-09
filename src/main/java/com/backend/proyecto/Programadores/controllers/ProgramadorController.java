package com.backend.proyecto.Programadores.controllers;

import com.backend.proyecto.Programadores.dtos.CreateProgramadorRequestDto;
import com.backend.proyecto.Programadores.dtos.UpdateProgramadorRequestDto;
import com.backend.proyecto.Programadores.dtos.ProgramadorResponseDto;
import com.backend.proyecto.Programadores.services.ProgramadorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Controlador REST para programadores
//API para gestión de perfiles de programadores
@RestController
@RequestMapping("/api/programadores")
public class ProgramadorController {

    private final ProgramadorService programadorService;

    // Constructor para inyección de dependencias
    public ProgramadorController(ProgramadorService programadorService) {
        this.programadorService = programadorService;
    }

    // Crea un perfil de programador (Solo ADMIN)
    @PostMapping
    public ResponseEntity<ProgramadorResponseDto> crearProgramador(
            @Valid @RequestBody CreateProgramadorRequestDto dto) {
        ProgramadorResponseDto response = programadorService.crearProgramador(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Obtiene todos los programadores (Público) (Paginado)
    @GetMapping
    public ResponseEntity<Page<ProgramadorResponseDto>> obtenerTodosProgramadores(Pageable pageable) {
        Page<ProgramadorResponseDto> response = programadorService.obtenerTodosProgramadores(pageable);
        return ResponseEntity.ok(response);
    }

    // Obtiene un programador por su ID (Público)
    @GetMapping("/{id}")
    public ResponseEntity<ProgramadorResponseDto> obtenerProgramadorPorId(@PathVariable Long id) {
        ProgramadorResponseDto response = programadorService.obtenerProgramadorPorId(id);
        return ResponseEntity.ok(response);
    }

    // Actualiza el perfil de un programador
    @PutMapping("/{id}")
    public ResponseEntity<ProgramadorResponseDto> actualizarProgramador(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProgramadorRequestDto dto) {
        ProgramadorResponseDto response = programadorService.actualizarProgramador(id, dto);
        return ResponseEntity.ok(response);
    }

    // Elimina el perfil de un programador por su ID (Solo ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProgramador(@PathVariable Long id) {
        programadorService.eliminarProgramador(id);
        return ResponseEntity.noContent().build();
    }

    // Obtiene un programador por el ID del usuario asociado
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<ProgramadorResponseDto> obtenerProgramadorPorUsuarioId(@PathVariable Long usuarioId) {
        ProgramadorResponseDto response = programadorService.obtenerProgramadorPorUsuarioId(usuarioId);
        return ResponseEntity.ok(response);
    }
}
