package com.backend.proyecto.Usuarios.controllers;

import com.backend.proyecto.Usuarios.dtos.CreateUsuarioRequestDto;
import com.backend.proyecto.Usuarios.dtos.UpdateUsuarioRequestDto;
import com.backend.proyecto.Usuarios.dtos.UsuarioResponseDto;
import com.backend.proyecto.Usuarios.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

//Controlador REST para gestión de usuarios
//Solo accesible por usuarios con rol ADMIN
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    // Constructor para inyección de dependencias
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Crea un nuevo usuario en el sistema (ADMIN)
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> crearUsuario(@Valid @RequestBody CreateUsuarioRequestDto dto) {
        UsuarioResponseDto response = usuarioService.crearUsuario(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Lista todos los usuarios del sistema
    @GetMapping
    public ResponseEntity<Page<UsuarioResponseDto>> obtenerTodosLosUsuarios(
            Pageable pageable) {
        Page<UsuarioResponseDto> response = usuarioService.obtenerTodosLosUsuarios(pageable);
        return ResponseEntity.ok(response);
    }

    // Lista solo los usuarios con rol PROGRAMMER (público)
    @GetMapping("/programadores")
    public ResponseEntity<Page<UsuarioResponseDto>> obtenerProgramadores(
            Pageable pageable) {
        Page<UsuarioResponseDto> response = usuarioService.obtenerProgramadores(pageable);
        return ResponseEntity.ok(response);
    }

    // Obtiene un usuario específico por su ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or principal.id.toString() == #id.toString()")
    public ResponseEntity<UsuarioResponseDto> obtenerUsuarioPorId(@PathVariable Long id) {
        UsuarioResponseDto response = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(response);
    }

    // Actualiza los datos de un usuario existente
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or principal.id.toString() == #id.toString()")
    public ResponseEntity<UsuarioResponseDto> actualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUsuarioRequestDto dto) {
        UsuarioResponseDto response = usuarioService.actualizarUsuario(id, dto);
        return ResponseEntity.ok(response);
    }

    // Elimina un usuario del sistema por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // Busca un usuario por su dirección de email
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponseDto> obtenerUsuarioPorEmail(@PathVariable String email) {
        UsuarioResponseDto response = usuarioService.obtenerUsuarioPorEmail(email);
        return ResponseEntity.ok(response);
    }
}
