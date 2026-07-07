# POST /api/v1/employees

## Overview

Creates a new **employee** account from `EmployeeDTO`. Use this endpoint when onboarding internal users. Body is validated with `@Valid` before create logic runs. Returns created employee data.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `POST`                                                              |
| **URL**       | `/api/v1/employees`                                                 |
| **Auth**      | Bearer Token (JWT) — required                                       |
| **Privilege** | Not explicitly declared in controller (`@PreAuthorize` not present) |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Request Body

| Field | Type          | Required | Description                               |
|-------|---------------|----------|-------------------------------------------|
| `dto` | `EmployeeDTO` | Yes      | Employee payload validated with `@Valid`. |

Example:

```json
{"name":"Tran Thi B","username":"tranthib","email":"employee@example.com","password":"P@ssw0rd123","role":"STAFF"}
```

---

## Response

### `201 Created`

Returns `EmployeeDTO`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |
| `400 Bad Request`       | `INVALID_INPUT` | Request payload validation failed  |

---

## Notes

- Controller mapping: `EmployeeControllerV1#createEmployee` -> `EmployeeCreationService#create`.
