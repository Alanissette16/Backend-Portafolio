package com.backend.proyecto.Usuarios.services;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.proyecto.Usuarios.dtos.AuthResponseDto;
import com.backend.proyecto.Usuarios.dtos.LoginRequestDto;
import com.backend.proyecto.Usuarios.dtos.RegisterRequestDto;
import com.backend.proyecto.Usuarios.dtos.UsuarioResponseDto;
import com.backend.proyecto.Usuarios.entities.UsuarioEntity;
import com.backend.proyecto.Usuarios.mappers.UsuarioMapper;
import com.backend.proyecto.Usuarios.repositories.UsuarioRepository;
import com.backend.proyecto.security.JwtTokenProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Servicio de autenticación
//Maneja registro, login y generación de tokens JWT
@Service
public class AutenticacionService {

    private static final Logger log = LoggerFactory.getLogger(AutenticacionService.class);

    private final UsuarioRepository UsuarioRepository;
    private final UsuarioMapper UsuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    //Constructor para inyección de dependencias
    public AutenticacionService(
            UsuarioRepository UsuarioRepository,
            UsuarioMapper UsuarioMapper,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider,
            AuthenticationManager authenticationManager) {
        this.UsuarioRepository = UsuarioRepository;
        this.UsuarioMapper = UsuarioMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    //Registra un nuevo usuario en el sistema
    @Transactional
    public AuthResponseDto register(RegisterRequestDto request) {
        //Verificar si el email ya existe
        if (UsuarioRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        //Crear entidad de usuario
        UsuarioEntity user = new UsuarioEntity();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); //Hashear contraseña
        user.setDisplayName(request.getDisplayName());
        user.setRole(UsuarioEntity.RolUsuario.EXTERNAL); //Rol por defecto
        user.setCreatedAt(LocalDateTime.now());

        //Guardar en base de datos
        UsuarioEntity savedUser = UsuarioRepository.save(user);
        log.info("Usuario registrado exitosamente: {}", savedUser.getEmail());

        //Generar token JWT
        //Ahora usamos generateToken que acepta String (o UserDetails)
        String token = jwtTokenProvider.generateToken(savedUser.getEmail());

        //Preparar respuesta
        UsuarioResponseDto userResponse = UsuarioMapper.toResponseDto(savedUser);

        return new AuthResponseDto(token, userResponse);
    }

    //Autentica un usuario y genera un token JWT
    @Transactional
    public AuthResponseDto login(LoginRequestDto request) {
        //Autenticar con Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        //Si la autenticación fue exitosa, obtener el usuario
        UsuarioEntity user = UsuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        //Actualizar último login
        user.setLastLogin(LocalDateTime.now());
        UsuarioRepository.save(user);

        //Generar token JWT
        String token = jwtTokenProvider.generateToken(user.getEmail());

        //Preparar respuesta
        UsuarioResponseDto userResponse = UsuarioMapper.toResponseDto(user);

        log.info("Usuario autenticado exitosamente: {}", user.getEmail());

        return new AuthResponseDto(token, userResponse);
    }

    //Obtiene la información del usuario autenticado actualmente
    public UsuarioResponseDto getCurrentUser(String email) {
        UsuarioEntity user = UsuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        return UsuarioMapper.toResponseDto(user);
    }
}
