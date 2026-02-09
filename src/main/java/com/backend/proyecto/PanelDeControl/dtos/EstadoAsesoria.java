package com.backend.proyecto.PanelDeControl.dtos;

//DTO para respuestas de estadísticas de asesorías
//Usado en reportes del panel de control
public class EstadoAsesoria {
    private String label;
    private Long count;

    public EstadoAsesoria() {
    }

    public EstadoAsesoria(String label, Long count) {
        this.label = label;
        this.count = count;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
