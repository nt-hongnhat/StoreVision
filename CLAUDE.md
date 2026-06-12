# Project Instructions

This file contains custom instructions for Claude Code CLI to help development
on the **StoreVision** project. Follow all rules strictly when generating,
modifying, or reviewing code.

---

## Core Stack

| Layer          | Technology                                                                     |
|----------------|--------------------------------------------------------------------------------|
| Language       | Java 25 (via Gradle toolchain: `JavaLanguageVersion.of(25)`)                   |
| Framework      | Spring Boot 4.0.6, Spring Modulith 2.0.6                                       |
| Data Access    | Spring Data JPA, Spring Data JDBC, Hibernate (PostgreSQL dialect)              |
| Database       | PostgreSQL (HikariCP connection pool, Liquibase 4.29.0 migrations)             |
| Security       | Spring Security + OAuth2 (Google provider), custom filter chain                |
| API Docs       | SpringDoc OpenAPI 3.0.2 / Swagger UI                                           |
| Templating     | Thymeleaf 3.x (with Spring Security extras)                                    |
| Batch          | Spring Batch                                                                   |
| Mapping        | MapStruct (Entity ↔ DTO conversions)                                           |
| Boilerplate    | Lombok (constructor properties + chaining accessors enabled via lombok.config) |
| Build Tool     | Gradle 9.4.1 (wrapper: `./gradlew`)                                            |
| Infrastructure | Docker / Docker Compose (PostgreSQL service via `compose.yaml`)                |
| Observability  | Spring Boot Actuator                                                           |
| i18n           | Spring MessageSource (`/i18n/messages` basenames, Vietnamese + English)        |

---

## Style & Engineering Principles

### 1. Package & Naming Conventions

- **Root package**: `com.nthn.storevision`
- **Sub-packages**: all lowercase, singular nouns (e.g., `controller`,
  `service`, `repository`, `entity`, `dto`, `mapper`, `config`, `exception`,
  `factory`, `util`)
- **Classes**: `PascalCase` (e.g., `OrderCreationService`, `CustomerMapper`)
- **Methods & variables**: `camelCase`
- **Constants**: `UPPER_SNAKE_CASE`
- **API versioning**: place versioned controllers under `controller/v1/` and
  name them `<Domain>ControllerV1`
- **Config/property keys**: `snake_case` (Jackson `SNAKE_CASE` naming strategy
  is project-wide)

### 2. Layered Architecture

Strictly follow the four-layer separation. Never skip a layer or mix concerns:

```
Controller (v1/)
    └── Service (operation-specific: Creation / Retrieval / Update / Deletion)
            └── Repository (interface + impl/ + jpa/)
                    └── Entity (entity/ + entity/mapped/)
```

- **Controllers**: Only handle HTTP concerns (request mapping, input validation
  via `@Valid`, status code declaration via `@ResponseStatus`). Delegate all
  logic to services. **Never wrap responses manually** — `ApiResponseHandler` (
  `advice/`) intercepts every non-`ResponseEntity` return value and wraps it in
  `ApiResponse` automatically.
- **Services**: One service class per operation per domain (e.g.,
  `OrderCreationService`, `OrderRetrievalService`). Implement generic interfaces
  from `service/generic/` (`Creatable`, `Readable`, `Updatable`, `Deletable`,
  `SoftDeletable`, `HardDeletable`).
- **Repositories**: Define interface in `repository/`, implementation in
  `repository/impl/`, Spring Data JPA repository in `repository/jpa/`.
  Repository methods **must return Entity types** (`Entity`, `Optional<Entity>`,
  `List<Entity>`, `Page<Entity>`) — never DTOs, never primitives constructed
  from projection queries unless explicitly using `@Query` projections for
  read-only optimization.
- **Entities**: Extend `BaseEntity` for all auditable entities. Never expose
  entities directly through API — always convert via Mapper. Entity-to-DTO
  conversion is the **exclusive responsibility of the Service layer**.

### 3. Service Design Rules

- Use **abstract base classes** in `service/common/` (`AbstractCreatable`,
  `AbstractReadable`) for shared behavior.
- Use **generic interfaces** in `service/generic/` for contract definition.
- **Controllers inject specific operation services directly** —
  `*CreationService`, `*RetrievalService`, `*UpdateService`, `*DeletionService`.
  Never inject the aggregator `*Service` (e.g., `ProductService`) into a
  controller.
