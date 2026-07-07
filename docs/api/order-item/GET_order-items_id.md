# GET /api/v1/order-items/{id}

## Overview

Gets one **order item** by ID. Use this endpoint for detail view of a line item. Returns `OrderItemDTO`.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `GET`                                                               |
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

No request body required.

---

## Response

### `200 OK`

Returns `OrderItemDTO`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |
| `404 Not Found`         | `NOT_FOUND`     | Order item does not exist          |

---

## Notes

- Controller mapping: `OrderItemControllerV1#getOrderItemById` -> `OrderItemRetrievalService#findOrderItemDTOById`.
