# PUT /api/v1/customers/{id}

## Overview

Updates a **customer** profile by ID using `CustomerDTO`. Use this endpoint for full profile edits. Request body is validated with `@Valid` before update execution. Returns updated customer data.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `PUT`                                                               |
| **URL**       | `/api/v1/customers/{id}`                                            |
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
| `id` | Path | `Long` | Yes      | -       | Customer identifier. |

### Request Body

| Field | Type          | Required | Description                                       |
|-------|---------------|----------|---------------------------------------------------|
| `dto` | `CustomerDTO` | Yes      | Updated customer payload validated with `@Valid`. |

Example:

```json
{
  "name": "Nguyen Van A",
  "email": "newmail@example.com"
}
```

---

## Response

### `200 OK`

Returns updated `CustomerDTO`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |
| `400 Bad Request`       | `INVALID_INPUT` | Request payload validation failed  |
| `404 Not Found`         | `NOT_FOUND`     | Customer does not exist            |

---

## Notes

- Controller mapping: `CustomerControllerV1#updateCustomer` -> `CustomerUpdateService#update`.
