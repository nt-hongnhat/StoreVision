# DELETE /api/v1/products/{id}

## Overview

Deletes a **product** by ID with optional hard-delete mode. By default (`hard=false`), it performs soft delete; hard delete is allowed only when explicitly requested and authorized. Use this endpoint for product retirement workflows. Response body is empty.

## Endpoint

| Property      | Value                         |
|---------------|-------------------------------|
| **Method**    | `DELETE`                      |
| **URL**       | `/api/v1/products/{id}`       |
| **Auth**      | Bearer Token (JWT) — required |
| **Privilege** | `hasRole('ADMIN') or (!#hard and hasRole('MANAGER'))` |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Path / Query Parameters

| Name   | In    | Type      | Required | Default | Description                                     |
|--------|-------|-----------|----------|---------|-------------------------------------------------|
| `id`   | Path  | `Long`    | Yes      | -       | Product identifier.                             |
| `hard` | Query | `boolean` | No       | `false` | `true`: permanent delete, `false`: soft delete. |

No request body required.

---

## Response

### `204 No Content`

Empty body — product deleted (soft or hard mode depending on `hard`).

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Missing required role/permission   |
| `404 Not Found`         | `NOT_FOUND`     | Product does not exist             |

---

## Notes

- Controller mapping: `ProductControllerV1#deleteProduct` -> `ProductDeletionService#permanentlyDelete` when `hard=true`, otherwise `markAsDeleted`.
- Endpoint is not annotated with `@RateLimited`.
