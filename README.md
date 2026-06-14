# Sanos y Salvos - Backend

## Arquetipos Maven

Este proyecto utiliza un Parent POM centralizado como base para todos los microservicios,
funcionando como arquetipo para generar nuevos módulos de forma consistente.

### Estructura del arquetipo

sanos-y-salvos-parent/
pom.xml                  ← Parent POM con dependencias compartidas
api-gateway/             ← BFF con Spring Cloud Gateway
user-service/            ← Microservicio de usuarios
lost-pet-service/        ← Microservicio de mascotas perdidas
found-pet-service/       ← Microservicio de mascotas encontradas
matching-service/        ← Microservicio de matching
adoption-service/        ← Microservicio de adopciones
notification-service/    ← Microservicio de notificaciones

### Cómo generar un nuevo microservicio basado en este arquetipo

1. Instalar el parent POM en el repositorio local:
```bash
mvn install -N
```

2. Crear un nuevo módulo heredando del parent:
```xml
<parent>
    <groupId>com.sanosysalvos</groupId>
    <artifactId>sanos-y-salvos-parent</artifactId>
    <version>1.0.0</version>
</parent>
<artifactId>nuevo-servicio</artifactId>
```

3. Agregar el nuevo módulo al `pom.xml` raíz:
```xml
<modules>
    ...
    <module>nuevo-servicio</module>
</modules>
```

4. Compilar todo el proyecto:
```bash
mvn clean package -DskipTests
```

### Tecnologías

- Java 17
- Spring Boot 3.5.1
- Spring Cloud 2024.0.1
- MySQL 8.0
- Maven 3.x
