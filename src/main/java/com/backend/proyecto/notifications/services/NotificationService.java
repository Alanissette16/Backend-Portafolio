package com.backend.proyecto.notifications.services;

import com.backend.proyecto.asesorias.entities.AsesoriaEntity;

//Interfaz para servicios de notificación
public interface NotificationService {

    //Notifica cuando se crea una nueva asesoría
    void sendAsesoriaCreada(AsesoriaEntity asesoria);

    //Notifica cuando se confirma una asesoría
    void sendAsesoriaConfirmada(AsesoriaEntity asesoria);

    //Notifica cuando se rechaza una asesoría
    void sendAsesoriaRechazada(AsesoriaEntity asesoria);

    //Envía recordatorio antes de una asesoría
    void sendRecordatorio(AsesoriaEntity asesoria);
}
