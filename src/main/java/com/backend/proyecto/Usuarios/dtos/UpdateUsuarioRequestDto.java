package com.backend.proyecto.Usuarios.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

//DTO para actualizar un usuario existente
//Todos los campos son opcionales
public class UpdateUsuarioRequestDto {

    @Email(message = "El email debe ser válido")
    private String email;

    @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres")
    private String password;

    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String displayName;

    private String photoURL;

    private String lastName;

    private String location;

    private String specialty;

    private String bio;

    private String quote;

    private String skills;

    //Portfolio fields
    private String headline;

    private String about;

    private String tags;

    private String theme;

    private String socials;

    private String stats;

    private String role;

    //Constructor vacío
    public UpdateUsuarioRequestDto() {
    }

    //Getters
    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
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

    public void setSocials(String socials) {
        this.socials = socials;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
