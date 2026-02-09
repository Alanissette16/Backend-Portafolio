package com.backend.proyecto.Usuarios.dtos;

//DTO para actualización de perfil de usuario
public class ActualizarUsuarioRequestDto {

    private String displayName;
    private String photoURL;

    public ActualizarUsuarioRequestDto() {
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
