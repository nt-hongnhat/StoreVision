# PUT /api/v1/order-items/{id}

## Overview

Updates an **order item** by ID using `OrderItemDTO`. This endpoint applies full replacement semantics. Request body is validated with `@Valid`. Returns updated item data.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `PUT`                                                               |
| **URL**       | `/api/v1/order-items/{id}`                                          |
| **Auth**      | Bearer Token (JWT) — required                                       |
| **Privilege** | Not explicitly declared in controller (`@PreAuthorize` not present) |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Path / Query Parameters

| Name | In   | Type   | Required | Default | Description            |
|------|------|--------|----------|---------|------------------------|
| `id` | Path | `Long` | Yes      | -       | Order-item identifier. |

### Request Body

| Field | Type           | Required | Description                                         |
|-------|----------------|----------|-----------------------------------------------------|
| `dto` | `OrderItemDTO` | Yes      | Updated order-item payload validated with `@Valid`. |

Example:

```json
{"quantity":4}
```

---

## Response

### `200 OK`

Returns updated `OrderItemDTO`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |
| `400 Bad Request`       | `INVALID_INPUT` | Request payload validation failed  |
| `404 Not Found`         | `NOT_FOUND`     | Order item does not exist          |

---

## Notes

- Controller mapping: `OrderItemControllerV1#updateOrderItem` -> `OrderItemUpdateService#update`.
