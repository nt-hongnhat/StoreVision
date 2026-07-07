# PATCH /api/v1/orders/{id}

## Overview

Transitions **order status** for a pending order using `OrderStatusUpdateRequest`. This endpoint enforces role-based status transition rules and validates request body with `@Valid`. Use it for operational workflow changes like completion or cancellation. Returns updated `OrderDTO`.

## Endpoint

| Property      | Value                                                                                              |
|---------------|----------------------------------------------------------------------------------------------------|
| **Method**    | `PATCH`                                                                                            |
| **URL**       | `/api/v1/orders/{id}`                                                                              |
| **Auth**      | Bearer Token (JWT) — required                                                                      |
| **Privilege** | `hasAnyRole('ADMIN','MANAGER') or (#request.status == OrderStatus.COMPLETED and hasRole('STAFF'))` |

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

| Field    | Type          | Required | Description                                                             |
|----------|---------------|----------|-------------------------------------------------------------------------|
| `status` | `OrderStatus` | Yes      | Target status (`COMPLETED` or `CANCELLED` path accepted in controller). |

Example:

```json
{"status":"COMPLETED"}
```

---

## Response

### `200 OK`

Returns updated `OrderDTO`.

### Error Responses

| Status                     | Code                   | Description                          |
|----------------------------|------------------------|--------------------------------------|
| `401 Unauthorized`         | `UNAUTHORIZED`         | Missing or invalid JWT token         |
| `403 Forbidden`            | `ACCESS_DENIED`        | Missing required role/permission     |
| `400 Bad Request`          | `INVALID_INPUT`        | Request payload validation failed    |
| `404 Not Found`            | `NOT_FOUND`            | Order does not exist                 |
| `409 Conflict`             | `CONFLICT`             | Invalid status transition            |
| `422 Unprocessable Entity` | `UNPROCESSABLE_ENTITY` | Stock or business constraint not met |

---

## Notes

- Controller mapping: `OrderControllerV1#updateOrderStatus` -> `OrderUpdateService#complete` or `#cancel`.
