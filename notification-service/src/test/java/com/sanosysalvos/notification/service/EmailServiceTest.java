package com.sanosysalvos.notification.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EmailService - Pruebas unitarias")
class EmailServiceTest {

    private EmailService emailService;

    @BeforeEach
    void setUp() {
        emailService = new EmailService();
    }

    @Test
    @DisplayName("Debe enviar email sin lanzar excepcion")
    void debeEnviarEmailSinExcepcion() {
        NotificationRequest request = new NotificationRequest(
                "test@ejemplo.com",
                "Asunto de prueba",
                "Mensaje de prueba"
        );
        assertDoesNotThrow(() -> emailService.sendEmail(request));
    }

    @Test
    @DisplayName("Debe enviar notificacion de match sin lanzar excepcion")
    void debeEnviarNotificacionDeMatch() {
        assertDoesNotThrow(() ->
                emailService.sendMatchNotification("dueño@ejemplo.com", "+56912345678"));
    }

    @Test
    @DisplayName("Debe enviar confirmacion de registro sin lanzar excepcion")
    void debeEnviarConfirmacionDeRegistro() {
        assertDoesNotThrow(() ->
                emailService.sendRegistrationConfirmation("usuario@ejemplo.com", "Juan Pérez"));
    }

    @Test
    @DisplayName("NotificationRequest debe guardar correctamente los campos")
    void notificationRequestDebeGuardarCampos() {
        NotificationRequest request = new NotificationRequest(
                "test@ejemplo.com",
                "Asunto",
                "Mensaje"
        );
        assertEquals("test@ejemplo.com", request.getToEmail());
        assertEquals("Asunto", request.getSubject());
        assertEquals("Mensaje", request.getMessage());
    }

    @Test
    @DisplayName("NotificationRequest vacio debe permitir setters")
    void notificationRequestVacioDebePermitirSetters() {
        NotificationRequest request = new NotificationRequest();
        request.setToEmail("nuevo@ejemplo.com");
        request.setSubject("Nuevo asunto");
        request.setMessage("Nuevo mensaje");
        assertEquals("nuevo@ejemplo.com", request.getToEmail());
        assertEquals("Nuevo asunto", request.getSubject());
        assertEquals("Nuevo mensaje", request.getMessage());
    }
}