package com.backend.proyecto.Usuarios.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.backend.proyecto.Usuarios.entities.UsuarioEntity.RolUsuario;

//DTO para crear un nuevo usuario
public class CreateUsuarioRequestDto {

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres")
    private String password;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String displayName;

    private String photoURL;

    @NotNull(message = "El rol es obligatorio")
    private RolUsuario role;

    private String lastName;
    private String location;
    private String specialty;
    private String bio;
    private String quote;
    private String skills;
    private String socials;
    private String stats;

    //Constructor vacío
    public CreateUsuarioRequestDto() {
    }

    //Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public RolUsuario getRole() {
        return role;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLocation() {
        return location;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getBio() {
        return bio;
    }

    public String getQuote() {
        return quote;
    }

    public String getSkills() {
        return skills;
    }

    public String getSocials() {
        return socials;
    }

    public String getStats() {
        return stats;
    }

    //Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public void setRole(RolUsuario role) {
        this.role = role;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public void setSocials(String socials) {
        this.socials = socials;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }
}
