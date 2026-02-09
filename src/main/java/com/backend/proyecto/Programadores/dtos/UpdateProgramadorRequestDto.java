package com.backend.proyecto.Programadores.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

//DTO para actualizar perfil de programador
public class UpdateProgramadorRequestDto {

    @Size(max = 200, message = "La especialidad no puede exceder 200 caracteres")
    private String especialidad;

    @Size(max = 1000, message = "La biografía no puede exceder 1000 caracteres")
    private String biografia;

    @Size(max = 500, message = "La URL de LinkedIn no puede exceder 500 caracteres")
    private String linkedinUrl;

    @Size(max = 500, message = "La URL de GitHub no puede exceder 500 caracteres")
    private String githubUrl;

    @Size(max = 500, message = "La URL del sitio web no puede exceder 500 caracteres")
    private String sitioWeb;

    @DecimalMin(value = "0.0", message = "La tarifa debe ser mayor o igual a 0")
    private BigDecimal tarifa;

    //Constructor vacío
    public UpdateProgramadorRequestDto() {
    }

    //Constructor con parámetros
    public UpdateProgramadorRequestDto(String especialidad, String biografia, String linkedinUrl,
            String githubUrl, String sitioWeb, BigDecimal tarifa) {
        this.especialidad = especialidad;
        this.biografia = biografia;
        this.linkedinUrl = linkedinUrl;
        this.githubUrl = githubUrl;
        this.sitioWeb = sitioWeb;
        this.tarifa = tarifa;
    }

    //Getters
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
