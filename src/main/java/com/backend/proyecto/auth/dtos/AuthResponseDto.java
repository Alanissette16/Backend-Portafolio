package com.backend.proyecto.auth.dtos;

import com.backend.proyecto.Usuarios.entities.UsuarioEntity.RolUsuario;

//DTO para respuesta de autenticación
public class AuthResponseDto {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private String displayName;
    private RolUsuario role;

    //Constructor vacío
    public AuthResponseDto() {
    }

    //Constructor con parámetros
    public AuthResponseDto(String token, Long id, String email, String displayName, RolUsuario role) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.displayName = displayName;
        this.role = role;
    }

    //Getters y Setters
    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public RolUsuario getRole() {
        return role;
    }

    //Setters
    public void setToken(String token) {
        this.token = token;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setRole(RolUsuario role) {
        this.role = role;
    }
}
