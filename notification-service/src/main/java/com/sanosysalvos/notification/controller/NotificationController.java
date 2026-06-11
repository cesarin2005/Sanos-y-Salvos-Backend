package com.sanosysalvos.notification.controller;

import com.sanosysalvos.notification.service.EmailService;
import com.sanosysalvos.notification.service.NotificationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final EmailService emailService;

    public NotificationController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody NotificationRequest request) {
        emailService.sendEmail(request);
        return ResponseEntity.ok("Notificación enviada correctamente");
    }

    @PostMapping("/match")
    public ResponseEntity<String> notifyMatch(@RequestParam String ownerEmail,
                                               @RequestParam String finderPhone) {
        emailService.sendMatchNotification(ownerEmail, finderPhone);
        return ResponseEntity.ok("Notificación de match enviada");
    }
}
