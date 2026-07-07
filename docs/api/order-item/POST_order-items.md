# POST /api/v1/order-items

## Overview

Creates a new **order item** row from `OrderItemDTO`. Use this endpoint when adding line items programmatically. Input is validated with `@Valid`. Returns created `OrderItemDTO`.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `POST`                                                              |
| **URL**       | `/api/v1/order-items`                                               |
| **Auth**      | Bearer Token (JWT) — required                                       |
| **Privilege** | Not explicitly declared in controller (`@PreAuthorize` not present) |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Request Body

| Field | Type           | Required | Description                                 |
|-------|----------------|----------|---------------------------------------------|
| `dto` | `OrderItemDTO` | Yes      | Order item payload validated with `@Valid`. |

Example:

```json
{"orderId":1,"productId":2,"quantity":3}
```

---

## Response

### `201 Created`

Returns `OrderItemDTO`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |
| `400 Bad Request`       | `INVALID_INPUT` | Request payload validation failed  |

---

## Notes

- Controller mapping: `OrderItemControllerV1#createOrderItem` -> `OrderItemCreationService#create`.
