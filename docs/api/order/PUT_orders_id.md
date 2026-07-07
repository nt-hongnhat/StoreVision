# PUT /api/v1/orders/{id}

## Overview

Updates an **order** by ID using `OrderDTO`. This endpoint applies full update semantics and validates request body with `@Valid`. Returns updated order state.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `PUT`                                                               |
| **URL**       | `/api/v1/orders/{id}`                                               |
| **Auth**      | Bearer Token (JWT) — required                                       |
| **Privilege** | Not explicitly declared in controller (`@PreAuthorize` not present) |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Path / Query Parameters

| Name | In   | Type   | Required | Default | Description       |
|------|------|--------|----------|---------|-------------------|
| `id` | Path | `Long` | Yes      | -       | Order identifier. |

### Request Body

| Field | Type       | Required | Description                                    |
|-------|------------|----------|------------------------------------------------|
| `dto` | `OrderDTO` | Yes      | Updated order payload validated with `@Valid`. |

Example:

```json
{"note":"Updated note"}
```

---

## Response

### `200 OK`

Returns updated `OrderDTO`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |
| `400 Bad Request`       | `INVALID_INPUT` | Request payload validation failed  |
| `404 Not Found`         | `NOT_FOUND`     | Order does not exist               |

---

## Notes

- Controller mapping: `OrderControllerV1#updateOrder` -> `OrderUpdateService#update`.
