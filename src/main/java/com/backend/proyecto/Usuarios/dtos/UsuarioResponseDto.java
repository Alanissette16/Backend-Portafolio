package com.backend.proyecto.Usuarios.dtos;

import com.backend.proyecto.Usuarios.entities.UsuarioEntity.RolUsuario;

import java.time.LocalDateTime;

//DTO para respuestas de usuario
//NO incluye el password por seguridad
public class UsuarioResponseDto {
    // ... (fields remain the same up to getters)

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

    public String getHeadline() {
        return headline;
    }

    public String getAbout() {
        return about;
    }

    public String getTags() {
        return tags;
    }

    public String getTheme() {
        return theme;
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

    public String getPhotoURL() {
        return photoURL;
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

    public RolUsuario getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    private Long id;
    private String email;
    private String displayName;
    private String photoURL;
    private String lastName;
    private String location;
    private String specialty;
    private String bio;
    private String quote;
    private String skills;
    private String socials;
    private String stats;

    //Portfolio fields
    private String headline;
    private String about;
    private String tags;
    private String theme;

    private RolUsuario role;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    // ... (constructor remains same as it touches only basic fields or is empty)

    //Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
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

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
