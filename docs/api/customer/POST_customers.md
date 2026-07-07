# POST /api/v1/customers

## Overview

Creates a new **customer** profile from `CustomerDTO`. Use this endpoint when registering a new customer for sales and loyalty workflows. Input is validated with `@Valid`, including email format and required fields. Returns the created customer data.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `POST`                                                              |
| **URL**       | `/api/v1/customers`                                                 |
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
| `dto` | `CustomerDTO` | Yes      | Customer payload validated with `@Valid`. |

Example:

```json
{
  "name": "Nguyen Van A",
  "email": "customer@example.com",
  "phone": "0901234567"
}
```

---

## Response

### `201 Created`

Returns created `CustomerDTO`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |
| `400 Bad Request`       | `INVALID_INPUT` | Request payload validation failed  |
| `409 Conflict`          | `CONFLICT`      | Email already exists               |

---

## Notes

- Controller mapping: `CustomerControllerV1#createCustomer` -> `CustomerCreationService#create`.
- Endpoint is not annotated with `@RateLimited`.
