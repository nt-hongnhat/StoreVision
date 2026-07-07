# GET /api/v1/customers/{id}/orders

## Overview

Retrieves the **order history** of a customer. Use this endpoint for customer-centric order timeline screens. It loads orders associated with one customer ID. Returns `List<OrderDTO>`.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `GET`                                                               |
| **URL**       | `/api/v1/customers/{id}/orders`                                     |
| **Auth**      | Bearer Token (JWT) — required                                       |
| **Privilege** | Not explicitly declared in controller (`@PreAuthorize` not present) |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Path / Query Parameters

| Name | In | Type | Required | Default | Description |
|---|---|---|---|---|---|
| `id` | Path | `Long` | Yes | - | Customer identifier. |

No request body required.

---

## Response

### `200 OK`

Returns `List<OrderDTO>`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |
| `404 Not Found`         | `NOT_FOUND`     | Customer does not exist            |

---

## Notes

- Controller mapping: `CustomerControllerV1#getCustomerOrders` -> `CustomerRetrievalService#findOrderHistory`.
