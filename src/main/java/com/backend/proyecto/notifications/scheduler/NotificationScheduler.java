package com.backend.proyecto.notifications.scheduler;

import com.backend.proyecto.asesorias.entities.AsesoriaEntity;
import com.backend.proyecto.asesorias.repositories.AsesoriaRepository;
import com.backend.proyecto.notifications.services.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

//Componente para programar y enviar recordatorios de asesorías
@Component
public class NotificationScheduler {

    private static final Logger logger = LoggerFactory.getLogger(NotificationScheduler.class);

    private final AsesoriaRepository asesoriaRepository;
    private final NotificationService notificationService;

    @Value("${notifications.reminder.minutes-before}")
    private int minutesBefore;

    //Constructor para inyección de dependencias
    public NotificationScheduler(AsesoriaRepository asesoriaRepository,
            NotificationService notificationService) {
        this.asesoriaRepository = asesoriaRepository;
        this.notificationService = notificationService;
    }

    //Ejecutar cada 10 minutos para enviar recordatorios
    @Scheduled(cron = "0 */10 * * * *")
    public void enviarRecordatorios() {
        logger.info("Revisando asesorías próximas para enviar recordatorios...");

        LocalDate hoy = LocalDate.now();
        LocalTime ahora = LocalTime.now();
        LocalTime tiempoRecordatorio = ahora.plusMinutes(minutesBefore);

        //Buscar asesorías confirmadas para hoy
        List<AsesoriaEntity> asesorias = asesoriaRepository.findByFechaAndEstado(
                hoy, AsesoriaEntity.EstadoAsesoria.CONFIRMADA);

        for (AsesoriaEntity asesoria : asesorias) {
            //Verificar si falta exactamente el tiempo configurado (con tolerancia de ±5
            //min)
            //Esto es necesario porque el scheduler corre cada 10 min
            LocalTime horaInicio = asesoria.getHoraInicio();

            //Lógica simplificada: Si la hora de inicio está entre (ahora + minutesBefore -
            //5min) y (ahora + minutesBefore + 5min)
            //Ejemplo: minutesBefore = 30. Ahora = 10:00. Buscamos citas a las 10:30.
            //Rango: ]10:25, 10:35[
            if (horaInicio.isAfter(tiempoRecordatorio.minusMinutes(5)) &&
                    horaInicio.isBefore(tiempoRecordatorio.plusMinutes(5))) {

                logger.info("Enviando recordatorio para asesoría ID: {}", asesoria.getId());
                notificationService.sendRecordatorio(asesoria);
            }
        }
    }
}
