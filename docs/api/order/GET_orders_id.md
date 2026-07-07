# GET /api/v1/orders/{id}

## Overview

Returns a single **order** by ID. Use this endpoint for order detail pages. Returns `OrderDTO` if found.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `GET`                                                               |
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

No request body required.

---

## Response

### `200 OK`

Returns `OrderDTO`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |
| `404 Not Found`         | `NOT_FOUND`     | Order does not exist               |

---

## Notes

- Controller mapping: `OrderControllerV1#getOrderById` -> `OrderRetrievalService#findOrderDTOById`.
