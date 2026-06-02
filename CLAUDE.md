# Project Instructions

This file contains custom instructions for Claude Code CLI to help development on the **StoreVision** project. Follow all rules strictly when generating, modifying, or reviewing code.

---

## Core Stack

| Layer            | Technology                                                                 |
|------------------|----------------------------------------------------------------------------|
| Language         | Java 25 (via Gradle toolchain: `JavaLanguageVersion.of(25)`)               |
| Framework        | Spring Boot 4.0.6, Spring Modulith 2.0.6                                  |
| Data Access      | Spring Data JPA, Spring Data JDBC, Hibernate (PostgreSQL dialect)          |
| Database         | PostgreSQL (HikariCP connection pool, Liquibase 4.29.0 migrations)         |
| Security         | Spring Security + OAuth2 (Google provider), custom filter chain            |
| API Docs         | SpringDoc OpenAPI 3.0.2 / Swagger UI                                       |
| Templating       | Thymeleaf 3.x (with Spring Security extras)                                |
| Batch            | Spring Batch                                                               |
| Mapping          | MapStruct (Entity ↔ DTO conversions)                                       |
| Boilerplate      | Lombok (constructor properties + chaining accessors enabled via lombok.config) |
| Build Tool       | Gradle 9.4.1 (wrapper: `./gradlew`)                                        |
| Infrastructure   | Docker / Docker Compose (PostgreSQL service via `compose.yaml`)            |
| Observability    | Spring Boot Actuator                                                       |
| i18n             | Spring MessageSource (`/i18n/messages` basenames, Vietnamese + English)    |

---

## Style & Engineering Principles

### 1. Package & Naming Conventions

- **Root package**: `com.nthn.storevision`
- **Sub-packages**: all lowercase, singular nouns (e.g., `controller`, `service`, `repository`, `entity`, `dto`, `mapper`, `config`, `exception`, `factory`, `util`)
- **Classes**: `PascalCase` (e.g., `OrderCreationService`, `CustomerMapper`)
- **Methods & variables**: `camelCase`
- **Constants**: `UPPER_SNAKE_CASE`
- **API versioning**: place versioned controllers under `controller/v1/` and name them `<Domain>ControllerV1`
- **Config/property keys**: `snake_case` (Jackson `SNAKE_CASE` naming strategy is project-wide)

### 2. Layered Architecture

Strictly follow the four-layer separation. Never skip a layer or mix concerns:

```
Controller (v1/)
    └── Service (operation-specific: Creation / Retrieval / Update / Deletion)
            └── Repository (interface + impl/ + jpa/)
                    └── Entity (entity/ + entity/mapped/)
```

- **Controllers**: Only handle HTTP concerns (request mapping, response wrapping, input validation via `@Valid`). Delegate all logic to services.
- **Services**: One service class per operation per domain (e.g., `OrderCreationService`, `OrderRetrievalService`). Implement generic interfaces from `service/generic/` (`Creatable`, `Readable`, `Updatable`, `Deletable`, `SoftDeletable`, `HardDeletable`).
- **Repositories**: Define interface in `repository/`, implementation in `repository/impl/`, Spring Data JPA repository in `repository/jpa/`.
- **Entities**: Extend `BaseEntity` for all auditable entities. Never expose entities directly through API — always convert via Mapper.

### 3. Service Design Rules

- Use **abstract base classes** in `service/common/` (`AbstractCreatable`, `AbstractReadable`) for shared behavior.
- Use **generic interfaces** in `service/generic/` for contract definition.
- Each concrete `impl/` class (e.g., `CustomerServiceImpl`) aggregates operation-specific services — it is the single entry point per domain for the controller.
- Never call a `Repository` directly from a `Controller` or another `Service`'s domain.

### 4. DTO & Mapping Rules

- Always use **DTOs** (`dto/`) to expose data through the API — never JPA entities directly.
- Use **MapStruct** mappers (`mapper/`) for all entity ↔ DTO conversions. Do not write manual mapping code.
- Wrap all API responses in `ApiSuccessResponse` or `ApiErrorResponse` from `dto/response/`. Use `ApiResponseFactory` / `ApiErrorResponseFactory` / `ApiSuccessResponseFactory` from `factory/` to construct them.

### 5. Exception Handling

- Define all custom exceptions in the `exception/` package, extending `ApplicationException`.
- **Never** use `System.out.println()` for logging or error output.
- All exceptions must be caught and handled by the global exception handler in `advice/`.
- Use `EntityNotFoundException` for missing entities and `EntityAccessException` for authorization failures.
- Error messages must be externalized to `/i18n/messages` properties files to support i18n.

### 6. Logging

- Use **SLF4J** (`@Slf4j` from Lombok) exclusively for all logging.
- Log at the correct level: `DEBUG` for service/logic flow, `INFO` for significant state changes, `WARN` for recoverable issues, `ERROR` for exceptions.
- Refer to `application-logging.yml`: project package `com.nthn` logs at `DEBUG`, root at `INFO`.

### 7. Security Rules

- Security configuration lives exclusively in `SecurityConfig.java` under `security/config/`.
- The `security/` package is structured into focused sub-packages: `config/` (SecurityConfig), `context/` (UserContext), `filter/` (UserInfoFilter), `oauth/` (OAuth2SuccessHandler), `user/` (CustomUserDetailsService, UserPrincipal).
- OAuth2 Google authentication is configured in `application-authentication.yml` — do not hardcode credentials.
- Never expose internal user/session data outside the `security/` boundary without going through `UserContext` in `security/context/`.
- Keep security-related components (JWT provider, custom filters, authentication configuration) isolated inside the `security/` sub-package hierarchy — never scatter them into generic packages such as `component/` or the top-level `config/`.

