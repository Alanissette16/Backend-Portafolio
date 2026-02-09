package com.backend.proyecto.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    // Constructor para inyección de dependencias
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${mail.from}")
    private String fromEmail;
    @Value("${mail.from.name}")
    private String fromName;

    // Enviar correo electrónico en formato HTML de forma asíncrona
    @Async
    public void sendEmail(String to, String subject, String HTMLContent) {
        try {
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

    // Email de bienvenida para nuevos usuarios
    public void sendWelcomeEmail(String to, String userName) {
        String subject = "Bienvenido a Portafolio Programadores";
        String htmlContent = """
                <!DOCTYPE html>
                <html lang="es">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                </head>
                <body style="margin: 0; padding: 0; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f4f4;">
                    <table role="presentation" cellpadding="0" cellspacing="0" width="100%%" style="background-color: #f4f4f4; padding: 20px 0;">
                        <tr>
                            <td align="center">
                                <table role="presentation" cellpadding="0" cellspacing="0" width="600" style="background-color: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 6px rgba(0,0,0,0.1);">
                                    <!-- Header -->
                                    <tr>
                                        <td style="background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%); padding: 40px 30px; text-align: center;">
                                            <h1 style="margin: 0; color: #ffffff; font-size: 28px; font-weight: 600;">Bienvenido a Portafolio</h1>
                                        </td>
                                    </tr>
                                    <!-- Content -->
                                    <tr>
                                        <td style="padding: 40px 30px;">
                                            <h2 style="margin: 0 0 20px; color: #333333; font-size: 24px; font-weight: 600;">¡Hola, %s!</h2>
                                            <p style="margin: 0 0 15px; color: #666666; font-size: 16px; line-height: 1.6;">
                                                Gracias por unirte a nuestra plataforma de portafolios profesionales. Estamos emocionados de tenerte como parte de nuestra comunidad.
                                            </p>
                                            <p style="margin: 0 0 15px; color: #666666; font-size: 16px; line-height: 1.6;">
                                                Ahora puedes explorar portafolios de programadores talentosos, solicitar asesorías personalizadas y conectar con profesionales de la industria.
                                            </p>
                                            <div style="margin: 30px 0; padding: 20px; background-color: #f8f9fa; border-left: 4px solid #667eea; border-radius: 4px;">
                                                <p style="margin: 0; color: #555555; font-size: 14px; line-height: 1.6;">
                                                    <strong>¿Qué puedes hacer ahora?</strong><br>
                                                    • Explorar perfiles de programadores<br>
                                                    • Solicitar asesorías personalizadas<br>
                                                    • Revisar proyectos destacados
                                                </p>
                                            </div>
                                        </td>
                                    </tr>
                                    <!-- Footer -->
                                    <tr>
                                        <td style="background-color: #f8f9fa; padding: 30px; text-align: center; border-top: 1px solid #e9ecef;">
                                            <p style="margin: 0 0 10px; color: #999999; font-size: 14px;">
                                                Este es un correo automático, por favor no responder.
                                            </p>
                                            <p style="margin: 0; color: #999999; font-size: 12px;">
                                                &copy; 2024 Portafolio Programadores. Todos los derechos reservados.
                                            </p>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </body>
                </html>
                """
                .formatted(userName);
        sendEmail(to, subject, htmlContent);
    }

    // Notificación de nueva solicitud de asesoría al programador
    public void sendAsesoriaNotificationToProgramador(
            String programadorEmail,
            String programadorName,
            String solicitanteName,
            String date,
            String time,
            String nota) {
        String subject = "Nueva Solicitud de Asesoría";
        String htmlContent = """
                <!DOCTYPE html>
                <html lang="es">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                </head>
                <body style="margin: 0; padding: 0; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f4f4;">
                    <table role="presentation" cellpadding="0" cellspacing="0" width="100%%" style="background-color: #f4f4f4; padding: 20px 0;">
                        <tr>
                            <td align="center">
                                <table role="presentation" cellpadding="0" cellspacing="0" width="600" style="background-color: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 6px rgba(0,0,0,0.1);">
                                    <!-- Header -->
                                    <tr>
                                        <td style="background: linear-gradient(135deg, #3b82f6 0%%, #2563eb 100%%); padding: 40px 30px; text-align: center;">
                                            <h1 style="margin: 0; color: #ffffff; font-size: 28px; font-weight: 600;">Nueva Solicitud de Asesoría</h1>
                                        </td>
                                    </tr>
                                    <!-- Content -->
                                    <tr>
                                        <td style="padding: 40px 30px;">
                                            <h2 style="margin: 0 0 20px; color: #333333; font-size: 24px; font-weight: 600;">Hola, %s</h2>
                                            <p style="margin: 0 0 25px; color: #666666; font-size: 16px; line-height: 1.6;">
                                                Has recibido una nueva solicitud de asesoría de <strong style="color: #3b82f6;">%s</strong>.
                                            </p>
                                            <!-- Details Card -->
                                            <table role="presentation" cellpadding="0" cellspacing="0" width="100%%" style="background-color: #f8f9fa; border-radius: 6px; margin: 20px 0;">
                                                <tr>
                                                    <td style="padding: 25px;">
                                                        <table role="presentation" cellpadding="0" cellspacing="0" width="100%%">
                                                            <tr>
                                                                <td style="padding: 10px 0;">
                                                                    <p style="margin: 0; color: #888888; font-size: 14px; text-transform: uppercase; letter-spacing: 0.5px;">Fecha</p>
                                                                    <p style="margin: 5px 0 0; color: #333333; font-size: 18px; font-weight: 600;">%s</p>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td style="padding: 10px 0; border-top: 1px solid #e9ecef;">
                                                                    <p style="margin: 0; color: #888888; font-size: 14px; text-transform: uppercase; letter-spacing: 0.5px;">Hora</p>
                                                                    <p style="margin: 5px 0 0; color: #333333; font-size: 18px; font-weight: 600;">%s</p>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td style="padding: 10px 0; border-top: 1px solid #e9ecef;">
                                                                    <p style="margin: 0; color: #888888; font-size: 14px; text-transform: uppercase; letter-spacing: 0.5px;">Nota del solicitante</p>
                                                                    <p style="margin: 5px 0 0; color: #333333; font-size: 16px; line-height: 1.5;">%s</p>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table>
                                            <!-- CTA -->
                                            <div style="margin: 30px 0; padding: 20px; background-color: #fef3c7; border-left: 4px solid #f59e0b; border-radius: 4px;">
                                                <p style="margin: 0; color: #92400e; font-size: 14px; line-height: 1.6;">
                                                    <strong>Acción requerida:</strong> Por favor, revisa tu panel de control para confirmar o rechazar esta solicitud.
                                                </p>
                                            </div>
                                        </td>
                                    </tr>
                                    <!-- Footer -->
                                    <tr>
                                        <td style="background-color: #f8f9fa; padding: 30px; text-align: center; border-top: 1px solid #e9ecef;">
                                            <p style="margin: 0 0 10px; color: #999999; font-size: 14px;">
                                                Este es un correo automático, por favor no responder.
                                            </p>
                                            <p style="margin: 0; color: #999999; font-size: 12px;">
                                                &copy; 2024 Portafolio Programadores. Todos los derechos reservados.
                                            </p>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </body>
                </html>
                """
                .formatted(programadorName, solicitanteName, date, time, nota);
        sendEmail(programadorEmail, subject, htmlContent);
    }

    // Notificación de nueva asesoría asignada
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

    // Confirmación de asesoría al solicitante
    public void sendAsesoriaConfirmationToSolicitante(
            String solicitanteName,
            String solicitanteEmail,
            String programadorName,
            String date,
            String time) {
        String subject = "¡Tu Asesoría ha sido Confirmada!";
        String htmlContent = """
                <!DOCTYPE html>
                <html lang="es">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                </head>
                <body style="margin: 0; padding: 0; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f4f4;">
                    <table role="presentation" cellpadding="0" cellspacing="0" width="100%%" style="background-color: #f4f4f4; padding: 20px 0;">
                        <tr>
                            <td align="center">
                                <table role="presentation" cellpadding="0" cellspacing="0" width="600" style="background-color: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 6px rgba(0,0,0,0.1);">
                                    <!-- Header -->
                                    <tr>
                                        <td style="background: linear-gradient(135deg, #10b981 0%%, #059669 100%%); padding: 40px 30px; text-align: center;">
                                            <div style="background-color: #ffffff; width: 60px; height: 60px; border-radius: 50%%; margin: 0 auto 20px; display: flex; align-items: center; justify-content: center;">
                                                <span style="color: #10b981; font-size: 32px;">✓</span>
                                            </div>
                                            <h1 style="margin: 0; color: #ffffff; font-size: 28px; font-weight: 600;">Asesoría Confirmada</h1>
                                        </td>
                                    </tr>
                                    <!-- Content -->
                                    <tr>
                                        <td style="padding: 40px 30px;">
                                            <h2 style="margin: 0 0 20px; color: #333333; font-size: 24px; font-weight: 600;">¡Excelente noticia, %s!</h2>
                                            <p style="margin: 0 0 25px; color: #666666; font-size: 16px; line-height: 1.6;">
                                                Tu asesoría con <strong style="color: #10b981;">%s</strong> ha sido confirmada exitosamente.
                                            </p>
                                            <!-- Details Card -->
                                            <table role="presentation" cellpadding="0" cellspacing="0" width="100%%" style="background: linear-gradient(135deg, #ecfdf5 0%%, #d1fae5 100%%); border-radius: 8px; margin: 25px 0; border: 2px solid #10b981;">
                                                <tr>
                                                    <td style="padding: 30px;">
                                                        <table role="presentation" cellpadding="0" cellspacing="0" width="100%%">
                                                            <tr>
                                                                <td style="padding: 12px 0;">
                                                                    <p style="margin: 0; color: #065f46; font-size: 14px; text-transform: uppercase; letter-spacing: 0.5px; font-weight: 600;">📅 Fecha</p>
                                                                    <p style="margin: 5px 0 0; color: #047857; font-size: 20px; font-weight: 700;">%s</p>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td style="padding: 12px 0; border-top: 1px solid #a7f3d0;">
                                                                    <p style="margin: 0; color: #065f46; font-size: 14px; text-transform: uppercase; letter-spacing: 0.5px; font-weight: 600;">⏰ Hora</p>
                                                                    <p style="margin: 5px 0 0; color: #047857; font-size: 20px; font-weight: 700;">%s</p>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table>
                                            <!-- Tips -->
                                            <div style="margin: 25px 0; padding: 20px; background-color: #f0f9ff; border-left: 4px solid #3b82f6; border-radius: 4px;">
                                                <p style="margin: 0 0 10px; color: #1e40af; font-size: 14px; font-weight: 600;">Recomendaciones para tu asesoría:</p>
                                                <ul style="margin: 0; padding-left: 20px; color: #1e3a8a; font-size: 14px; line-height: 1.8;">
                                                    <li>Prepara tus preguntas con anticipación</li>
                                                    <li>Ten a mano materiales o código relevante</li>
                                                    <li>Verifica tu conexión a internet</li>
                                                    <li>Únete 5 minutos antes de la hora programada</li>
                                                </ul>
                                            </div>
                                            <p style="margin: 25px 0 0; color: #666666; font-size: 16px; line-height: 1.6;">
                                                Recibirás un recordatorio 30 minutos antes de tu asesoría.
                                            </p>
                                        </td>
                                    </tr>
                                    <!-- Footer -->
                                    <tr>
                                        <td style="background-color: #f8f9fa; padding: 30px; text-align: center; border-top: 1px solid #e9ecef;">
                                            <p style="margin: 0 0 10px; color: #666666; font-size: 14px;">
                                                ¿Tienes alguna pregunta? Contáctanos en cualquier momento.
                                            </p>
                                            <p style="margin: 0; color: #999999; font-size: 12px;">
                                                &copy; 2024 Portafolio Programadores. Todos los derechos reservados.
                                            </p>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </body>
                </html>
                """
                .formatted(solicitanteName, programadorName, date, time);
        sendEmail(solicitanteEmail, subject, htmlContent);
    }

    // Notificación de actualización de estado de asesoría
    public void sendAsesoriaStatusUpdate(
            String solicitanteName,
            String solicitanteEmail,
            String programadorName,
            String status) {
        // Determinar colores y mensajes basados en el estado
        String statusColor;
        String statusBgColor;
        String statusMessage;
        String statusIcon;

        switch (status.toUpperCase()) {
            case "CONFIRMADA", "CONFIRMED" -> {
                statusColor = "#059669";
                statusBgColor = "#d1fae5";
                statusMessage = "confirmada";
                statusIcon = "✓";
            }
            case "RECHAZADA", "REJECTED" -> {
                statusColor = "#dc2626";
                statusBgColor = "#fee2e2";
                statusMessage = "rechazada";
                statusIcon = "✕";
            }
            case "CANCELADA", "CANCELLED" -> {
                statusColor = "#ea580c";
                statusBgColor = "#fed7aa";
                statusMessage = "cancelada";
                statusIcon = "⚠";
            }
            case "COMPLETADA", "COMPLETED" -> {
                statusColor = "#0284c7";
                statusBgColor = "#e0f2fe";
                statusMessage = "completada";
                statusIcon = "★";
            }
            default -> {
                statusColor = "#2563eb";
                statusBgColor = "#dbeafe";
                statusMessage = status.toLowerCase();
                statusIcon = "ℹ";
            }
        }

        String subject = "Actualización de Estado de Asesoría";
        String htmlContent = """
                <!DOCTYPE html>
                <html lang="es">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                </head>
                <body style="margin: 0; padding: 0; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f4f4;">
                    <table role="presentation" cellpadding="0" cellspacing="0" width="100%%" style="background-color: #f4f4f4; padding: 20px 0;">
                        <tr>
                            <td align="center">
                                <table role="presentation" cellpadding="0" cellspacing="0" width="600" style="background-color: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 6px rgba(0,0,0,0.1);">
                                    <!-- Header -->
                                    <tr>
                                        <td style="background: linear-gradient(135deg, %s 0%%, %s 100%%); padding: 40px 30px; text-align: center;">
                                            <div style="background-color: #ffffff; width: 60px; height: 60px; border-radius: 50%%; margin: 0 auto 20px; display: flex; align-items: center; justify-content: center;">
                                                <span style="color: %s; font-size: 32px;">%s</span>
                                            </div>
                                            <h1 style="margin: 0; color: #ffffff; font-size: 28px; font-weight: 600;">Estado Actualizado</h1>
                                        </td>
                                    </tr>
                                    <!-- Content -->
                                    <tr>
                                        <td style="padding: 40px 30px;">
                                            <h2 style="margin: 0 0 20px; color: #333333; font-size: 24px; font-weight: 600;">Hola, %s</h2>
                                            <p style="margin: 0 0 25px; color: #666666; font-size: 16px; line-height: 1.6;">
                                                Te informamos que tu asesoría con <strong>%s</strong> ha sido:
                                            </p>
                                            <!-- Status Card -->
                                            <div style="margin: 30px 0; padding: 25px; background-color: %s; border-left: 4px solid %s; border-radius: 6px; text-align: center;">
                                                <p style="margin: 0; color: %s; font-size: 24px; font-weight: 700; text-transform: uppercase; letter-spacing: 1px;">
                                                    %s %s
                                                </p>
                                            </div>
                                            <p style="margin: 25px 0 0; color: #666666; font-size: 16px; line-height: 1.6;">
                                                Puedes revisar el estado actualizado de tus asesorías en tu panel de control.
                                            </p>
                                        </td>
                                    </tr>
                                    <!-- Footer -->
                                    <tr>
                                        <td style="background-color: #f8f9fa; padding: 30px; text-align: center; border-top: 1px solid #e9ecef;">
                                            <p style="margin: 0 0 10px; color: #999999; font-size: 14px;">
                                                Este es un correo automático, por favor no responder.
                                            </p>
                                            <p style="margin: 0; color: #999999; font-size: 12px;">
                                                &copy; 2024 Portafolio Programadores. Todos los derechos reservados.
                                            </p>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </body>
                </html>
                """
                .formatted(statusColor, statusColor, statusColor, statusIcon, solicitanteName, programadorName,
                        statusBgColor, statusColor, statusColor, statusIcon, statusMessage);
        sendEmail(solicitanteEmail, subject, htmlContent);
    }
}
