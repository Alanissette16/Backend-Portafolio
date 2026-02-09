package com.backend.proyecto.asesorias.controllers;

import com.backend.proyecto.asesorias.dtos.CreateAsesoriaRequestDto;
import com.backend.proyecto.asesorias.dtos.UpdateAsesoriaRequestDto;
import com.backend.proyecto.asesorias.dtos.AsesoriaResponseDto;
import com.backend.proyecto.asesorias.services.AsesoriaService;
import com.backend.proyecto.Programadores.services.ProgramadorService;
import com.backend.proyecto.Programadores.dtos.ProgramadorResponseDto;
import com.backend.proyecto.Usuarios.entities.UsuarioEntity;
import com.backend.proyecto.Usuarios.repositories.UsuarioRepository;
import com.backend.proyecto.exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asesorias")
@Tag(name = "Asesorías", description = "API para gestión de asesorías")
@SecurityRequirement(name = "bearer-jwt")
public class AsesoriaController {

    private final AsesoriaService asesoriaService;
    private final ProgramadorService programadorService;
    private final UsuarioRepository usuarioRepository;

    public AsesoriaController(AsesoriaService asesoriaService,
            ProgramadorService programadorService,
            UsuarioRepository usuarioRepository) {
        this.asesoriaService = asesoriaService;
        this.programadorService = programadorService;
        this.usuarioRepository = usuarioRepository;
    }

    //Solicitar una asesoría (Solo EXTERNAL)
    @PostMapping
    @Operation(summary = "Solicitar una asesoría (Solo EXTERNAL)", description = "Crea una solicitud de asesoría en estado PENDIENTE")
    public ResponseEntity<AsesoriaResponseDto> solicitarAsesoria(@Valid @RequestBody CreateAsesoriaRequestDto dto) {
        Long usuarioId = getCurrentUserId();
        AsesoriaResponseDto response = asesoriaService.solicitarAsesoria(dto, usuarioId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //Ver mis asesorías (Solicitadas por mí)
    @GetMapping("/mias")
    @Operation(summary = "Ver mis asesorías (Solicitadas por mí)", description = "Devuelve todas las asesorías del usuario logueado")
    public ResponseEntity<List<AsesoriaResponseDto>> obtenerMisAsesorias() {
        Long usuarioId = getCurrentUserId();
        List<AsesoriaResponseDto> response = asesoriaService.obtenerMisAsesorias(usuarioId);
        return ResponseEntity.ok(response);
    }

    //Ver asesorías recibidas (Solo PROGRAMMER)
    @GetMapping("/programador")
    @Operation(summary = "Ver asesorías recibidas (Solo PROGRAMMER)", description = "Devuelve todas las solicitudes recibidas por el programador logueado")
    public ResponseEntity<List<AsesoriaResponseDto>> obtenerAsesoriasProgramador() {
        Long usuarioId = getCurrentUserId();
        //Obtener el ID del perfil de programador usando el ID de usuario
        ProgramadorResponseDto programador = programadorService.obtenerProgramadorPorUsuarioId(usuarioId);

        List<AsesoriaResponseDto> response = asesoriaService.obtenerAsesoriasDeProgramador(programador.getId());
        return ResponseEntity.ok(response);
    }

    //Gestionar asesoría (Solo PROGRAMMER)
    @PutMapping("/{id}/gestionar")
    @Operation(summary = "Gestionar asesoría (Solo PROGRAMMER)", description = "Confirmar, Rechazar o actualizar una asesoría")
    public ResponseEntity<AsesoriaResponseDto> gestionarAsesoria(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAsesoriaRequestDto dto) {
        Long usuarioId = getCurrentUserId();
        AsesoriaResponseDto response = asesoriaService.actualizarEstadoAsesoria(id, dto, usuarioId);
        return ResponseEntity.ok(response);
    }

    //Cancelar solicitud (Solo EXTERNAL)
    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar solicitud (Solo EXTERNAL)", description = "Cancela una solicitud pendiente")
    public ResponseEntity<Void> cancelarAsesoria(@PathVariable Long id) {
        Long usuarioId = getCurrentUserId();
        asesoriaService.cancelarAsesoria(id, usuarioId);
        return ResponseEntity.noContent().build();
    }

    //Listar todas las asesorías (Para Reportes/Admin)
    @GetMapping
    @Operation(summary = "Listar todas las asesorías", description = "Devuelve todas las asesorías del sistema (Uso administrativo)")
    public ResponseEntity<List<AsesoriaResponseDto>> listarTodasLasAsesorias() {
        //En un entorno real, aquí se validaría el rol ADMIN
        List<AsesoriaResponseDto> response = asesoriaService.obtenerTodasLasAsesorias();
        return ResponseEntity.ok(response);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        UsuarioEntity usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "email", email));

        return usuario.getId();
    }
}