- The aggregator `*ServiceImpl` exists for orchestration (e.g., cross-domain
  delegations) but is **not** the controller's entry point.
- Never call a `Repository` directly from a `Controller` or another `Service`'s
  domain.

#### Interface-Implementation pattern (mandatory for all service beans)

Every `@Service` bean must follow the Interface-Implementation split:

```
service/domain/
    XxxCreationService.java      ← interface, extends Creatable<E, I>
    XxxRetrievalService.java     ← interface, extends Readable<E, I>  (@NamedInterface if cross-module)
    XxxUpdateService.java        ← interface, extends Updatable<E>          (omit if domain has no update)
    XxxDeletionService.java      ← interface, extends SoftDeletable<E, I> & HardDeletable<E, I>  (omit if no delete)
    XxxService.java              ← aggregator interface — OPTIONAL; create only when cross-operation
    impl/                           orchestration is needed (e.g. ProductService delegates to all 4 ops)
        XxxCreationServiceImpl.java
        XxxRetrievalServiceImpl.java
        XxxUpdateServiceImpl.java   (omit if no update)
        XxxDeletionServiceImpl.java (omit if no delete)
        XxxServiceImpl.java         (omit if aggregator is not needed — see rule below)
```

**Aggregator rule**: only create `XxxService.java` + `XxxServiceImpl.java` when
the domain genuinely needs cross-operation orchestration. Domains that expose
only a subset of operations (e.g., Inventory with creation + retrieval only)
must **not** have an aggregator — the controller injects the two specific
services directly. Creating an aggregator for a domain that does not need it
adds indirection without value.

No concrete `@Service` class may live directly in `service/domain/` — only
interfaces belong there.

#### Deletion strategy

`XxxDeletionService` must implement **both** `SoftDeletable<E, I>` and
`HardDeletable<E, I>`:

| Method                  | When to call                                          | How it works                                                                                             |
|-------------------------|-------------------------------------------------------|----------------------------------------------------------------------------------------------------------|
| `markAsDeleted(id)`     | Default delete flow (called directly from controller) | Sets `entity.isDeleted = true`, saves → `CustomAuditingEntityListener` fills `deletedBy` / `deletedDate` |
| `restore(id)`           | Undo a soft delete                                    | Clears `deletedBy`, `deletedDate`, `isDeleted`; saves                                                    |
| `permanentlyDelete(id)` | Admin / compliance (GDPR) only                        | `repository.deleteById(id)` — physical removal                                                           |
| `delete(entity)`        | Inline soft delete via object                         | Sets `entity.isDeleted = true`, saves                                                                    |

**Business rules in specific service impls**: Pre-condition checks and warnings
tied to an operation (e.g., BR-P04: warn when soft-deleting a product that has
pending orders) must be implemented by **overriding the relevant method
in `*DeletionServiceImpl`** (or the appropriate `*OperationServiceImpl`), not in
the aggregator. The aggregator must remain a pure delegator.

```java
// correct — BR-P04 lives in ProductDeletionServiceImpl.markAsDeleted()
@Override
@Transactional
public void markAsDeleted(final Long id) {
  if (orderRetrievalService.existsPendingOrderForProduct(id)) {
    log.warn("BR-P04: product id {} has PENDING orders — soft-deleting anyway;...", id);
  }
  super.markAsDeleted(id);
}

// wrong — business rule buried in aggregator, invisible to direct callers
@Override
public void delete(final Long id) {
  if (orderRetrievalService.existsPendingOrderForProduct(id)) { ...}  // ❌
  deletionService.markAsDeleted(id);
}
```

### 4. DTO & Mapping Rules

- Always use **DTOs** (`dto/`) to expose data through the API — never JPA
  entities directly.
- Use **MapStruct** mappers (`mapper/`) for all entity ↔ DTO conversions. Do not
  write manual mapping code.
- API responses are wrapped automatically by `ApiResponseHandler` (a
  `ResponseBodyAdvice` in `advice/`) — controllers must return raw DTOs or
  `void`, **not** `ResponseEntity<>` or `ApiSuccessResponse<>`.
- `ApiResponseFactory` / `ApiErrorResponseFactory` are used internally by the
  advice layer — never inject them into controllers.
- Use `@ResponseStatus(HttpStatus.CREATED)` /
  `@ResponseStatus(HttpStatus.NO_CONTENT)` on controller methods to declare
  non-200 status codes without constructing `ResponseEntity`.

