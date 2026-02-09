package com.backend.proyecto.Programadores.dtos;

import com.backend.proyecto.Usuarios.dtos.UsuarioResponseDto;
import java.math.BigDecimal;

//DTO para respuesta de programador
public class ProgramadorResponseDto {

    private Long id;
    private UsuarioResponseDto usuario;
    private String especialidad;
    private String biografia;
    private String linkedinUrl;
    private String githubUrl;
    private String sitioWeb;
    private BigDecimal tarifa;

    //Constructor vacío
    public ProgramadorResponseDto() {
    }

    //Constructor con parámetros
    public ProgramadorResponseDto(Long id, UsuarioResponseDto usuario, String especialidad,
            String biografia, String linkedinUrl, String githubUrl,
            String sitioWeb, BigDecimal tarifa) {
        this.id = id;
        this.usuario = usuario;
        this.especialidad = especialidad;
        this.biografia = biografia;
        this.linkedinUrl = linkedinUrl;
        this.githubUrl = githubUrl;
        this.sitioWeb = sitioWeb;
        this.tarifa = tarifa;
    }

    //Getters
    public Long getId() {
        return id;
    }

    public UsuarioResponseDto getUsuario() {
        return usuario;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public String getBiografia() {
        return biografia;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public BigDecimal getTarifa() {
        return tarifa;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsuario(UsuarioResponseDto usuario) {
        this.usuario = usuario;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public void setTarifa(BigDecimal tarifa) {
        this.tarifa = tarifa;
    }
}
