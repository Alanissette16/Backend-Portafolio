package com.backend.proyecto.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

//Habilita el uso de tareas programadas (@Scheduled)
//Usado para el sistema de recordatorios de asesorías
@Configuration
@EnableScheduling
public class SchedulerConfig {
    //La anotación @EnableScheduling es suficiente
    //No se requiere configuración adicional
}
