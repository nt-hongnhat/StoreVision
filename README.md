# StoreVision

A retail store management backend built with **Java 25** and **Spring Boot 4.0.6**, providing RESTful APIs for managing products, orders, customers, and employees.

## Features

- Product, Customer, Employee, Order & Order Item management (full CRUD)
- Google OAuth2 authentication via Spring Security
- Role-based access control with a custom security filter chain
- Soft-delete and hard-delete support with full audit trails (created/modified/deleted by + timestamp)
- Database schema versioning via Liquibase
- Modular architecture enforced by Spring Modulith
- API documentation via Swagger UI (SpringDoc OpenAPI 3)
- Internationalization (Vietnamese & English)

## Tech Stack

| Layer          | Technology                                          |
|----------------|-----------------------------------------------------|
| Language       | Java 25                                             |
| Framework      | Spring Boot 4.0.6 · Spring Modulith 2.0.6           |
| Data Access    | Spring Data JPA · Spring Data JDBC · Hibernate      |
| Database       | PostgreSQL · HikariCP · Liquibase 4.29.0            |
| Security       | Spring Security · OAuth2 (Google)                   |
| Mapping        | MapStruct                                           |
| Boilerplate    | Lombok                                              |
| Build          | Gradle 9.4.1                                        |
| Infrastructure | Docker · Docker Compose                             |
| API Docs       | SpringDoc OpenAPI 3.0.2 / Swagger UI                |
| Observability  | Spring Boot Actuator                                |

## Prerequisites

- Java 25+
- Docker & Docker Compose
- A Google OAuth2 application (Client ID & Secret)

## Getting Started

**1. Clone the repository**

```bash
git clone <repository-url>
cd StoreVision
```

**2. Start PostgreSQL**

```bash
docker compose up -d
```

**3. Configure environment variables**

| Variable                          | Description                        |
|-----------------------------------|------------------------------------|
| `DATASOURCE_URL`                  | PostgreSQL JDBC URL                |
| `DATASOURCE_MAX_POOL_SIZE`        | HikariCP max pool size             |
| `LIQUIBASE_CHANGE_LOG`            | Path to Liquibase master changelog |
| `LIQUIBASE_DEFAULT_SCHEMA`        | Default schema name                |
| `LIQUIBASE_CHANGE_LOG_TABLE`      | Liquibase tracking table name      |
| `LIQUIBASE_CHANGE_LOG_LOCK_TABLE` | Liquibase lock table name          |
| `GOOGLE_CLIENT_ID`                | Google OAuth2 client ID            |
| `GOOGLE_CLIENT_SECRET`            | Google OAuth2 client secret        |

**4. Run the application**

```bash
./gradlew bootRun
```

The API will be available at `http://localhost:8080/store-vision/api`.

## API Documentation

Swagger UI: `http://localhost:8080/store-vision/api/swagger-ui.html`

OpenAPI spec: `http://localhost:8080/store-vision/api/v3/api-docs`

## Build & Test

```bash
# Run tests
./gradlew test

# Build JAR (skip tests)
./gradlew build -x test

# Full clean build
./gradlew clean build

# Build Docker image
./gradlew bootBuildImage
```

## Project Structure

```mermaid
graph TD
    subgraph src["src/main/java/com/nthn/storevision"]
        ADV["advice/\nApiResponseHandler · exception handlers"]
        CFG["config/\nSwagger, Jackson, CORS, Locale"]
        CTL["controller/\nInterfaces (API contract)"]
        CTLV1["controller/v1/\n@RestController implementations"]
        CTL -->|implemented by| CTLV1
        SVC[service/\nBusiness logic]
        REPO[repository/\nData access]
        ENT[entity/\nJPA models]
        DTO[dto/\nAPI contracts]
        MAP[mapper/\nMapStruct converters]
        FAC["factory/\nApiResponseFactory · UserFactory"]
        EXC[exception/\nCustom error types]
        AUD[component/audit/\nAudit listeners]

        subgraph SEC["security/"]
            SECCFG[config/\nSecurityConfig]
            SECCTX[context/\nUserContext]
            SECFLT[filter/\nUserInfoFilter]
            SECOAUTH[oauth/\nOAuth2SuccessHandler]
            SECUSR[user/\nCustomUserDetailsService · UserPrincipal]
        end
    end
```

## Database Migrations

Schema is managed by **Liquibase 4.29.0**. All SQL is plain PostgreSQL wrapped in an anonymous block:

```sql
DO
$$
    BEGIN
        CREATE TABLE example (...);
    END
$$;
```

### Directory layout

```
src/main/resources/db/
├── changelog/
│   └── db.changelog-master.xml   ← <changeSet> entries with <sqlFile> + <rollback><sqlFile>
├── changeset/
│   └── v1.0.0/
│       ├── 001-create-users-table.sql
│       ├── 002-create-employees-table.sql
│       └── ...
└── rollback/
    └── v1.0.0/
        ├── 001-create-users-table-rollback.sql
        └── ...
```

### Running rollback

```bash
# Roll back the last N changesets
./gradlew liquibaseRollback -PliquibaseCommandValue=<N>

# Roll back by label
./gradlew liquibaseRollback -Plabels=v1.0.0
```

### Adding a new migration

1. Create `db/changeset/v{version}/{NNN}-{description}.sql` — wrap SQL in `DO $$ BEGIN ... END $$;`
2. Create `db/rollback/v{version}/{NNN}-{description}-rollback.sql` — same wrapper, `DROP` statements
3. Add a `<changeSet>` entry in `db.changelog-master.xml` with `labels="v{version}"`

