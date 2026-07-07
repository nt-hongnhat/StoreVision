# DELETE /api/v1/employees/{id}

## Overview

Soft-deletes an **employee** account by ID. Use this endpoint when deactivating staff access. Successful response has empty body.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `DELETE`                                                            |
| **URL**       | `/api/v1/employees/{id}`                                            |
| **Auth**      | Bearer Token (JWT) — required                                       |
| **Privilege** | Not explicitly declared in controller (`@PreAuthorize` not present) |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Path / Query Parameters

| Name | In   | Type   | Required | Default | Description          |
|------|------|--------|----------|---------|----------------------|
| `id` | Path | `Long` | Yes      | -       | Employee identifier. |

No request body required.

---

## Response

### `204 No Content`

Empty body — employee is marked as deleted.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |
| `404 Not Found`         | `NOT_FOUND`     | Employee does not exist            |

---

## Notes

- Controller mapping: `EmployeeControllerV1#deleteEmployee` -> `EmployeeDeletionService#markAsDeleted`.
