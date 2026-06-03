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

- **Controllers**: Only handle HTTP concerns (request mapping, input validation via `@Valid`, status code declaration via `@ResponseStatus`). Delegate all logic to services. **Never wrap responses manually** — `ApiResponseHandler` (`advice/`) intercepts every non-`ResponseEntity` return value and wraps it in `ApiResponse` automatically.
- **Services**: One service class per operation per domain (e.g., `OrderCreationService`, `OrderRetrievalService`). Implement generic interfaces from `service/generic/` (`Creatable`, `Readable`, `Updatable`, `Deletable`, `SoftDeletable`, `HardDeletable`).
- **Repositories**: Define interface in `repository/`, implementation in `repository/impl/`, Spring Data JPA repository in `repository/jpa/`.
- **Entities**: Extend `BaseEntity` for all auditable entities. Never expose entities directly through API — always convert via Mapper.

### 3. Service Design Rules

- Use **abstract base classes** in `service/common/` (`AbstractCreatable`, `AbstractReadable`) for shared behavior.
- Use **generic interfaces** in `service/generic/` for contract definition.
- Each concrete `impl/` class (e.g., `CustomerServiceImpl`) aggregates operation-specific services — it is the single entry point per domain for the controller.
- Never call a `Repository` directly from a `Controller` or another `Service`'s domain.

#### Interface-Implementation pattern (mandatory for all service beans)

Every `@Service` bean must follow the Interface-Implementation split:

```
service/domain/
    XxxCreationService.java      ← interface, extends Creatable<E, I>
    XxxRetrievalService.java     ← interface, extends Readable<E, I>  (@NamedInterface if cross-module)
    XxxUpdateService.java        ← interface, extends Updatable<E>
    XxxDeletionService.java      ← interface, extends SoftDeletable<E, I> & HardDeletable<E, I>
    XxxService.java              ← aggregator interface (domain contract)
    impl/
        XxxCreationServiceImpl.java
        XxxRetrievalServiceImpl.java
        XxxUpdateServiceImpl.java
        XxxDeletionServiceImpl.java
        XxxServiceImpl.java      ← aggregator impl, single entry point for the controller
```

No concrete `@Service` class may live directly in `service/domain/` — only interfaces belong there.

#### Deletion strategy

`XxxDeletionService` must implement **both** `SoftDeletable<E, I>` and `HardDeletable<E, I>`:

| Method | When to call | How it works |
|--------|-------------|--------------|
| `markAsDeleted(id)` | Default delete flow (called from aggregator) | Sets `entity.isDeleted = true`, saves → `CustomAuditingEntityListener` fills `deletedBy` / `deletedDate` |
| `restore(id)` | Undo a soft delete | Clears `deletedBy`, `deletedDate`, `isDeleted`; saves |
| `permanentlyDelete(id)` | Admin / compliance (GDPR) only | `repository.deleteById(id)` — physical removal |
| `delete(entity)` | Inline soft delete via object | Sets `entity.isDeleted = true`, saves |

### 4. DTO & Mapping Rules

- Always use **DTOs** (`dto/`) to expose data through the API — never JPA entities directly.
- Use **MapStruct** mappers (`mapper/`) for all entity ↔ DTO conversions. Do not write manual mapping code.
- API responses are wrapped automatically by `ApiResponseHandler` (a `ResponseBodyAdvice` in `advice/`) — controllers must return raw DTOs or `void`, **not** `ResponseEntity<>` or `ApiSuccessResponse<>`.
- `ApiResponseFactory` / `ApiErrorResponseFactory` are used internally by the advice layer — never inject them into controllers.
- Use `@ResponseStatus(HttpStatus.CREATED)` / `@ResponseStatus(HttpStatus.NO_CONTENT)` on controller methods to declare non-200 status codes without constructing `ResponseEntity`.

### 5. Exception Handling

- Define all custom exceptions in the `exception/` package, extending `ApplicationException`.
- **Never** use `System.out.println()` for logging or error output.
- All exceptions must be caught and handled by the global exception handler in `advice/`.
- Use `EntityNotFoundException` for missing entities and `EntityAccessException` for authorization failures.
- Error messages must be externalized to `/i18n/messages` properties files to support i18n.

### 6. Logging

- Use **SLF4J** (`@Slf4j` from Lombok) exclusively for all logging. Never use `System.out.println` or `printStackTrace()`.
- Log at the correct level: `DEBUG` for service/logic flow, `INFO` for significant state changes, `WARN` for recoverable issues, `ERROR` for exceptions.
- Always use **parameterized logging** — never string concatenation:
  ```java
  // correct
  log.debug("Fetching customer id: {}", id);
  // wrong — allocates string even when log level is disabled
  log.debug("Fetching customer id: " + id);
  ```
