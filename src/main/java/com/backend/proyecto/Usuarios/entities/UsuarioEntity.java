package com.backend.proyecto.Usuarios.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//Entidad JPA que representa un Usuario en el sistema
//Almacena la información básica de autenticación y perfil
@Entity
@Table(name = "users")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Email único para autenticación e identificación
    @Column(unique = true, nullable = false)
    private String email;

    //Contraseña hasheada (BCrypt)
    @Column(nullable = false)
    private String password;

    //Nombre para mostrar del usuario
    @Column(name = "display_name")
    private String displayName;

    //URL de la foto de perfil (puede ser Base64, soporta imágenes grandes)
    @Column(name = "photo_url", columnDefinition = "TEXT")
    private String photoURL;

    //Apellido del usuario
    @Column(name = "last_name")
    private String lastName;

    //Ubicación/país
    private String location;

    //Especialidad (para programadores)
    private String specialty;

    //Biografía profesional
    @Column(columnDefinition = "TEXT")
    private String bio;

    //Frase/quote personal
    @Column(columnDefinition = "TEXT")
    private String quote;

    //Habilidades técnicas (JSON array: [{"name": "React", "level": 90}])
    @Column(columnDefinition = "TEXT")
    private String skills;

    //Portfolio fields
    //Headline del portafolio
    @Column(columnDefinition = "TEXT")
    private String headline;

    //Sobre mí / About
    @Column(columnDefinition = "TEXT")
    private String about;

    //Tags del portafolio (JSON array: ["Frontend", "Backend"])
    @Column(columnDefinition = "TEXT")
    private String tags;

    //Tema visual del portafolio
    private String theme;

    //Redes sociales (JSON object: {"instagram": "url", "linkedin": "url", ...})
    @Column(columnDefinition = "TEXT")
    private String socials;

    //Estadísticas (JSON object: {"projects": 60, "experience": "6 años",
    // "clients": 40})
    @Column(columnDefinition = "TEXT")
    private String stats;

    //Rol del usuario en el sistema (Admin, Programmer, External)
    @jakarta.persistence.Convert(converter = RolUsuarioConverter.class)
    @Column(nullable = false)
    private RolUsuario role = RolUsuario.EXTERNAL;

    //Metadata de auditoría
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    //Enumeración de roles disponibles en el sistema
    // -ADMIN: Acceso completo
    // -PROGRAMMER: Gestión de portafolio y asesoría
    // -EXTERNAL: Usuario externo/Cliente
    public enum RolUsuario {
        ADMIN, //Administrador del sistema
        PROGRAMMER, //Programador/Mentor
        EXTERNAL //Usuario externo/Cliente
    }

    //Constructor por defecto (JPA)
    public UsuarioEntity() {
    }

    //Constructor con todos los campos
    public UsuarioEntity(Long id, String email, String password, String displayName,
            String photoURL, RolUsuario role, LocalDateTime createdAt, LocalDateTime lastLogin) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.photoURL = photoURL;
        this.role = role;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
    }

    //Getters
    public Long getId() {
        return id;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }

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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getSocials() {
        return socials;
    }

    public void setSocials(String socials) {
        this.socials = socials;
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }

    //equals() basado en el ID (buena práctica JPA)
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UsuarioEntity that = (UsuarioEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    //hashCode() constante (buena práctica JPA para entidades con ID generado)
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "UsuarioEntity{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", displayName='" + displayName + '\'' +
                ", photoURL='" + photoURL + '\'' +
                ", role=" + role +
                ", createdAt=" + createdAt +
                ", lastLogin=" + lastLogin +
                '}';
    }
}