### 8. Database & Migrations

- **All schema changes** must be applied via **Liquibase** changesets — never modify the database schema directly or use `spring.jpa.hibernate.ddl-auto=update/create`.
- Connection pool settings (`max-pool-size`, `connection-timeout`) are externalized via environment variables (`${DATASOURCE_MAX_POOL_SIZE}`, `${DATASOURCE_URL}`).
- Use **soft delete** (via `deleted_by` / `deleted_date` audit fields on `BaseEntity`) unless a hard delete is explicitly required by business logic.

### 9. Auditing

- All entities must extend `BaseEntity` which provides `createdBy`, `createdDate`, `lastModifiedBy`, `lastModifiedDate`, `deletedBy`, `deletedDate`.
- The `CustomAuditingEntityListener` and `UserAuditorAware` in `component/audit/` handle automatic population — do not set these fields manually.
- Use `@DeletedBy` / `@DeletedDate` annotations (from `annotation/`) for soft delete fields.

### 10. API Documentation

- All controllers and endpoints must include **SpringDoc/OpenAPI annotations** (`@Tag`, `@Operation`, `@ApiResponse`) so the Swagger UI at `/swagger-ui.html` stays accurate.
- API base path: `/store-vision/api`
- Swagger config: `application-swagger.yml` — do not duplicate configuration elsewhere.

### 11. Modulith Boundaries

- This project uses **Spring Modulith**. Use `@NamedInterface` to declare explicit module boundaries.
- Do not create cross-module dependencies that bypass the declared interfaces.
- Run `ApplicationModularityTests` to verify module structure before merging changes.

### 12. Testing Standards

- **Mandatory**: write unit tests for all service-layer logic using **JUnit 5** (Jupiter) and **Spring Boot Test**.
- Security-related tests must use **Spring Security Test** (`@WithMockUser`, `SecurityMockMvcRequestPostProcessors`).
- Batch jobs must be tested with **Spring Batch Test**.
- Minimum code coverage target: **80%** for service layer classes.
- Test classes mirror the source package structure under `src/test/java/com/nthn/storevision/`.
- Use Spring's `@DataJpaTest` (or `@JdbcTest`) for repository layer tests — do not spin up the full application context for persistence tests.
- Do **not** use mocked databases when integration behavior is under test; prefer the real PostgreSQL via `compose.yaml`.

### 13. Code Quality Rules

- **No raw `System.out` or `printStackTrace()`** anywhere in the codebase.
- **No magic strings or numbers** — externalize to constants in `constant/` or message properties in `i18n/`.
- Use constructor injection (Lombok `@RequiredArgsConstructor`) — never field injection (`@Autowired` on fields).
- Prefer `Optional<T>` over `null` returns in service and repository methods.
- Validate all incoming request bodies with Bean Validation (`@Valid`, `@NotNull`, `@NotBlank`, etc.) at the controller boundary.
- Factory classes in `factory/` must be used for constructing response objects — do not construct `ApiResponse` subclasses inline.

---

## Operational Instructions

### Build & Run Commands

```bash
# Run all tests
./gradlew test

# Build the project (skip tests)
./gradlew build -x test

# Full clean build with tests
./gradlew clean build

# Run the application locally
./gradlew bootRun

# Start dependent services (PostgreSQL via Docker Compose)
docker compose up -d

# Stop services
docker compose down

# Build OCI/Docker image
./gradlew bootBuildImage
```

### Directories & Files to Ignore

Do **not** read, index, or suggest changes inside the following paths — they are generated or transient:

```
build/
.gradle/
out/
logs/
*.class
*.jar
*.war
```

### Environment Variables

The following **must** be provided at runtime (never hardcode in source):

| Variable                          | Purpose                                    |
|-----------------------------------|--------------------------------------------|
| `DATASOURCE_URL`                  | PostgreSQL JDBC connection URL             |
| `DATASOURCE_MAX_POOL_SIZE`        | HikariCP max pool size                     |
| `LIQUIBASE_CHANGE_LOG`            | Path to the Liquibase master changelog     |
| `LIQUIBASE_DEFAULT_SCHEMA`        | Default schema for Liquibase operations    |
| `LIQUIBASE_CHANGE_LOG_TABLE`      | Liquibase tracking table name              |
| `LIQUIBASE_CHANGE_LOG_LOCK_TABLE` | Liquibase lock table name                  |
| `GOOGLE_CLIENT_ID`                | Google OAuth2 client ID                    |
| `GOOGLE_CLIENT_SECRET`            | Google OAuth2 client secret                |

### Active Spring Profiles (local development)

```yaml
spring:
  profiles:
    active: local, authentication, logging, swagger, thymeleaf
```

---

## Quick Reference: Key File Locations

| Concern              | Location                                                          |
|----------------------|-------------------------------------------------------------------|
| Main entry point     | `src/main/java/com/nthn/storevision/StoreVisionApplication.java`  |
| Security config      | `security/config/SecurityConfig.java`                             |
| Global error handler | `advice/`                                                         |
| Base entity (audit)  | `entity/mapped/BaseEntity.java`                                   |
| Generic interfaces   | `service/generic/`                                                |
| Response DTOs        | `dto/response/`                                                   |
| Response factories   | `factory/`                                                        |
| i18n messages        | `src/main/resources/i18n/messages*.properties`                    |
| DB migrations        | `src/main/resources/db/changelog/` (Liquibase)                    |
| API docs (runtime)   | `http://localhost:{port}/store-vision/api/swagger-ui.html`        |