#### Repository → Service → Controller data flow (mandatory)

The conversion boundary between Entity and DTO is **always the Service layer**:

| Layer        | Returns                                                      | Never returns                                                       |
|--------------|--------------------------------------------------------------|---------------------------------------------------------------------|
| `Repository` | `Entity`, `Optional<Entity>`, `List<Entity>`, `Page<Entity>` | DTO, request/response objects                                       |
| `Service`    | `DTO`, `Optional<DTO>`, `List<DTO>`, `Page<DTO>`             | raw `Entity` (unless internal private helper within the same class) |
| `Controller` | raw `DTO` or `void`                                          | `Entity`, `ResponseEntity<>`                                        |

```java
// correct — Repository returns Entity
Optional<Customer> findById(Long id);   // CustomerRepository

// correct — Service converts Entity → DTO before returning
public CustomerDTO getById(Long id) {
  Customer entity = customerRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException(...));
  return customerMapper.toDTO(entity);  // conversion happens here, in the service
}

// wrong — never return DTO from Repository
Optional<CustomerDTO> findById(Long id);  // ❌

// wrong — never return Entity from Service public API
public Customer getById(Long id) { ...}  // ❌ (exposes persistence detail to controller)
```

- Internal helper methods within a service implementation may pass `Entity`
  objects between private methods — the rule applies to **public method
  signatures** on service interfaces.
- Cross-domain service calls (e.g., `OrderCreationService` calling
  `CustomerRetrievalService`) must use the **DTO** returned by the other
  domain's service — never reach into another domain's repository directly.

#### Mapper architecture (`mapper/`)

The mapper layer has two levels:

**1. `EntityMapper<T, I, E>` — generic base interface**

All domain mappers extend this interface. It defines three operations every
mapper must support:

| Method                                         | Direction             | Notes                                                                                                                                            |
|------------------------------------------------|-----------------------|--------------------------------------------------------------------------------------------------------------------------------------------------|
| `toDTO(E entity)`                              | Entity → DTO          | Used by service layer after persistence or retrieval                                                                                             |
| `toEntity(T dto)`                              | DTO → Entity          | Audit fields (`id`, `createdBy`, `createdDate`, `lastModifiedBy`, `lastModifiedDate`, `deletedBy`, `deletedDate`, `deleted`) are `ignore = true` |
| `updateEntity(T dto, @MappingTarget E entity)` | DTO → existing Entity | `id` is always `ignore = true`; other ignored fields are domain-specific                                                                         |

Abstract service classes (e.g., `AbstractCreatable`) depend on
`EntityMapper<T, ?, E>` directly — never on concrete mapper types.

**2. Domain mappers — thin `EntityMapper` extensions**

Each domain mapper extends `EntityMapper` and adds only domain-specific
`@Mapping` overrides. All domain mappers must declare
`config = BaseEntityMapperConfig.class`:

```java
// correct — minimal domain mapper
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, config = BaseEntityMapperConfig.class)
public interface CustomerMapper extends EntityMapper<CustomerDTO, Long, CustomerEntity> {
}

// correct — with domain-specific field mapping overrides
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, config = BaseEntityMapperConfig.class)
public interface OrderMapper extends EntityMapper<OrderDTO, Long, OrderEntity> {
  @Mapping(source = "customer.id", target = "customerId")
  OrderDTO toDTO(OrderEntity order);

  @Mapping(target = "customer", expression = "java(mapCustomerIdToCustomerEntity(dto.getCustomerId()))")
  OrderEntity toEntity(OrderDTO dto);
}
```

**3. `BaseEntityMapperConfig` — shared `@MapperConfig`**

`BaseEntityMapperConfig` (`mapper/`) is the single shared MapStruct config.
Every domain mapper must reference it via
`config = BaseEntityMapperConfig.class`. It provides:

- `unmappedTargetPolicy = IGNORE` — suppresses audit-field warnings for all
  DTO-based mappers
- A `toEntity(User)` prototype mapping — handles the `UserEntity ← User` edge
  case where `User` extends `CustomAuditable` with `Optional<User>` getters that
  MapStruct cannot auto-unwrap

Do **not** repeat `unmappedTargetPolicy = IGNORE` or the audit-field
`@Mapping(ignore = true)` block on individual domain mappers —
`BaseEntityMapperConfig` and `EntityMapper.toEntity()` already cover them.

