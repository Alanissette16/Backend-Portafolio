package com.backend.proyecto.Programadores.controllers;

import com.backend.proyecto.Programadores.dtos.CreateProgramadorRequestDto;
import com.backend.proyecto.Programadores.dtos.UpdateProgramadorRequestDto;
import com.backend.proyecto.Programadores.dtos.ProgramadorResponseDto;
import com.backend.proyecto.Programadores.services.ProgramadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Controlador REST para programadores
//API para gestión de perfiles de programadores
@RestController
@RequestMapping("/api/programadores")
@Tag(name = "Programadores", description = "API para gestión de perfiles de programadores")
public class ProgramadorController {

    private final ProgramadorService programadorService;

    //Constructor para inyección de dependencias
    public ProgramadorController(ProgramadorService programadorService) {
        this.programadorService = programadorService;
    }

    //Crea un perfil de programador (Solo ADMIN)
    @PostMapping
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Crear perfil de programador", description = "Solo ADMIN puede crear (vincula usuario a perfil)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Perfil creado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "409", description = "Ya existe perfil para este usuario")
    })
    public ResponseEntity<ProgramadorResponseDto> crearProgramador(
            @Valid @RequestBody CreateProgramadorRequestDto dto) {
        ProgramadorResponseDto response = programadorService.crearProgramador(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //Obtiene todos los programadores (Público)
    @GetMapping
    @Operation(summary = "Obtener todos los programadores", description = "Endpoint público para ver todos los programadores")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    public ResponseEntity<List<ProgramadorResponseDto>> obtenerTodosProgramadores() {
        List<ProgramadorResponseDto> response = programadorService.obtenerTodosProgramadores();
        return ResponseEntity.ok(response);
    }

    //Obtiene un programador por su ID (Público)
    @GetMapping("/{id}")
    @Operation(summary = "Obtener programador por ID", description = "Endpoint público para ver perfil de programador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Programador encontrado"),
            @ApiResponse(responseCode = "404", description = "Programador no encontrado")
    })
    public ResponseEntity<ProgramadorResponseDto> obtenerProgramadorPorId(@PathVariable Long id) {
        ProgramadorResponseDto response = programadorService.obtenerProgramadorPorId(id);
        return ResponseEntity.ok(response);
    }

    //Actualiza el perfil de un programador
    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Actualizar perfil de programador", description = "PROGRAMMER puede actualizar su propio perfil, ADMIN puede actualizar cualquiera")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Programador no encontrado")
    })
    public ResponseEntity<ProgramadorResponseDto> actualizarProgramador(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProgramadorRequestDto dto) {
        ProgramadorResponseDto response = programadorService.actualizarProgramador(id, dto);
        return ResponseEntity.ok(response);
    }

    //Elimina el perfil de un programador por su ID (Solo ADMIN)
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearer-jwt")
    @Operation(summary = "Eliminar perfil de programador", description = "Solo ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Perfil eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Programador no encontrado")
    })
    public ResponseEntity<Void> eliminarProgramador(@PathVariable Long id) {
        programadorService.eliminarProgramador(id);
        return ResponseEntity.noContent().build();
    }

    //Obtiene un programador por el ID del usuario asociado
    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Obtener programador por ID de usuario", description = "Buscar perfil de programador de un usuario específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Programador encontrado"),
            @ApiResponse(responseCode = "404", description = "Programador no encontrado")
    })
    public ResponseEntity<ProgramadorResponseDto> obtenerProgramadorPorUsuarioId(@PathVariable Long usuarioId) {
        ProgramadorResponseDto response = programadorService.obtenerProgramadorPorUsuarioId(usuarioId);
        return ResponseEntity.ok(response);
    }
}
