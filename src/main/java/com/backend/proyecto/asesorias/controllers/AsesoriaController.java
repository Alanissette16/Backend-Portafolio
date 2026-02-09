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
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/asesorias")
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

    // Solicitar una asesoría (Solo EXTERNAL)
    @PostMapping
    public ResponseEntity<AsesoriaResponseDto> solicitarAsesoria(@Valid @RequestBody CreateAsesoriaRequestDto dto) {
        Long usuarioId = getCurrentUserId();
        AsesoriaResponseDto response = asesoriaService.solicitarAsesoria(dto, usuarioId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Ver mis asesorías (Solicitadas por mí) (Paginado)
    @GetMapping("/mias")
    public ResponseEntity<Page<AsesoriaResponseDto>> obtenerMisAsesorias(Pageable pageable) {
        Long usuarioId = getCurrentUserId();
        Page<AsesoriaResponseDto> response = asesoriaService.obtenerMisAsesorias(usuarioId, pageable);
        return ResponseEntity.ok(response);
    }

    // Ver asesorías recibidas (Solo PROGRAMMER) (Paginado)
    @GetMapping("/programador")
    public ResponseEntity<Page<AsesoriaResponseDto>> obtenerAsesoriasProgramador(Pageable pageable) {
        Long usuarioId = getCurrentUserId();
        // Obtener el ID del perfil de programador usando el ID de usuario
        ProgramadorResponseDto programador = programadorService.obtenerProgramadorPorUsuarioId(usuarioId);

        Page<AsesoriaResponseDto> response = asesoriaService.obtenerAsesoriasDeProgramador(programador.getId(),
                pageable);
        return ResponseEntity.ok(response);
    }

    // Gestionar asesoría (Solo PROGRAMMER)
    @PutMapping("/{id}/gestionar")
    public ResponseEntity<AsesoriaResponseDto> gestionarAsesoria(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAsesoriaRequestDto dto) {
        Long usuarioId = getCurrentUserId();
        AsesoriaResponseDto response = asesoriaService.actualizarEstadoAsesoria(id, dto, usuarioId);
        return ResponseEntity.ok(response);
    }

    // Cancelar solicitud (Solo EXTERNAL)
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarAsesoria(@PathVariable Long id) {
        Long usuarioId = getCurrentUserId();
        asesoriaService.cancelarAsesoria(id, usuarioId);
        return ResponseEntity.noContent().build();
    }

    // Listar todas las asesorías (Para Reportes/Admin) (Paginado)
    @GetMapping
    public ResponseEntity<Page<AsesoriaResponseDto>> listarTodasLasAsesorias(Pageable pageable) {
        // En un entorno real, aquí se validaría el rol ADMIN
        Page<AsesoriaResponseDto> response = asesoriaService.obtenerTodasLasAsesorias(pageable);
        return ResponseEntity.ok(response);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        UsuarioEntity usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "email", email));

        return usuario.getId();
    }

    // Eliminar una asesoría (Solo participantes)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAsesoria(@PathVariable Long id) {
        Long usuarioId = getCurrentUserId();
        asesoriaService.eliminarAsesoria(id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}