### 5. Exception Handling

- Define all custom exceptions in the `exception/` package, extending
  `ApplicationException`.
- **Never** use `System.out.println()` for logging or error output.
- All exceptions must be caught and handled by the global exception handler in
  `advice/`.
- Use `EntityNotFoundException` for missing entities and `EntityAccessException`
  for authorization failures.
- Error messages must be externalized to `/i18n/messages` properties files to
  support i18n.

### 6. i18n Message Key Convention

All message keys in `/i18n/messages*.properties` must follow the
`[PREFIX][3-DIGIT-NUMBER]` format — never dot-notation (`product.not.found` is *
*wrong**).

#### Prefix registry

| Prefix | Domain                    | Example  |
|--------|---------------------------|----------|
| `USM`  | User (`user`)             | `USM001` |
| `PRM`  | Product (`product`)       | `PRM001` |
| `INM`  | Inventory (`inventory`)   | `INM001` |
| `AUM`  | Audit (`component/audit`) | `AUM001` |
| `CSM`  | Customer (`customer`)     | `CSM001` |
| `EMM`  | Employee (`employee`)     | `EMM001` |
| `ORM`  | Order (`order`)           | `ORM001` |
| `OIM`  | Order item (`orderitem`)  | `OIM001` |
| `VCM`  | Voucher (`voucher`)       | `VCM001` |

> When adding a new domain, register its prefix in this table before writing any
> message keys.

#### Rules

- **Constants file**: every domain must have a `{Domain}MessageCodes` enum in
  `constant/` whose `static final String` fields reference the code string (e.g.
  `public static final String NOT_FOUND = "PRM001"`). Never inline the string in
  business code.
- **Bean Validation messages**: validation constraint messages in DTOs must also
  use the coded format — `@NotBlank(message = "{PRM003}")`, not
  `@NotBlank(message = "Product name is required")`.
- **Numbering**: allocate codes sequentially per prefix. The first code is
  `001`. Leave no gaps.
- **All locales**: every code added to `messages.properties` (EN base) must also
  be added to `messages-vi.properties` (VI).
- **Parameter placeholders**: use bare `{0}`, `{1}` for dynamic values — never
  surround with escaped single quotes (`''{0}''`). MessageFormat's `''{n}''`
  pattern emits literal quotes around the value, which is almost never desired.
  Use `'{n}'` only when you need to display a literal `{n}` string (no
  substitution).

#### Current code allocation

| Code     | Meaning                                               |
|----------|-------------------------------------------------------|
| `USM001` | User not found                                        |
| `PRM001` | Product not found                                     |
| `PRM002` | Product name + category already exists (BR-P01)       |
| `PRM003` | Product name required                                 |
| `PRM004` | Product price required                                |
| `PRM005` | Product price must be > 0                             |
| `PRM006` | Stock quantity required                               |
| `PRM007` | Stock quantity must be >= 0                           |
| `INM001` | Product ID required (StockReceiptRequest)             |
| `INM002` | Quantity required (StockReceiptRequest)               |
| `INM003` | Quantity must be >= 1 (BR-I02)                        |
| `INM004` | Unit cost must be >= 0                                |
| `INM005` | No authenticated user found for performed_by (BR-I01) |
| `AUM001` | Failed to set annotated audit field                   |
| `AUM002` | No setter found for audit field                       |
| `AUM003` | Failed to invoke setter for audit field               |
| `CSM001` | Customer not found                                    |
| `EMM001` | Employee not found                                    |
| `ORM001` | Order not found                                       |
| `ORM002` | Customer ID required (OrderCreationRequest)           |
| `ORM003` | Order must contain at least one item (BR-O05)         |
| `ORM004` | Insufficient stock for order item (BR-O01)            |
| `ORM005` | Order channel required (OrderCreationRequest)         |
| `OIM001` | Order item not found                                  |
| `OIM002` | Product ID required (OrderItemCreationRequest)        |
| `OIM003` | Quantity required (OrderItemCreationRequest)          |
| `OIM004` | Quantity must be >= 1                                 |
| `VCM001` | Voucher not found                                     |
| `VCM002` | Voucher expired (BR-V01)                              |
| `VCM003` | Voucher inactive (BR-V02)                             |
| `VCM004` | Voucher usage limit reached (BR-V03)                  |
| `VCM005` | Order amount below voucher minimum (BR-V04)           |

