package com.backend.proyecto.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${mail.from}")
    private String fromEmail;
    @Value("${mail.from.name}")
    private String fromName;

    // Enviar correo HTML
    @Async
    public void sendEmail(String to, String subject, String HTMLContent) {
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail, fromName);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(HTMLContent, true); // true indicates HTML

            mailSender.send(message);
            log.info("Email enviado a: {}", to);
        } catch (Exception e) {
            log.error("Error al enviar email a: {}", to, e.getMessage());
        }
    }
    // Email de Bienvenida ***IMPORTANTE CAMBIAR MENSAJE***
    public void sendWelcomeEmail(String to, String userName) {
        String subject = "Bienvenido a Foreign";
        String htmlContent = """
                <html>
                <body>
                    <h1>¡Bienvenido a Foreign, %s!</h1>
                    <p>Gracias por unirte a nuestra comunidad de programadores.</p>
                    <p>Estamos emocionados de tenerte con nosotros.</p>
                    <br>
                    <p>Saludos,<br>El equipo de Foreign</p>
                </body>
                </html>
                """.formatted(userName);
        sendEmail(to, subject, htmlContent);
    }


    //norificacionde nueva solicitud de asesoria al programador ***IMPORTANTE CAMBIAR MENSAJE***
    public void sendAsesoriaNotificationToProgramador(
        String programadorEmail,
        String programadorName,
        String solicitanteName,
        String date,
        String time,
        String nota) {
            String subject = "Nueva Solicitud de Asesoría";
            String htmlContent = """
                <html>
                <body>
                    <h1>Hola %s,</h1>
                    <p>Has recibido una nueva solicitud de asesoría de %s.</p>
                    <p><strong>Fecha:</strong> %s</p>
                    <p><strong>Hora:</strong> %s</p>
                    <p><strong>Nota del solicitante:</strong> %s</p>
                    <br>
                    <p>Por favor, revisa tu panel de control para gestionar esta solicitud.</p>
                    <br>
                    <p>Saludos,<br>El equipo de Foreign</p>
                </body>
                </html>
                """.formatted(programadorName, solicitanteName, date, time, nota);
            sendEmail(programadorEmail, subject, htmlContent);
    }
    //Notificacion de nueva asesoria asignada ***IMPORTANTE CAMBIAR MENSAJE***
    public void sendNewAsesoriaNotification(
        String programadorName,
        String programadorEmail,
        String solicitanteName,
        String date,
        String time,
        String nota) {
            String subject = "Nueva Asesoría Asignada";
            String htmlContent = """
                <html>
                <body>
                    <h1>Hola %s,</h1>
                    <p>Se te ha asignado una nueva asesoría solicitada por %s.</p>
                    <p><strong>Fecha:</strong> %s</p>
                    <p><strong>Hora:</strong> %s</p>
                    <p><strong>Nota del solicitante:</strong> %s</p>
                    <br>
                    <p>Por favor, prepárate para la asesoría.</p>
                    <br>
                    <p>Saludos,<br>El equipo de Foreign</p>
                </body>
                </html>
                """.formatted(programadorName, solicitanteName, date, time, nota);
            sendEmail(programadorEmail, subject, htmlContent);
    }

    // Confirmacion de asesoria al solicitante ***IMPORTANTE CAMBIAR MENSAJE***
    public void sendAsesoriaConfirmationToSolicitante(
        String solicitanteName,
        String solicitanteEmail,
        String programadorName,
        String date,
        String time) {
            String subject = "Confirmación de Asesoría Solicitada";
            String htmlContent = """
                <html>
                <body>
                    <h1>Hola %s,</h1>
                    <p>Tu asesoría con %s ha sido confirmada.</p>
                    <p><strong>Fecha:</strong> %s</p>
                    <p><strong>Hora:</strong> %s</p>
                    <p><strong>Nota:</strong> %s</p>
                    <br>
                    <p>Gracias por utilizar nuestros servicios.</p>
                    <br>
                    <p>Saludos,<br>El equipo de Foreign</p>
                </body>
                </html>
                """.formatted(solicitanteName, programadorName, date, time);
            sendEmail(solicitanteEmail, subject, htmlContent);
    }
    // Actualizacion de estado de asesoria ***IMPORTANTE CAMBIAR MENSAJE***
    public void sendAsesoriaStatusUpdate(
        String solicitanteName,
        String solicitanteEmail,
        String programadorName,
        String status) {
            String statusColor = status.equals("aprobado") ? "green":status.equals("CANCELLED") ? "red" : "blue";
            String subject = "Actualización de Estado de Asesoría";
            String htmlContent = """
                <html>
                <body>
                    <h1>Hola %s,</h1>
                    <p>La asesoría con %s ha sido <span style="color:%s;">%s</span>.</p>
                    <br>
                    <p>Saludos,<br>El equipo de Foreign</p>
                </body>
                </html>
                """.formatted(solicitanteName, programadorName, statusColor, status);
            sendEmail(solicitanteEmail, subject, htmlContent);
        }
}
