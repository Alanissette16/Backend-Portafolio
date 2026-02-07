package com.backend.proyecto.Users.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.backend.proyecto.asesorias.entities.AsesoriaEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    private String uid;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String speciality;

    @Column(length = 1001)
    private String bio;
    private String profilePictureUrl;
    
    private Boolean available = false;

    private String linkedinUrl;
    private String githubUrl;
    private String instagram;
    private String whatsap;

    //Habilidades tecnicas del usuario
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_skills", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "skill")
    private List<String> skills;
    
    //Horarios disponibles para asesorias
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_disponibilidad", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "disponibilidad")
    private List<String> disponibilidad;

    //Relacion OneToMany con Projects
    //@OneToMany(mappedBy = "owner",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    //@JsonIgnoreProperties("owner")
    //private List<ProjectEntity> projects = new ArrayList<>();

    // Relacion OneToMany con Asesorias
    @OneToMany(mappedBy = "programador", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("programador")
    private List<AsesoriaEntity> asesorias = new ArrayList<>();
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Roles de usuario
    public enum Role {
        programmer,
        admin,
        external,
    }

    // Constructor por defecto (requerido por JPA)
    public UserEntity() {
    }

    // Constructor con todos los campos
    public UserEntity(String uid, String email, String password, String displayName, Role role,
                      String speciality, String bio, String profilePictureUrl, Boolean available,
                      String linkedinUrl, String githubUrl, String instagram, String whatsap,
                      List<String> skills, List<String> disponibilidad, List<AsesoriaEntity> asesorias,
                      LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.role = role;
        this.speciality = speciality;
        this.bio = bio;
        this.profilePictureUrl = profilePictureUrl;
        this.available = available;
        this.linkedinUrl = linkedinUrl;
        this.githubUrl = githubUrl;
        this.instagram = instagram;
        this.whatsap = whatsap;
        this.skills = skills;
        this.disponibilidad = disponibilidad;
        this.asesorias = asesorias;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters
    public String getUid() {
        return uid;
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

    public Role getRole() {
        return role;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getBio() {
        return bio;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public Boolean getAvailable() {
        return available;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public String getInstagram() {
        return instagram;
    }

    public String getWhatsap() {
        return whatsap;
    }

    public List<String> getSkills() {
        return skills;
    }

    public List<String> getDisponibilidad() {
        return disponibilidad;
    }

    public List<AsesoriaEntity> getAsesorias() {
        return asesorias;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Setters
    public void setUid(String uid) {
        this.uid = uid;
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

    public void setRole(Role role) {
        this.role = role;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public void setWhatsap(String whatsap) {
        this.whatsap = whatsap;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public void setDisponibilidad(List<String> disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public void setAsesorias(List<AsesoriaEntity> asesorias) {
        this.asesorias = asesorias;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // equals() basado en el ID (buena práctica JPA)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return uid != null && Objects.equals(uid, that.uid);
    }

    // hashCode() constante (buena práctica JPA para entidades)
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                ", displayName='" + displayName + '\'' +
                ", role=" + role +
                ", speciality='" + speciality + '\'' +
                ", available=" + available +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