### 7. Logging

- Use **SLF4J** (`@Slf4j` from Lombok) exclusively for all logging. Never use
  `System.out.println` or `printStackTrace()`.
- Log at the correct level: `DEBUG` for service/logic flow, `INFO` for
  significant state changes, `WARN` for recoverable issues, `ERROR` for
  exceptions.
- Always use **parameterized logging** — never string concatenation:
  ```java
  // correct
  log.debug("Fetching customer id: {}", id);
  // wrong — allocates string even when log level is disabled
  log.debug("Fetching customer id: " + id);
  ```
- Pass the **exception object as the final argument** when logging errors to
  preserve the full stack trace:
  ```java
  // correct — SLF4J renders the stack trace automatically
  log.error("Failed to process entity: {}", entity, exception);
  // wrong — stack trace is lost
  log.error("Failed to process entity: " + exception.getMessage());
  ```
- Refer to `application-logging.yml`: project package `com.nthn` logs at
  `DEBUG`, root at `INFO`.

### 8. Security Rules

- Security configuration lives exclusively in `SecurityConfig.java` under
  `security/config/`.
- The `security/` package is structured into focused sub-packages: `config/` (
  SecurityConfig), `context/` (UserContext), `filter/` (UserInfoFilter),
  `oauth/` (OAuth2SuccessHandler), `user/` (CustomUserDetailsService,
  UserPrincipal).
- OAuth2 Google authentication is configured in
  `application-authentication.yml` — do not hardcode credentials.
- Never expose internal user/session data outside the `security/` boundary
  without going through `UserContext` in `security/context/`.
- Keep security-related components (JWT provider, custom filters, authentication
  configuration) isolated inside the `security/` sub-package hierarchy — never
  scatter them into generic packages such as `component/` or the top-level
  `config/`.

### 9. Database & Migrations

- **All schema changes** must be applied via **Liquibase** changesets — never
  modify the database schema directly or use
  `spring.jpa.hibernate.ddl-auto=update/create`.
- Connection pool settings (`max-pool-size`, `connection-timeout`) are
  externalized via environment variables (`${DATASOURCE_MAX_POOL_SIZE}`,
  `${DATASOURCE_URL}`).
- Use **soft delete** (via `deleted_by` / `deleted_date` audit fields on
  `BaseEntity`) unless a hard delete is explicitly required by business logic.

#### Liquibase changeset SQL convention

- Changeset SQL files live under `src/main/resources/db/changeset/v{version}/`
  and rollback files under `src/main/resources/db/rollback/v{version}/`.
- **All SQL inside changeset and rollback files must be wrapped in a PostgreSQL
  anonymous block:**
  ```sql
  DO
  $$
      BEGIN
          -- DDL here (CREATE TABLE, CREATE INDEX, DROP TABLE, DROP INDEX…)
      END
  $$;
  ```
- Master changelog (`db/changelog/db.changelog-master.xml`) uses `<changeSet>`
  with `<sqlFile>` and `<rollback><sqlFile>` — never inline `<sql>` blocks,
  never `<include>`.
- Each `<changeSet>` must carry a `labels="v{version}"` attribute matching its
  folder version.
- File naming: `{NNN}-{action}-{table}-table.sql` /
  `{NNN}-{action}-{table}-table-rollback.sql`.

### 10. Auditing

- All entities must extend `BaseEntity` which provides `createdBy`,
  `createdDate`, `lastModifiedBy`, `lastModifiedDate`, `deletedBy`,
  `deletedDate`.
- The `CustomAuditingEntityListener` and `UserAuditorAware` in
  `component/audit/` handle automatic population — do not set these fields
  manually.
- Use `@DeletedBy` / `@DeletedDate` annotations (from `annotation/`) for soft
  delete fields.

#### Soft delete trigger mechanism

`BaseEntity` has `@Transient boolean isDeleted`. The
`CustomAuditingEntityListener` fires on `@PreUpdate` and, when
`isDeleted == true && deletedDate.isEmpty()`, automatically sets `deletedBy` and
`deletedDate` via reflection. **Never set these two fields directly** — always
go through `entity.setDeleted(true)` + `repository.save(entity)`.

To restore: call `entity.setDeletedBy(null)`, `entity.setDeletedDate(null)`,
`entity.setDeleted(false)`, then `repository.save(entity)`. The listener will
skip the soft-delete block (because `isDeleted == false`) and only update
`lastModifiedBy` / `lastModifiedDate`.

