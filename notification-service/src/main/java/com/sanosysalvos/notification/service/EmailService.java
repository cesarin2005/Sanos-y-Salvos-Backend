package com.sanosysalvos.notification.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendEmail(NotificationRequest request) {
        // Patrón Strategy: en producción se inyectaría un JavaMailSender
        // En desarrollo se loguea la notificación
        System.out.println("=== NOTIFICACIÓN EMAIL ===");
        System.out.println("Para: " + request.getToEmail());
        System.out.println("Asunto: " + request.getSubject());
        System.out.println("Mensaje: " + request.getMessage());
        System.out.println("=========================");
    }

    public void sendMatchNotification(String ownerEmail, String finderPhone) {
        NotificationRequest request = new NotificationRequest(
            ownerEmail,
            "Alguien encontró una mascota similar a la tuya",
            "Hemos encontrado una mascota que coincide con las características de la tuya. " +
            "Para confirmar, comunícate con quien la encontró al teléfono: " + finderPhone + "."
    );
    sendEmail(request);
}

    public void sendRegistrationConfirmation(String userEmail, String userName) {
        NotificationRequest request = new NotificationRequest(
                userEmail,
                "Bienvenido a Sanos y Salvos",
                "Hola " + userName + ", tu cuenta ha sido creada exitosamente. " +
                "Ya puedes reportar mascotas perdidas o encontradas."
        );
        sendEmail(request);
    }
}
