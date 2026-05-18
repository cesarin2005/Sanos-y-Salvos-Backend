# User Service — Microservicio de Usuarios

Microservicio responsable del registro, autenticación y gestión de usuarios de la plataforma Sanos y Salvos. Emite tokens JWT que son validados por el API Gateway.

## Puerto

`8081`

## Tecnologías

- Java 17
- Spring Boot 3.4.4
- Spring Security (autenticación stateless)
- Spring Data JPA + Hibernate
- MySQL
- JJWT 0.12.5
- Maven

## Endpoints

| Método | Ruta | Descripción | Auth |
|---|---|---|---|
| POST | `/api/auth/register` | Registrar nuevo usuario | No |
| POST | `/api/auth/login` | Iniciar sesión, retorna JWT | No |

### Ejemplo — Registro

```json
POST /api/auth/register
{
  "name": "Juan Pérez",
  "email": "juan@ejemplo.com",
  "password": "123456",
  "phone": "+56912345678",
  "city": "Santiago"
}
```

Respuesta `200 OK`:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "name": "Juan Pérez"
}
```

### Ejemplo — Login

```json
POST /api/auth/login
{
  "email": "juan@ejemplo.com",
  "password": "123456"
}
```

## Base de datos

- Motor: MySQL
- Base de datos: `users_db` (se crea automáticamente)
- La tabla `users` se genera con `ddl-auto: update`

## Requisitos previos

- Java 17+
- Maven 3.8+
- MySQL corriendo en `localhost:3306`

## Instalación y ejecución

```bash
cd user-service
mvn clean install
mvn spring-boot:run
```

## Configuración (`application.yml`)

```yaml
server:
  port: 8081
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/users_db?createDatabaseIfNotExist=true
    username: root
    password:           # Completar con tu contraseña local
jwt:
  secret: sanosysalvos-super-secret-key-2024-production-ready-256bits
  expiration: 86400000
```

## Patrones implementados

- **Repository Pattern:** `UserRepository` extiende `JpaRepository` para abstraer el acceso a datos.
- **DTO Pattern:** `AuthDtos` (RegisterRequest, LoginRequest, AuthResponse) separa los datos de entrada/salida del modelo de dominio.
- **Builder Pattern:** `User.builder()` y `AuthResponse.builder()` para construcción limpia de objetos.
- **Service Layer:** `AuthService` centraliza la lógica de negocio, separada del controlador.

## Estructura del proyecto

```
user-service/
├── src/main/java/com/sanosysalvos/user/
│   ├── UserServiceApplication.java
│   ├── config/
│   │   └── SecurityConfig.java          # Configuración Spring Security
│   ├── controller/
│   │   └── AuthController.java          # Endpoints REST
│   ├── dto/
│   │   └── AuthDtos.java                # DTOs de entrada y salida
│   ├── model/
│   │   └── User.java                    # Entidad JPA con Builder
│   ├── repository/
│   │   └── UserRepository.java          # Acceso a datos
│   ├── security/
│   │   └── JwtService.java              # Generación y validación JWT
│   └── service/
│       └── AuthService.java             # Lógica de negocio
└── pom.xml
```

## Repositorio

https://github.com/cesarin2005/Sanos-y-Salvos-Backend
