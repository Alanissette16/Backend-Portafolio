package com.backend.proyecto.Usuarios.dtos;

//DTO para respuestas de autenticación
//Contiene el token JWT y la información del usuario
public class AuthResponseDto {

    private String token;
    private UsuarioResponseDto user;

    //Constructor vacío
    public AuthResponseDto() {
    }

    //Constructor con parámetros
    public AuthResponseDto(String token, UsuarioResponseDto user) {
        this.token = token;
        this.user = user;
    }

    //Getters y Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UsuarioResponseDto getUser() {
        return user;
    }

    public void setUser(UsuarioResponseDto user) {
        this.user = user;
    }
}
