# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Spring Boot REST API backend (Java 17, Spring Boot 3.2.0) for a product/inventory management system. Designed to pair with a separate React frontend on `localhost:5173`.

## Commands

```bash
# Run the application
./mvnw spring-boot:run

# Build
./mvnw clean package

# Run tests
./mvnw test

# GraalVM native compile
./mvnw native:compile -Pnative
```

On Windows use `mvnw.cmd` instead of `./mvnw`.

**Prerequisites:** PostgreSQL running on `localhost:5432` with database `SematexFIN` and user `postgres`.

## Architecture

Layered architecture: **Controller → Service (interface + impl) → Repository → Entity**

```
src/main/java/com/Proyecto/ProyectoSematext/
├── Controller/          # REST endpoints (CORS configured for localhost:5173)
├── Service/             # Business logic interfaces
│   └── Impl/            # Service implementations
├── Repository/          # Spring Data JPA repositories
├── Entity/              # JPA entities (7 total)
├── DTO/                 # Data Transfer Objects for request/response
├── config/              # SecurityConfig (BCrypt bean)
└── Mapper/              # Planned mapper classes (currently empty)
```

**Database entities and relationships:**
- `UsuarioEntity` (Many-to-One → `RolEntity`) — users with roles
- `ProductoEntity` (Many-to-One → `CategoriaEntity`, `UnidadMedidaEntity`) — products
- `PedidosEntity` (Many-to-One → `UsuarioEntity`) — orders
- `ProveedorEntity`, `CategoriaEntity`, `UnidadMedidaEntity`, `RolEntity` — supporting tables

Hibernate auto-updates the schema (`spring.jpa.hibernate.ddl-auto=update`).

## API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| POST | `/Usuario/login` | Authenticate with `username`/`password`, returns user on success |
| POST | `/Usuario/Registrarusuario` | Register new user |

`ControladorPedido` and `ProductoController` exist but have no implemented endpoints yet.

## Key Patterns

- **Password handling:** BCrypt encoding via `SecurityConfig` bean; login uses `passwordEncoder.matches()` — never store or compare plain-text passwords.
- **Error responses:** Controllers return `ResponseEntity` with HTTP status codes (`UNAUTHORIZED`, `BAD_REQUEST`, `OK`).
- **CORS:** Configured per-controller with `@CrossOrigin(origins = "http://localhost:5173")`.
- **Lombok:** Entities and DTOs use `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor` — no manual getters/setters needed.
- **New services:** Follow the existing pattern — create an interface in `Service/`, implement in `Service/Impl/`, inject the interface in controllers.

## Endpoint Rules (mandatory for every endpoint)

Every controller method must follow all of these rules:

1. **HTTP method** — Use the semantically correct verb (GET to read, POST to create, PUT/PATCH to update, DELETE to remove).
2. **Input validation** — Validate all request body fields and path/query params before any business logic.
3. **Error handling** — Wrap logic in try/catch; never let exceptions propagate unhandled to the client.
4. **Standard JSON envelope** — Always return `{ "success": boolean, "data": ..., "error": ... }`.
5. **No internal leakage** — Never expose stack traces, SQL errors, or internal messages in responses.
6. **Correct HTTP codes** — 200 OK, 201 Created, 400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 500 Internal Server Error.
7. **Javadoc** — Document every controller method with a Javadoc comment describing purpose, params, and return.
8. **Layered separation** — Controllers handle HTTP only; business logic lives in services; persistence in repositories.
9. **Security** — Every non-public endpoint must verify authentication and authorization.
10. **Consistency** — Naming conventions, response shape, and error format must be uniform across all endpoints.