- Pass the **exception object as the final argument** when logging errors to preserve the full stack trace:
  ```java
  // correct — SLF4J renders the stack trace automatically
  log.error("Failed to process entity: {}", entity, exception);
  // wrong — stack trace is lost
  log.error("Failed to process entity: " + exception.getMessage());
  ```
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

#### Soft delete trigger mechanism

`BaseEntity` has `@Transient boolean isDeleted`. The `CustomAuditingEntityListener` fires on `@PreUpdate` and, when `isDeleted == true && deletedDate.isEmpty()`, automatically sets `deletedBy` and `deletedDate` via reflection. **Never set these two fields directly** — always go through `entity.setDeleted(true)` + `repository.save(entity)`.

To restore: call `entity.setDeletedBy(null)`, `entity.setDeletedDate(null)`, `entity.setDeleted(false)`, then `repository.save(entity)`. The listener will skip the soft-delete block (because `isDeleted == false`) and only update `lastModifiedBy` / `lastModifiedDate`.

### 10. API Documentation

- All controllers and endpoints must include **SpringDoc/OpenAPI annotations** so the Swagger UI at `/swagger-ui.html` stays accurate.
- API base path: `/store-vision/api`
- Swagger config: `application-swagger.yml` — do not duplicate configuration elsewhere.

#### Controller Interface-Implementation pattern

```
controller/
    XxxController.java          ← version-agnostic interface (contract only)
    v1/
        XxxControllerV1.java    ← @RestController, @RequestMapping(value = "/xxx", version = "1")
```

**Controller interface rules (`XxxController.java`):**

| Element | Allowed | Notes |
|---------|---------|-------|
| Method signatures | ✅ | All public endpoint methods |
| `@Valid` on DTO params | ✅ | Validation is part of the domain contract |
| Javadoc + `@see XxxControllerV1` | ✅ | Document the contract, link to impl |
| `@RequestMapping`, `@GetMapping`, `@PostMapping`, etc. | ❌ | HTTP routing belongs in the versioned impl |
| `@RequestBody`, `@PathVariable` | ❌ | Spring MVC binding belongs in impl |
| `@Tag`, `@Operation`, `@ApiResponse`, `@Parameter` | ❌ | OpenAPI docs belong in impl |
| `@RestController`, `@Slf4j`, `@RequiredArgsConstructor` | ❌ | Spring bean annotations belong in impl |

```java
// controller/CustomerController.java — correct
public interface CustomerController {
  CustomerDTO createCustomer(@Valid CustomerDTO dto);   // @Valid ✅, raw DTO return ✅
  void deleteCustomer(Long id);
  List<CustomerDTO> getAllCustomers();
  CustomerDTO getCustomerById(Long id);
  CustomerDTO updateCustomer(Long id, @Valid CustomerDTO dto);
}

// controller/v1/CustomerControllerV1.java — implementation
@ResponseStatus(HttpStatus.CREATED)  // sets 201 without ResponseEntity
@PostMapping
@Override
public CustomerDTO createCustomer(@Valid @RequestBody final CustomerDTO dto) {
  return customerService.create(dto);  // ApiResponseHandler wraps this automatically
}
```

#### Required OpenAPI annotations on the implementation class

| Annotation | Scope | Notes |
|-----------|-------|-------|
| `@Tag(name, description)` | Class | One tag per controller |
| `@Operation(summary, description)` | Method | `summary` = one-liner; `description` = field constraints, business rules |
| `@ApiResponse(responseCode, description)` | Method | Use directly (repeatable) — do **not** wrap in `@ApiResponses` |
| `@Parameter(description)` | `@PathVariable` param | Place on the parameter itself, not in `@Operation` |

#### String constants for annotation values

All string literals used in OpenAPI annotations must be defined as compile-time constants — never inline strings:
- **Cross-controller constants** (response codes, generic descriptions) → `constant/ApiDocConstants`
- **Within-controller constants** (entity-specific ID descriptions) → `private static final String` in the class

```java
// correct
@ApiResponse(responseCode = ApiDocConstants.HTTP_404, description = ApiDocConstants.DESC_NOT_FOUND)
@Parameter(description = ID_DESC) @PathVariable final Long id   // ID_DESC = private static final

// wrong — duplicate string literals flagged by linter
@ApiResponse(responseCode = "404", description = "Not found")
```

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
