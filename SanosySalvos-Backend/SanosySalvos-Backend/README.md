# Sanos y Salvos — Backend

Backend de la plataforma **Sanos y Salvos**, construido con arquitectura de microservicios usando Spring Boot y Maven.

## Arquitectura

```
                        ┌─────────────────────┐
  Frontend (puerto 3000)│                     │
         ───────────────►   API Gateway (BFF)  │  :8080
                        │   api-gateway        │
                        └──────────┬──────────┘
                                   │  enruta + valida JWT
              ┌────────────────────┼────────────────────┐
              │                    │                    │
    ┌─────────▼──────┐  ┌──────────▼──────┐  ┌────────▼────────┐
    │  user-service  │  │lost-pet-service  │  │found-pet-service│
    │    :8081       │  │     :8082        │  │     :8083       │
    └────────────────┘  └─────────────────┘  └─────────────────┘
              │                    │                    │
    ┌─────────▼──────┐  ┌──────────▼──────┐
    │matching-service│  │notification-svc  │
    │    :8084       │  │     :8086        │
    └────────────────┘  └─────────────────┘
```

## Microservicios

| Servicio | Puerto | Descripción | README |
|---|---|---|---|
| api-gateway | 8080 | BFF: entrada única, routing y JWT | [Ver](api-gateway/README.md) |
| user-service | 8081 | Registro y autenticación de usuarios | [Ver](user-service/README.md) |
| lost-pet-service | 8082 | Reportes de mascotas perdidas | [Ver](lost-pet-service/README.md) |
| found-pet-service | 8083 | Reportes de mascotas encontradas | [Ver](found-pet-service/README.md) |
| matching-service | 8084 | Coincidencias entre reportes | [Ver](matching-service/README.md) |
| notification-service | 8086 | Notificaciones por email | [Ver](notification-service/README.md) |

## Requisitos previos

- Java 17+
- Maven 3.8+
- MySQL 8+ corriendo en `localhost:3306`

## Levantar todo el backend

```bash
# Desde la raíz del proyecto (compila todos los módulos)
mvn clean install

# Luego levantar cada servicio en terminales separadas:
cd api-gateway       && mvn spring-boot:run &
cd user-service      && mvn spring-boot:run &
cd lost-pet-service  && mvn spring-boot:run &
cd found-pet-service && mvn spring-boot:run &
cd matching-service  && mvn spring-boot:run &
```

## Tecnologías

- Java 17 / Spring Boot 3.4.4
- Spring Cloud Gateway
- Spring Security + JWT (JJWT 0.12.5)
- Spring Data JPA + Hibernate
- MySQL
- Maven (multi-módulo, arquetipo `spring-boot-starter-parent`)

## Patrones implementados

- **Backend For Frontend (BFF):** api-gateway como entrada única
- **Repository Pattern:** abstracción del acceso a datos en cada microservicio
- **DTO Pattern:** objetos de transferencia separados del modelo de dominio
- **Builder Pattern:** construcción limpia de entidades y respuestas
- **Service Layer:** lógica de negocio separada de los controladores
- **Filter Pattern:** AuthFilter centraliza la validación JWT

## Repositorio Frontend

https://github.com/cesarin2005/Sanos-y-Salvos-Frontend
