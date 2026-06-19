# Lost Pet Service — Microservicio de Mascotas Perdidas

Microservicio responsable del registro y gestión de reportes de mascotas perdidas.

## Puerto

`8082`

## Endpoints (protegidos con JWT)

| Método | Ruta | Descripción |
|---|---|---|
| POST | `/api/lost-pets` | Registrar mascota perdida |
| GET | `/api/lost-pets` | Listar todas las mascotas perdidas |
| GET | `/api/lost-pets/{id}` | Obtener reporte por ID |
| PUT | `/api/lost-pets/{id}` | Actualizar reporte |
| DELETE | `/api/lost-pets/{id}` | Eliminar reporte |

## Requisitos previos

- Java 17+, Maven 3.8+, MySQL en `localhost:3306`

## Instalación y ejecución

```bash
cd lost-pet-service
mvn clean install
mvn spring-boot:run
```

## Patrones implementados

- **Repository Pattern:** abstracción del acceso a la base de datos via Spring Data JPA.
- **Service Layer:** lógica de negocio separada del controlador.
- **MVC:** separación clara entre Controller, Service y Model.

## Repositorio

https://github.com/cesarin2005/Sanos-y-Salvos-Backend

---

# Found Pet Service — Microservicio de Mascotas Encontradas

Microservicio responsable del registro y gestión de reportes de mascotas encontradas.

## Puerto

`8083`

## Endpoints (protegidos con JWT)

| Método | Ruta | Descripción |
|---|---|---|
| POST | `/api/found-pets` | Registrar mascota encontrada |
| GET | `/api/found-pets` | Listar todas las mascotas encontradas |
| GET | `/api/found-pets/{id}` | Obtener reporte por ID |
| PUT | `/api/found-pets/{id}` | Actualizar reporte |
| DELETE | `/api/found-pets/{id}` | Eliminar reporte |

## Requisitos previos

- Java 17+, Maven 3.8+, MySQL en `localhost:3306`

## Instalación y ejecución

```bash
cd found-pet-service
mvn clean install
mvn spring-boot:run
```

## Patrones implementados

- **Repository Pattern**, **Service Layer**, **MVC** (mismos que lost-pet-service).

## Repositorio

https://github.com/cesarin2005/Sanos-y-Salvos-Backend

---

# Matching Service — Microservicio de Coincidencias

Microservicio que cruza reportes de mascotas perdidas con las encontradas para detectar posibles coincidencias. Se comunica con `lost-pet-service` y `found-pet-service` vía HTTP.

## Puerto

`8084`

## Endpoints (protegidos con JWT)

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/matches` | Listar todas las coincidencias |
| POST | `/api/matches/run` | Ejecutar el proceso de matching |
| GET | `/api/matches/{id}` | Ver detalle de coincidencia |

## Comunicación entre servicios

Usa `PetHttpClient` para consultar las APIs de `lost-pet-service` (puerto 8082) y `found-pet-service` (puerto 8083).

```yaml
services:
  lost-pet-url: http://localhost:8082
  found-pet-url: http://localhost:8083
```

## Requisitos previos

- Java 17+, Maven 3.8+, MySQL en `localhost:3306`
- lost-pet-service y found-pet-service corriendo

## Instalación y ejecución

```bash
cd matching-service
mvn clean install
mvn spring-boot:run
```

## Patrones implementados

- **Repository Pattern**, **Service Layer**, **MVC**.
- **DTO Pattern:** `PetDtos` para transferir datos entre microservicios.
- **HTTP Client Pattern:** `PetHttpClient` encapsula la comunicación inter-servicio.

## Repositorio

https://github.com/cesarin2005/Sanos-y-Salvos-Backend
