package com.sanosysalvos.notification.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EmailService - Pruebas unitarias")
class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    @DisplayName("Debe enviar email con los datos correctos")
    void debeEnviarEmail() {
        NotificationRequest request = new NotificationRequest(
                "test@email.com",
                "Asunto de prueba",
                "Mensaje de prueba"
        );

        emailService.sendEmail(request);

        String output = outputStream.toString();
        assertTrue(output.contains("NOTIFICACIÓN EMAIL"));
        assertTrue(output.contains("test@email.com"));
        assertTrue(output.contains("Asunto de prueba"));
        assertTrue(output.contains("Mensaje de prueba"));
    }

    @Test
    @DisplayName("Debe enviar notificación de coincidencia de mascota")
    void debeEnviarNotificacionDeCoincidencia() {
        String ownerEmail = "dueno@email.com";
        String finderPhone = "+56912345678";

        emailService.sendMatchNotification(ownerEmail, finderPhone);

        String output = outputStream.toString();
        assertTrue(output.contains("NOTIFICACIÓN EMAIL"));
        assertTrue(output.contains("dueno@email.com"));
        assertTrue(output.contains("Posible coincidencia encontrada"));
        assertTrue(output.contains("+56912345678"));
    }

    @Test
    @DisplayName("Debe enviar confirmación de registro al usuario")
    void debeEnviarConfirmacionDeRegistro() {
        String userEmail = "nuevo@email.com";
        String userName = "Carlos";

        emailService.sendRegistrationConfirmation(userEmail, userName);

        String output = outputStream.toString();
        assertTrue(output.contains("NOTIFICACIÓN EMAIL"));
        assertTrue(output.contains("nuevo@email.com"));
        assertTrue(output.contains("Bienvenido a Sanos y Salvos"));
        assertTrue(output.contains("Carlos"));
    }

    @Test
    @DisplayName("Debe incluir teléfono del finder en notificación de coincidencia")
    void debeIncluirTelefonoEnNotificacion() {
        emailService.sendMatchNotification("dueno@email.com", "+56987654321");

        String output = outputStream.toString();
        assertTrue(output.contains("+56987654321"));
    }

    @Test
    @DisplayName("Debe incluir nombre del usuario en confirmación de registro")
    void debeIncluirNombreEnConfirmacion() {
        emailService.sendRegistrationConfirmation("usuario@email.com", "María");

        String output = outputStream.toString();
        assertTrue(output.contains("María"));
    }
}
