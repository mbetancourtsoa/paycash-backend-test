# item-service

Microservicio Spring Boot que expone información de `Item`, `Review` y `User`, desarrollado como prueba técnica de backend.

## Descripción

El servicio implementa tres capas:

- **`dao`** — acceso a datos con Spring Data JPA (`ItemRepository`, `ReviewRepository`, `UserRepository`).
- **`service`** — lógica de negocio (`ItemService`).
- **`controller`** — capa web REST (`ItemController`).

### Endpoint principal

```
GET /titles?rating={rating}
```

Devuelve, en JSON UTF-8, los títulos de los `Item` cuyo rating promedio (calculado a partir de sus `Review`) es **menor** al valor indicado. Si un item no tiene reviews, su rating se considera `0`.

El cálculo se resuelve con una sola consulta JPQL (subquery correlacionada con `AVG` + `COALESCE`), evitando el problema N+1 y minimizando los viajes a la base de datos.

**Ejemplo:**

```bash
curl "http://localhost:8080/titles?rating=3.5"
# ["Auriculares Bluetooth","Mouse Gamer","Silla Ergonómica"]
```

Si el parámetro `rating` falta o no es válido, el servicio responde `400 Bad Request` con un cuerpo de error estandarizado:

```json
{
  "timestamp": "2026-07-15T23:27:22.593735631Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Required request parameter 'rating' for method parameter type Double is not present",
  "path": "/titles"
}
```

## Stack técnico

- Java 21 / Spring Boot 4.1.0
- Spring Data JPA + Hibernate
- PostgreSQL (runtime) y H2 (solo para tests)
- Flyway (migraciones de esquema y datos semilla)
- Lombok
- springdoc-openapi (Swagger UI)
- JUnit 5, Mockito, MockMvc

## Estructura del proyecto

```
.
├── docker-compose.yml       # Levanta app + PostgreSQL
├── .env.example             # Variables de entorno para docker-compose
├── Dockerfile
├── pom.xml
└── src/
    ├── main/java/co/com/paycash/itemservice/
    │   ├── model/        # Item, Review, User
    │   ├── dao/          # Repositorios JPA
    │   ├── service/      # Lógica de negocio
    │   ├── controller/   # Endpoints REST
    │   └── exception/    # Manejo centralizado de errores
    ├── main/resources/
    │   ├── application.properties
    │   └── db/migration/ # Migraciones Flyway (V1__init.sql, V2__seed.sql)
    └── test/java/...     # Tests de repository, service y controller
```

## Cómo ejecutarlo

### Opción 1: Docker Compose (recomendada)

Levanta la aplicación junto con una base de datos PostgreSQL en un solo comando.

```bash
cp .env.example .env   # opcional, ya trae valores por defecto
docker compose up --build
```

La app queda disponible en `http://localhost:8080`. Flyway aplica las migraciones y los datos semilla automáticamente al arrancar.

Para detener y limpiar:

```bash
docker compose down
```

### Opción 2: Local con Maven

Requiere una instancia de PostgreSQL accesible (o ajustar `application.properties` a tu entorno).

```bash
./mvnw spring-boot:run
```

Variables de entorno soportadas (con valores por defecto para desarrollo local):

| Variable                     | Default                                   |
|-------------------------------|--------------------------------------------|
| `SPRING_DATASOURCE_URL`       | `jdbc:postgresql://localhost:5432/itemdb` |
| `SPRING_DATASOURCE_USERNAME`  | `itemuser`                                 |
| `SPRING_DATASOURCE_PASSWORD`  | `itempass`                                 |

## Tests

```bash
./mvnw test
```

Incluye:
- `ItemRepositoryTest` — valida la consulta de rating promedio contra H2 (`@DataJpaTest`).
- `ItemServiceImplTest` — valida el mapeo a títulos con Mockito.
- `ItemControllerTest` — valida el endpoint REST y los casos de error con `@WebMvcTest`.

## Documentación de la API

Con la app corriendo, la documentación interactiva (Swagger UI) está disponible en:

```
http://localhost:8080/swagger-ui.html
```

## Consideraciones de diseño

- **Consulta optimizada**: el rating promedio se calcula en una única query JPQL en base de datos, en vez de cargar reviews en memoria por item.
- **Persistencia versionada**: el esquema se gestiona con Flyway (`ddl-auto=validate`) en vez de generación automática, alineado a un flujo de producción.
- **Manejo de errores uniforme**: un `@RestControllerAdvice` centraliza las respuestas de error con un formato JSON consistente.
- **Entorno reproducible**: `docker-compose.yml` permite levantar app + base de datos con un solo comando, sin dependencias locales adicionales.
