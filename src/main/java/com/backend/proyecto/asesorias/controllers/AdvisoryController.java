package com.backend.proyecto.asesorias.controllers;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.proyecto.asesorias.dtos.AsesoriaRequestDto;
import com.backend.proyecto.asesorias.dtos.AsesoriaResponseDto;
import com.backend.proyecto.asesorias.entities.AsesoriaEntity;
import com.backend.proyecto.asesorias.mappers.AsesoriaMapper;
import com.backend.proyecto.asesorias.models.Asesoria;
import com.backend.proyecto.asesorias.services.AsesoriaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

// Controlador REST para la gestión de solicitudes de asesoría entre programadores y solicitantes
@RestController
@RequestMapping("/api/advisories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdvisoryController {

    private final AsesoriaService asesoriaService;
    private final AsesoriaMapper asesoriaMapper;

    // Obtiene una página con todas las asesorías registradas (Solo accesible por Administradores)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<AsesoriaResponseDto>> obtenerTodasLasAsesorias(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<Asesoria> asesorias = asesoriaService.getAllAsesorias(pageable);
        return ResponseEntity.ok(asesorias.map(asesoriaMapper::toResponseDto));
    }

    // Retorna la información detallada de una asesoría específica por su ID
    @GetMapping("/{id}")
    public ResponseEntity<AsesoriaResponseDto> obtenerAsesoriaPorId(@PathVariable("id") Long id) {
        Asesoria asesoria = asesoriaService.getAsesoriaById(id);
        return ResponseEntity.ok(asesoriaMapper.toResponseDto(asesoria));
    }

    // Lista las asesorías asignadas al programador autenticado actualmente
    @GetMapping("/my-advisories")
    public ResponseEntity<Page<AsesoriaResponseDto>> obtenerMisAsesorias(
            @AuthenticationPrincipal String uid,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<Asesoria> asesorias = asesoriaService.getAsesoriasByProgramador(uid, pageable);
        return ResponseEntity.ok(asesorias.map(asesoriaMapper::toResponseDto));
    }

    // Lista todas las asesorías asignadas a un programador específico
    @GetMapping("/programmer/{programmerId}")
    public ResponseEntity<Page<AsesoriaResponseDto>> obtenerAsesoriasPorProgramador(
            @PathVariable("programmerId") String programmerId,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<Asesoria> asesorias = asesoriaService.getAsesoriasByProgramador(programmerId, pageable);
        return ResponseEntity.ok(asesorias.map(asesoriaMapper::toResponseDto));
    }

    // Lista las asesorías solicitadas por un email de solicitante específico
    @GetMapping("/requester/{email}")
    public ResponseEntity<Page<AsesoriaResponseDto>> obtenerAsesoriasPorSolicitante(
            @PathVariable("email") String email,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<Asesoria> asesorias = asesoriaService.getAsesoriasBySolicitante(email, pageable);
        return ResponseEntity.ok(asesorias.map(asesoriaMapper::toResponseDto));
    }

    // Filtra las asesorías según su estado actual (pending, approved, rejected)
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<AsesoriaResponseDto>> obtenerAsesoriasPorEstado(
            @PathVariable("status") AsesoriaEntity.Status status,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<Asesoria> asesorias = asesoriaService.getAsesoriasByStatus(status, pageable);
        return ResponseEntity.ok(asesorias.map(asesoriaMapper::toResponseDto));
    }

    // Registra una nueva solicitud de asesoría y notifica a las partes involucradas
    @PostMapping
    public ResponseEntity<AsesoriaResponseDto> crearAsesoria(@Valid @RequestBody AsesoriaRequestDto request) {
        Asesoria asesoriaModel = asesoriaMapper.toModel(request);
        Asesoria created = asesoriaService.createAsesoria(asesoriaModel);
        return new ResponseEntity<>(asesoriaMapper.toResponseDto(created), HttpStatus.CREATED);
    }

    // Cambia manualmente el estado de una asesoría (Requiere ser el programador asignado o Admin)
    @PatchMapping("/{id}/status")
    public ResponseEntity<AsesoriaResponseDto> actualizarEstado(
            @PathVariable("id") Long id,
            @RequestBody Map<String, String> body,
            @AuthenticationPrincipal String uid) {
        String statusStr = body.get("status");
        Asesoria.Status status = Asesoria.Status.valueOf(statusStr);
        Asesoria updated = asesoriaService.updateAsesoriaStatus(id, status, uid);
        return ResponseEntity.ok(asesoriaMapper.toResponseDto(updated));
    }

    // Marca una asesoría como aprobada y notifica al solicitante
    @PatchMapping("/{id}/approve")
    public ResponseEntity<AsesoriaResponseDto> aprobarAsesoria(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal String uid) {
        Asesoria aprobada = asesoriaService.approveAsesoria(id, uid);
        return ResponseEntity.ok(asesoriaMapper.toResponseDto(aprobada));
    }

    // Marca una asesoría como rechazada y notifica al solicitante
    @PatchMapping("/{id}/reject")
    public ResponseEntity<AsesoriaResponseDto> rechazarAsesoria(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal String uid) {
        Asesoria rechazada = asesoriaService.rejectAsesoria(id, uid);
        return ResponseEntity.ok(asesoriaMapper.toResponseDto(rechazada));
    }

    // Elimina el registro de una asesoría del sistema
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAsesoria(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal String uid) {
        asesoriaService.deleteAsesoria(id, uid);
        return ResponseEntity.noContent().build();
    }
}