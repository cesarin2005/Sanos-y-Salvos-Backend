# API Gateway — Backend For Frontend (BFF)

El **API Gateway** actúa como **Backend For Frontend (BFF)** de la plataforma Sanos y Salvos. Centraliza todas las peticiones del frontend, aplica autenticación JWT y enruta el tráfico hacia los microservicios internos.

## Puerto

`8080`

## Tecnologías

- Java 17
- Spring Boot 3.4.4
- Spring Cloud Gateway
- JSON Web Token (JJWT 0.12.5)
- Maven (arquetipo `spring-boot-starter-parent`)

## Patrón arquitectónico

**Backend For Frontend (BFF):** el gateway expone una única entrada al sistema. El frontend solo necesita conocer un endpoint (`http://localhost:8080`). El gateway resuelve el routing interno y aplica el filtro `AuthFilter` para validar el JWT en todas las rutas protegidas.

## Rutas configuradas

| Ruta | Microservicio destino | Autenticación |
|---|---|---|
| `/api/auth/**` | user-service (8081) | Pública |
| `/api/users/**` | user-service (8081) | Pública |
| `/api/lost-pets/**` | lost-pet-service (8082) | JWT requerido |
| `/api/found-pets/**` | found-pet-service (8083) | JWT requerido |
| `/api/matches/**` | matching-service (8084) | JWT requerido |
| `/api/adoptions/**` | adoption-service (8085) | JWT requerido |
| `/api/notifications/**` | notification-service (8086) | JWT requerido |

## Requisitos previos

- Java 17+
- Maven 3.8+
- Microservicios del backend corriendo en sus puertos respectivos

## Instalación y ejecución

```bash
cd api-gateway
mvn clean install
mvn spring-boot:run
```

El gateway quedará disponible en `http://localhost:8080`

## Variables de configuración (`applicaton.yml`)

```yaml
jwt:
  secret: sanosysalvos-super-secret-key-2024-production-ready-256bits
  expiration: 86400000   # 24 horas en milisegundos
```

> **Nota:** En producción, el `jwt.secret` debe definirse como variable de entorno.

## Estructura del proyecto

```
api-gateway/
├── src/main/java/com/sanosysalvos/gateway/
│   ├── ApiGatewayApplication.java
│   └── filter/
│       └── AuthFilter.java          # Filtro JWT personalizado
├── src/main/resources/
│   └── applicaton.yml
└── pom.xml
```

## Repositorio

https://github.com/cesarin2005/Sanos-y-Salvos-Backend