### 11. API Documentation

- All controllers and endpoints must include **SpringDoc/OpenAPI annotations**
  so the Swagger UI at `/swagger-ui.html` stays accurate.
- API base path: `/` (at the root)
- Swagger config: `application-swagger.yml` — do not duplicate configuration
  elsewhere.

#### Controller Interface-Implementation pattern

```
controller/
    XxxController.java          ← version-agnostic interface (contract only)
    v1/
        XxxControllerV1.java    ← @RestController, @RequestMapping(value = "/xxx", version = "1")
```

**Controller interface rules (`XxxController.java`):**

| Element                                                 | Allowed | Notes                                      |
|---------------------------------------------------------|---------|--------------------------------------------|
| Method signatures                                       | ✅       | All public endpoint methods                |
| `@Valid` on DTO params                                  | ✅       | Validation is part of the domain contract  |
| Javadoc + `@see XxxControllerV1`                        | ✅       | Document the contract, link to impl        |
| `@RequestMapping`, `@GetMapping`, `@PostMapping`, etc.  | ❌       | HTTP routing belongs in the versioned impl |
| `@RequestBody`, `@PathVariable`                         | ❌       | Spring MVC binding belongs in impl         |
| `@Tag`, `@Operation`, `@ApiResponse`, `@Parameter`      | ❌       | OpenAPI docs belong in impl                |
| `@RestController`, `@Slf4j`, `@RequiredArgsConstructor` | ❌       | Spring bean annotations belong in impl     |

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

| Annotation                                | Scope                 | Notes                                                                    |
|-------------------------------------------|-----------------------|--------------------------------------------------------------------------|
| `@Tag(name, description)`                 | Class                 | One tag per controller                                                   |
| `@Operation(summary, description)`        | Method                | `summary` = one-liner; `description` = field constraints, business rules |
| `@ApiResponse(responseCode, description)` | Method                | Use directly (repeatable) — do **not** wrap in `@ApiResponses`           |
| `@Parameter(description)`                 | `@PathVariable` param | Place on the parameter itself, not in `@Operation`                       |

#### String constants for annotation values

All string literals used in OpenAPI annotations must be defined as compile-time
constants — never inline strings:

- **HTTP response codes and generic descriptions** (e.g., `"200"`,
  `"Resource not found"`) → `constant/api/ResponseCode` and
  `constant/api/ResponseDescription`
- **Domain-specific ID descriptions** (e.g., `"Product ID"`, `"Order ID"`) →
  `static final String` in `constant/api/ResponseDescription` — **not** as
  private constants in the controller class
- **Operation-specific descriptions unique to one controller** (e.g., a conflict
  message for a specific business rule) → `private static final String` in that
  controller class

```java
// correct — domain ID uses ResponseDescription constant
@Parameter(description = ResponseDescription.PRODUCT_ID)
@PathVariable
final Long id

// correct — controller-unique string stays private
private static final String CONFLICT_DESC = "Product name already exists in the same category";
@ApiResponse(responseCode = ResponseCode.CONFLICT, description = CONFLICT_DESC)

// wrong — inline strings flagged by linter as duplicates
@Parameter(description = "Product ID")
@PathVariable
final Long id   // ❌
@ApiResponse(responseCode = "409", description = "Conflict")          // ❌
```

**Registering a new domain ID constant in `ResponseDescription`**: when adding a
new domain (e.g., `Supplier`), add
`public static final String SUPPLIER_ID = "Supplier ID"` to
`ResponseDescription` before writing any controller code.

### 12. Modulith Boundaries

- This project uses **Spring Modulith**. Use `@NamedInterface` to declare
  explicit module boundaries.
- Do not create cross-module dependencies that bypass the declared interfaces.
- Run `ApplicationModularityTests` to verify module structure before merging
  changes.

### 13. Testing Standards

- **Mandatory**: write unit tests for all service-layer logic using **JUnit 5
  ** (Jupiter) and **Spring Boot Test**.
- Security-related tests must use **Spring Security Test** (`@WithMockUser`,
  `SecurityMockMvcRequestPostProcessors`).
- Batch jobs must be tested with **Spring Batch Test**.
- Minimum code coverage target: **80%** for service layer classes.
- Test classes mirror the source package structure under
  `src/test/java/com/nthn/storevision/` — including the `impl/` sub-package. A
  test for `service/product/impl/ProductServiceImpl` lives at
  `test/java/.../service/product/impl/ProductServiceImplTest`.
