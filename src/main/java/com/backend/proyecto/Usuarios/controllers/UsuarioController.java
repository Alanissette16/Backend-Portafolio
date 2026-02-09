package com.backend.proyecto.Usuarios.controllers;

import com.backend.proyecto.Usuarios.dtos.CreateUsuarioRequestDto;
import com.backend.proyecto.Usuarios.dtos.UpdateUsuarioRequestDto;
import com.backend.proyecto.Usuarios.dtos.UsuarioResponseDto;
import com.backend.proyecto.Usuarios.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Controlador REST para gestión de usuarios
//Solo accesible por usuarios con rol ADMIN
@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "API para gestión de usuarios (solo ADMIN)")
@SecurityRequirement(name = "bearer-jwt")
public class UsuarioController {

    private final UsuarioService usuarioService;

    //Constructor para inyección de dependencias
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    //Crea un nuevo usuario en el sistema (ADMIN)
    @PostMapping
    @Operation(summary = "Crear nuevo usuario", description = "Crea un nuevo usuario en el sistema (ADMIN)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "Email ya existe")
    })
    public ResponseEntity<UsuarioResponseDto> crearUsuario(@Valid @RequestBody CreateUsuarioRequestDto dto) {
        UsuarioResponseDto response = usuarioService.crearUsuario(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //Lista todos los usuarios del sistema
    @GetMapping
    @Operation(summary = "Obtener todos los usuarios", description = "Lista todos los usuarios del sistema")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    public ResponseEntity<List<UsuarioResponseDto>> obtenerTodosLosUsuarios() {
        List<UsuarioResponseDto> response = usuarioService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(response);
    }

    //Lista solo los usuarios con rol PROGRAMMER (público)
    @GetMapping("/programadores")
    @Operation(summary = "Obtener todos los programadores", description = "Lista todos los usuarios con rol PROGRAMMER (acceso público)")
    @ApiResponse(responseCode = "200", description = "Lista de programadores obtenida exitosamente")
    public ResponseEntity<List<UsuarioResponseDto>> obtenerProgramadores() {
        List<UsuarioResponseDto> response = usuarioService.obtenerProgramadores();
        return ResponseEntity.ok(response);
    }

    //Obtiene un usuario específico por su ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or principal.id.toString() == #id.toString()")
    @Operation(summary = "Obtener usuario por ID", description = "Obtiene un usuario específico por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioResponseDto> obtenerUsuarioPorId(@PathVariable Long id) {
        UsuarioResponseDto response = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(response);
    }

    //Actualiza los datos de un usuario existente
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or principal.id.toString() == #id.toString()")
    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "409", description = "Email ya existe")
    })
    public ResponseEntity<UsuarioResponseDto> actualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUsuarioRequestDto dto) {
        UsuarioResponseDto response = usuarioService.actualizarUsuario(id, dto);
        return ResponseEntity.ok(response);
    }

    //Elimina un usuario del sistema por su ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    //Busca un usuario por su dirección de email
    @GetMapping("/email/{email}")
    @Operation(summary = "Obtener usuario por email", description = "Busca un usuario por su dirección de email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioResponseDto> obtenerUsuarioPorEmail(@PathVariable String email) {
        UsuarioResponseDto response = usuarioService.obtenerUsuarioPorEmail(email);
        return ResponseEntity.ok(response);
    }
}
