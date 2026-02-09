package com.backend.proyecto.auth.controllers;

import com.backend.proyecto.auth.dtos.AuthResponseDto;
import com.backend.proyecto.auth.dtos.LoginRequestDto;
import com.backend.proyecto.auth.dtos.RegisterRequestDto;
import com.backend.proyecto.auth.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Controlador de autenticación (registro y login)
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints de registro y login (públicos)")
public class AuthController {

    private final AuthService authService;

    //Constructor para inyección de dependencias
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //Registro de nuevo usuario
    @PostMapping("/register")
    @Operation(summary = "Registrar nuevo usuario", description = "Crea una cuenta de usuario EXTERNO")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "Email ya existe")
    })
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterRequestDto dto) {
        AuthResponseDto response = authService.register(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //Inicio de sesión
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica usuario y retorna JWT token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login exitoso"),
            @ApiResponse(responseCode = "400", description = "Credenciales inválidas")
    })
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto dto) {
        AuthResponseDto response = authService.login(dto);
        return ResponseEntity.ok(response);
    }

    //Obtener usuario autenticado
    @GetMapping("/me")
    @Operation(summary = "Obtener perfil actual", description = "Retorna el perfil del usuario autenticado (usando el token)")
    @ApiResponse(responseCode = "200", description = "Perfil obtenido")
    public ResponseEntity<AuthResponseDto> getCurrentUser(java.security.Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        AuthResponseDto response = authService.getCurrentUser(principal.getName());
        return ResponseEntity.ok(response);
    }
}