---

## Architecture

### Layered Request Flow

```mermaid
sequenceDiagram
    participant Client
    participant Filter as Security Filter Chain
    participant Controller as Controller (v1/)
    participant Service as Service (per-operation)
    participant Repository as Repository
    participant DB as PostgreSQL
    participant Advice as ApiResponseHandler

    Client->>Filter: HTTP Request
    Filter->>Filter: OAuth2 / token validation
    Filter-->>Client: 401 Unauthorized (if invalid)
    Filter->>Controller: Authenticated request
    Controller->>Controller: @Valid — validate body
    Controller->>Service: delegate
    Service->>Repository: query
    Repository->>DB: SQL via JPA / JDBC
    DB-->>Repository: ResultSet
    Repository-->>Service: Entity
    Service->>Service: Entity → DTO (MapStruct)
    Service-->>Controller: DTO
    Controller-->>Advice: raw DTO (ResponseBodyAdvice intercepts)
    Advice->>Advice: wrap in ApiResponse
    Advice-->>Client: 200 OK + JSON
```

### Domain Module Structure

```mermaid
graph LR
    subgraph Domains["Spring Modulith — Domain Modules"]
        P["product"]
        C["customer"]
        E["employee"]
        O["order"]
        OI["orderitem"]
        U["user"]
    end

    subgraph Pattern["Per-domain Service Pattern (Interface → impl/)"]
        CTL_V1["XxxControllerV1\n(injects specific services directly)"]
        IMPL["XxxServiceImpl\n(aggregator — not controller entry point)"]
        CS["XxxCreationService\n+ XxxCreationServiceImpl"]
        RS["XxxRetrievalService\n+ XxxRetrievalServiceImpl"]
        US["XxxUpdateService\n+ XxxUpdateServiceImpl"]
        DS["XxxDeletionService\n+ XxxDeletionServiceImpl"]
        CTL_V1 --> CS & RS & US & DS
        IMPL -.->|delegates to| CS & RS & US & DS
    end

    subgraph Generic["service/generic — Contracts"]
        CR["Creatable"]
        RD["Readable"]
        UP["Updatable"]
        SD["SoftDeletable\nmarkAsDeleted · restore"]
        HD["HardDeletable\npermanentlyDelete"]
        DL["Deletable\ndelete"]
        SD & HD -->|extends| DL
    end

    CS -->|implements| CR
    RS -->|implements| RD
    US -->|implements| UP
    DS -->|implements| SD & HD
```

### Controller Pattern

```mermaid
graph LR
    subgraph Contracts["controller/ — version-agnostic interfaces"]
        CC["CustomerController"]
        EC["EmployeeController"]
        OC["OrderController"]
        OIC["OrderItemController"]
        PC["ProductController"]
    end

    subgraph V1["controller/v1/ — @RestController implementations"]
        CCV1["CustomerControllerV1\n@RequestMapping(value='/customers', version='1')"]
        ECV1["EmployeeControllerV1"]
        OCV1["OrderControllerV1"]
        OICV1["OrderItemControllerV1"]
        PCV1["ProductControllerV1"]
    end

    CC -->|implemented by| CCV1
    EC -->|implemented by| ECV1
    OC -->|implemented by| OCV1
    OIC -->|implemented by| OICV1
    PC -->|implemented by| PCV1
```

Controllers return **raw DTOs** (never `ResponseEntity<>`). `ApiResponseHandler` (`ResponseBodyAdvice`) intercepts every return value and wraps it in `ApiResponse` automatically. HTTP status codes are declared via `@ResponseStatus`.

### Audit & Softly Delete

```mermaid
classDiagram
    class BaseEntity {
        +UUID id
        +String createdBy
        +Instant createdDate
        +String lastModifiedBy
        +Instant lastModifiedDate
        +String deletedBy
        +Instant deletedDate
    }

    class CustomerEntity
    class EmployeeEntity
    class ProductEntity
    class OrderEntity
    class OrderItemEntity

    BaseEntity <|-- CustomerEntity
    BaseEntity <|-- EmployeeEntity
    BaseEntity <|-- ProductEntity
    BaseEntity <|-- OrderEntity
    BaseEntity <|-- OrderItemEntity

    class CustomAuditingEntityListener {
        +onPrePersist(entity)
        +onPreUpdate(entity)
    }

    class UserAuditorAware {
        +getCurrentAuditor() Optional~String~
    }

    CustomAuditingEntityListener ..> UserAuditorAware : uses
    BaseEntity ..> CustomAuditingEntityListener : @EntityListeners
```

Each domain (Product, Customer, Employee, Order, OrderItem) has dedicated **interface + implementation** pairs per operation, all implementing generic interfaces from `service/generic/`. Interfaces live in `service/domain/`, implementations in `service/domain/impl/`. Spring Modulith enforces module boundaries at compiler and test time.

**Deletion strategy**: `XxxDeletionService` implements both `SoftDeletable` and `HardDeletable`. The default delete flow calls `markAsDeleted(id)` directly from the controller — the `CustomAuditingEntityListener` then autopopulates `deletedBy` / `deletedDate` via `@PreUpdate`. `permanentlyDelete(id)` is reserved for admin / GDPR use cases. Pre-condition checks tied to deletion (e.g., warning when a product has pending orders) are implemented by overriding `markAsDeleted()` in `XxxDeletionServiceImpl`, keeping the aggregator as a pure delegator.

## License

Copyright © 2026 Nguyen Thi Hong Nhat. All rights reserved.
