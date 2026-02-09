package com.backend.proyecto.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

//DTO para login
public class LoginRequestDto {

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    //Constructor vacío
    public LoginRequestDto() {
    }

    //Constructor con parámetros
    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //Getters y Setters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    //Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
