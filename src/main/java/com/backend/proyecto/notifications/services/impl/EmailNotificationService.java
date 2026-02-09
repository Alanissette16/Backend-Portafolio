package com.backend.proyecto.notifications.services.impl;

import com.backend.proyecto.asesorias.entities.AsesoriaEntity;
import com.backend.proyecto.notifications.services.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

//Servicio de notificación por email
@Service
public class EmailNotificationService implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationService.class);

    private final JavaMailSender mailSender;

    @Value("${mail.from}")
    private String fromEmail;

    @Value("${mail.from.name}")
    private String fromName;

    //Constructor para inyección de dependencias
    public EmailNotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendAsesoriaCreada(AsesoriaEntity asesoria) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(asesoria.getProgramador().getUsuario().getEmail());
            message.setSubject("Nueva Solicitud de Asesoría");
            message.setText(String.format(
                    "Hola %s,\n\n" +
                            "Has recibido una nueva solicitud de asesoría:\n\n" +
                            "Cliente: %s\n" +
                            "Fecha: %s\n" +
                            "Hora: %s - %s\n" +
                            "Motivo: %s\n\n" +
                            "Por favor, ingresa al sistema para confirmar o rechazar la solicitud.\n\n" +
                            "Saludos,\n" +
                            "Equipo Portafolio Programadores",
                    asesoria.getProgramador().getUsuario().getDisplayName(),
                    asesoria.getUsuarioExterno().getDisplayName(),
                    asesoria.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    asesoria.getHoraInicio(),
                    asesoria.getHoraFin(),
                    asesoria.getMotivo()));

            mailSender.send(message);
            logger.info("Email enviado a programador: {}", asesoria.getProgramador().getUsuario().getEmail());
        } catch (Exception e) {
            logger.error("Error enviando email de asesoría creada", e);
        }
    }

    @Override
    public void sendAsesoriaConfirmada(AsesoriaEntity asesoria) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(asesoria.getUsuarioExterno().getEmail());
            message.setSubject("Asesoría Confirmada");
            message.setText(String.format(
                    "Hola %s,\n\n" +
                            "Tu solicitud de asesoría ha sido CONFIRMADA:\n\n" +
                            "Programador: %s\n" +
                            "Fecha: %s\n" +
                            "Hora: %s - %s\n" +
                            "Modalidad: %s\n\n" +
                            "¡Nos vemos pronto!\n\n" +
                            "Saludos,\n" +
                            "Equipo Portafolio Programadores",
                    asesoria.getUsuarioExterno().getDisplayName(),
                    asesoria.getProgramador().getUsuario().getDisplayName(),
                    asesoria.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    asesoria.getHoraInicio(),
                    asesoria.getHoraFin(),
                    asesoria.getModalidad()));

            mailSender.send(message);
            logger.info("Email enviado a usuario externo: {}", asesoria.getUsuarioExterno().getEmail());
        } catch (Exception e) {
            logger.error("Error enviando email de asesoría confirmada", e);
        }
    }

    @Override
    public void sendAsesoriaRechazada(AsesoriaEntity asesoria) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(asesoria.getUsuarioExterno().getEmail());
            message.setSubject("Asesoría Rechazada");
            message.setText(String.format(
                    "Hola %s,\n\n" +
                            "Lamentamos informarte que tu solicitud de asesoría ha sido rechazada:\n\n" +
                            "Fecha solicitada: %s\n" +
                            "Hora: %s - %s\n\n" +
                            "Te invitamos a solicitar otra fecha y horario.\n\n" +
                            "Saludos,\n" +
                            "Equipo Portafolio Programadores",
                    asesoria.getUsuarioExterno().getDisplayName(),
                    asesoria.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    asesoria.getHoraInicio(),
                    asesoria.getHoraFin()));

            mailSender.send(message);
            logger.info("Email enviado a usuario externo: {}", asesoria.getUsuarioExterno().getEmail());
        } catch (Exception e) {
            logger.error("Error enviando email de asesoría rechazada", e);
        }
    }

    @Override
    public void sendRecordatorio(AsesoriaEntity asesoria) {
        try {
            //Enviar recordatorio al programador
            SimpleMailMessage messageProgramador = new SimpleMailMessage();
            messageProgramador.setFrom(fromEmail);
            messageProgramador.setTo(asesoria.getProgramador().getUsuario().getEmail());
            messageProgramador.setSubject("Recordatorio: Asesoría en 30 minutos");
            messageProgramador.setText(String.format(
                    "Hola %s,\n\n" +
                            "Recordatorio: Tienes una asesoría en 30 minutes:\n\n" +
                            "Cliente: %s\n" +
                            "Hora: %s - %s\n" +
                            "Modalidad: %s\n\n" +
                            "¡Prepárate!\n\n" +
                            "Saludos,\n" +
                            "Equipo Portafolio Programadores",
                    asesoria.getProgramador().getUsuario().getDisplayName(),
                    asesoria.getUsuarioExterno().getDisplayName(),
                    asesoria.getHoraInicio(),
                    asesoria.getHoraFin(),
                    asesoria.getModalidad()));

            //Enviar recordatorio al usuario externo
            SimpleMailMessage messageUsuario = new SimpleMailMessage();
            messageUsuario.setFrom(fromEmail);
            messageUsuario.setTo(asesoria.getUsuarioExterno().getEmail());
            messageUsuario.setSubject("Recordatorio: Tu asesoría en 30 minutos");
            messageUsuario.setText(String.format(
                    "Hola %s,\n\n" +
                            "Recordatorio: Tu asesoría comienza en 30 minutos:\n\n" +
                            "Programador: %s\n" +
                            "Hora: %s - %s\n" +
                            "Modalidad: %s\n\n" +
                            "¡Nos vemos pronto!\n\n" +
                            "Saludos,\n" +
                            "Equipo Portafolio Programadores",
                    asesoria.getUsuarioExterno().getDisplayName(),
                    asesoria.getProgramador().getUsuario().getDisplayName(),
                    asesoria.getHoraInicio(),
                    asesoria.getHoraFin(),
                    asesoria.getModalidad()));

            mailSender.send(messageProgramador);
            mailSender.send(messageUsuario);
            logger.info("Recordatorios enviados para asesoría ID: {}", asesoria.getId());
        } catch (Exception e) {
            logger.error("Error enviando recordatorio", e);
        }
    }
}
