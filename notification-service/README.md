# Notification Service — Microservicio de Notificaciones

Microservicio responsable del envío de notificaciones por correo electrónico a los usuarios de la plataforma Sanos y Salvos.

## Puerto

`8086`

## Tecnologías

- Java 17
- Spring Boot 3.4.4
- Maven

## Endpoints (protegidos con JWT)

| Método | Ruta | Descripción |
|---|---|---|
| POST | `/api/notifications/send` | Enviar notificación personalizada |
| POST | `/api/notifications/match` | Notificar coincidencia al dueño |

### Ejemplo — Enviar notificación

```json
POST /api/notifications/send
{
  "toEmail": "juan@ejemplo.com",
  "subject": "Tu mascota fue encontrada",
  "message": "Hemos encontrado una coincidencia para tu mascota."
}
```

### Ejemplo — Notificar match

```
POST /api/notifications/match?ownerEmail=juan@ejemplo.com&finderPhone=+56912345678
```

## Requisitos previos

- Java 17+
- Maven 3.8+

## Instalación y ejecución

```bash
cd notification-service
mvn clean install
mvn spring-boot:run
```

El servicio quedará disponible en `http://localhost:8086`

## Patrones implementados

- **Service Layer:** `EmailService` contiene la lógica de construcción y envío de mensajes, separada del controlador.
- **Strategy Pattern (preparado):** `EmailService` está diseñado para recibir distintas implementaciones de envío (consola en desarrollo, SMTP en producción) sin cambiar el controlador.

## Estructura del proyecto

```
notification-service/
├── src/main/java/com/sanosysalvos/notification/
│   ├── NotificationServiceApplication.java
│   ├── controller/
│   │   └── NotificationController.java
│   └── service/
│       ├── EmailService.java
│       └── NotificationRequest.java
└── pom.xml
```

## Repositorio

https://github.com/cesarin2005/Sanos-y-Salvos-Backend
