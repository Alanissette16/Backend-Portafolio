package com.backend.proyecto.auth.controllers;

import com.backend.proyecto.auth.dtos.AuthResponseDto;
import com.backend.proyecto.auth.dtos.LoginRequestDto;
import com.backend.proyecto.auth.dtos.RegisterRequestDto;
import com.backend.proyecto.auth.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Controlador de autenticación (registro y login)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    // Constructor para inyección de dependencias
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Registro de nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterRequestDto dto) {
        AuthResponseDto response = authService.register(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Inicio de sesión
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto dto) {
        AuthResponseDto response = authService.login(dto);
        return ResponseEntity.ok(response);
    }

    // Obtener usuario autenticado
    @GetMapping("/me")
    public ResponseEntity<AuthResponseDto> getCurrentUser(java.security.Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        AuthResponseDto response = authService.getCurrentUser(principal.getName());
        return ResponseEntity.ok(response);
    }
}