- Test class names use the `<ClassName>Test` suffix matching the implementation
  class, not the interface (e.g., `ProductServiceImplTest`, not
  `ProductServiceTest`).
- Use Spring's `@DataJpaTest` (or `@JdbcTest`) for repository layer tests — do
  not spin up the full application context for persistence tests.
- Do **not** use mocked databases when integration behavior is under test;
  prefer the real PostgreSQL via `compose.yaml`.

### 14. Code Quality Rules

- **No raw `System.out` or `printStackTrace()`** anywhere in the codebase.
- **No magic strings or numbers** — externalize to constants in `constant/` or
  message properties in `i18n/`.
- Use constructor injection (Lombok `@RequiredArgsConstructor`) — never field
  injection (`@Autowired` on fields).
- Prefer `Optional<T>` over `null` returns in service and repository methods.
- Validate all incoming request bodies with Bean Validation (`@Valid`,
  `@NotNull`, `@NotBlank`, etc.) at the controller boundary.
- Factory classes in `factory/` must be used for constructing response objects —
  do not construct `ApiResponse` subclasses inline.

### 15. Javadoc Requirements

Every **public** method on any `interface` or `public class` must have a Javadoc
comment. This is enforced by the project's checkstyle/linter configuration.

#### Mandatory elements

| Element                    | Required tags                                                                                                           |
|----------------------------|-------------------------------------------------------------------------------------------------------------------------|
| Interface method           | `@param` for every parameter, `@return` unless `void`                                                                   |
| `model/` interface methods | Same as above; setters must also document valid/invalid values (e.g., `null` policy) and return `{@code Self}` instance |
| Class-level Javadoc        | Required on every `public` class, interface, and enum                                                                   |
| Enum constants             | Required on every constant if the enum is part of the public API                                                        |

#### Style rules

- The first sentence is the summary — keep it under 120 characters.
- Use `{@link ClassName}` for type references; `{@code value}` for literal
  values.
- Do **not** write trivial "Gets the X" / "Sets the X" Javadocs that add no
  information — but do include `@param` and `@return` even when the summary is
  minimal.
- Describe the **null policy** for every parameter and return value: either "
  must not be {@code null}" or "may be {@code null}; returns/accepts null if …".

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

Do **not** read, index, or suggest changes inside the following paths — they are
generated or transient:

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

The following **must** be provided at runtime (never hardcode in a source):

| Variable                          | Purpose                                 |
|-----------------------------------|-----------------------------------------|
| `DATASOURCE_URL`                  | PostgreSQL JDBC connection URL          |
| `DATASOURCE_MAX_POOL_SIZE`        | HikariCP max pool size                  |
| `LIQUIBASE_CHANGE_LOG`            | Path to the Liquibase master changelog  |
| `LIQUIBASE_DEFAULT_SCHEMA`        | Default schema for Liquibase operations |
| `LIQUIBASE_CHANGE_LOG_TABLE`      | Liquibase tracking table name           |
| `LIQUIBASE_CHANGE_LOG_LOCK_TABLE` | Liquibase lock table name               |
| `GOOGLE_CLIENT_ID`                | Google OAuth2 client ID                 |
| `GOOGLE_CLIENT_SECRET`            | Google OAuth2 client secret             |

### Active Spring Profiles (local development)

```yaml
spring:
  profiles:
    active: local, authentication, logging, swagger, thymeleaf
```

---

## Quick Reference: Key File Locations

| Concern              | Location                                                         |
|----------------------|------------------------------------------------------------------|
| Main entry point     | `src/main/java/com/nthn/storevision/StoreVisionApplication.java` |
| Security config      | `security/config/SecurityConfig.java`                            |
| Global error handler | `advice/`                                                        |
| Base entity (audit)  | `entity/mapped/BaseEntity.java`                                  |
| Generic interfaces   | `service/generic/`                                               |
| Response DTOs        | `dto/response/`                                                  |
| Response factories   | `factory/`                                                       |
| i18n messages        | `src/main/resources/i18n/messages*.properties`                   |
| DB migrations        | `src/main/resources/db/changelog/` (Liquibase)                   |
| API docs (runtime)   | `http://localhost:{port}/swagger-ui.html`                        |
