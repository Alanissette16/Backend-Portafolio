package com.backend.proyecto.PaneldeControl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class EstadoAsesoria {
    private String label;
    private Long count;

    
}
