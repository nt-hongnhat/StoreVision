# GET /api/v1/order-items

## Overview

Retrieves **order items** as a paginated list, optionally filtered by `orderId`. Use this endpoint for line-item browsing and diagnostics. Returns `Page<OrderItemDTO>`.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `GET`                                                               |
| **URL**       | `/api/v1/order-items`                                               |
| **Auth**      | Bearer Token (JWT) — required                                       |
| **Privilege** | Not explicitly declared in controller (`@PreAuthorize` not present) |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Path / Query Parameters

| Name      | In    | Type   | Required | Default                            | Description                  |
|-----------|-------|--------|----------|------------------------------------|------------------------------|
| `page`    | Query | `int`  | No       | `DefaultRequestParam.DEFAULT_PAGE` | Zero-based page index.       |
| `size`    | Query | `int`  | No       | `DefaultRequestParam.DEFAULT_SIZE` | Page size.                   |
| `orderId` | Query | `Long` | No       | -                                  | Optional filter by order ID. |

No request body required.

---

## Response

### `200 OK`

Returns `Page<OrderItemDTO>`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |

---

## Notes

- Controller mapping: `OrderItemControllerV1#getAllOrderItems` -> `OrderItemRetrievalService#findOrderItemsPage`.
