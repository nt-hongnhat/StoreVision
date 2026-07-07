# PUT /api/v1/employees/{id}

## Overview

Updates an **employee** by ID using `EmployeeDTO`. Use this endpoint for full account updates. Input is validated with `@Valid`. Returns updated `EmployeeDTO`.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `PUT`                                                               |
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

### Request Body

| Field | Type          | Required | Description                                       |
|-------|---------------|----------|---------------------------------------------------|
| `dto` | `EmployeeDTO` | Yes      | Updated employee payload validated with `@Valid`. |

Example:

```json
{"name":"Tran Thi B","role":"MANAGER"}
```

---

## Response

### `200 OK`

Returns updated `EmployeeDTO`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |
| `400 Bad Request`       | `INVALID_INPUT` | Request payload validation failed  |
| `404 Not Found`         | `NOT_FOUND`     | Employee does not exist            |

---

## Notes

- Controller mapping: `EmployeeControllerV1#updateEmployee` -> `EmployeeUpdateService#update`.
