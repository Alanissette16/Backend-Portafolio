package com.backend.proyecto.auth.services;

import com.backend.proyecto.Usuarios.entities.UsuarioEntity;
import com.backend.proyecto.Usuarios.repositories.UsuarioRepository;
import com.backend.proyecto.auth.dtos.AuthResponseDto;
import com.backend.proyecto.auth.dtos.LoginRequestDto;
import com.backend.proyecto.auth.dtos.RegisterRequestDto;
import com.backend.proyecto.exceptions.BadRequestException;
import com.backend.proyecto.exceptions.DuplicateResourceException;
import com.backend.proyecto.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

//Servicio de autenticación (login y registro)
@Service
@Transactional
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    //Constructor para inyección de dependencias
    public AuthService(UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //Registro de nuevo usuario (solo EXTERNAL)
    public AuthResponseDto register(RegisterRequestDto dto) {
        //Verificar que no exista usuario con ese email
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Usuario", "email", dto.getEmail());
        }

        //Crear nuevo usuario con rol EXTERNAL
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setDisplayName(dto.getDisplayName());
        usuario.setPhotoURL(dto.getPhotoURL());
        usuario.setRole(UsuarioEntity.RolUsuario.EXTERNAL);
        usuario.setCreatedAt(LocalDateTime.now());

        //Guardar usuario
        UsuarioEntity savedUsuario = usuarioRepository.save(usuario);

        //Generar token JWT
        String token = jwtTokenProvider.generateToken(savedUsuario.getEmail());

        //Retornar respuesta con token
        return new AuthResponseDto(
                token,
                savedUsuario.getId(),
                savedUsuario.getEmail(),
                savedUsuario.getDisplayName(),
                savedUsuario.getRole());
    }

    //Login de usuario
    public AuthResponseDto login(LoginRequestDto dto) {
        try {
            //Autenticar con Spring Security
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

            //Obtener usuario
            UsuarioEntity usuario = usuarioRepository.findByEmail(dto.getEmail())
                    .orElseThrow(() -> new BadRequestException("Credenciales inválidas"));

            //Actualizar último login
            usuario.setLastLogin(LocalDateTime.now());
            usuarioRepository.save(usuario);

            //Generar token JWT
            String token = jwtTokenProvider.generateToken(usuario.getEmail());

            //Retornar respuesta con token
            return new AuthResponseDto(
                    token,
                    usuario.getId(),
                    usuario.getEmail(),
                    usuario.getDisplayName(),
                    usuario.getRole());

        } catch (Exception e) {
            throw new BadRequestException("Credenciales inválidas");
        }
    }

    //Obtener información del usuario actual
    public AuthResponseDto getCurrentUser(String email) {
        UsuarioEntity usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));

        return new AuthResponseDto(
                null, //No devolvemos token aquí, ya lo tiene el cliente
                usuario.getId(),
                usuario.getEmail(),
                usuario.getDisplayName(),
                usuario.getRole());
    }
}
