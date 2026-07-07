# POST /api/v1/orders

## Overview

Creates a new **sales order** from `OrderCreationRequest`. Use this endpoint at checkout to place orders with customer, items, and optional voucher. Request body is validated with `@Valid`. Returns created `OrderDTO`.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `POST`                                                              |
| **URL**       | `/api/v1/orders`                                                    |
| **Auth**      | Bearer Token (JWT) — required                                       |
| **Privilege** | Not explicitly declared in controller (`@PreAuthorize` not present) |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Request Body

| Field         | Type                             | Required | Description                      |
|---------------|----------------------------------|----------|----------------------------------|
| `channel`     | `OrderChannel`                   | Yes      | Sales channel (`POS`/`ONLINE`).  |
| `customerId`  | `Long`                           | Yes      | Identifier of ordering customer. |
| `items`       | `List<OrderItemCreationRequest>` | Yes      | Must be non-empty (`@NotEmpty`). |
| `note`        | `String`                         | No       | Optional order note.             |
| `voucherCode` | `String`                         | No       | Optional voucher code.           |

Example:

```json
{"channel":"POS","customerId":1,"items":[{"productId":2,"quantity":1}],"note":"Gift wrap","voucherCode":"SUMMER10"}
```

---

## Response

### `201 Created`

Returns created `OrderDTO`.

### Error Responses

| Status                     | Code                   | Description                        |
|----------------------------|------------------------|------------------------------------|
| `401 Unauthorized`         | `UNAUTHORIZED`         | Missing or invalid JWT token       |
| `403 Forbidden`            | `ACCESS_DENIED`        | Access denied by security policy   |
| `400 Bad Request`          | `INVALID_INPUT`        | Request payload validation failed  |
| `404 Not Found`            | `NOT_FOUND`            | Referenced entity not found        |
| `422 Unprocessable Entity` | `UNPROCESSABLE_ENTITY` | Business constraints not satisfied |

---

## Notes

- Controller mapping: `OrderControllerV1#createOrder` -> `OrderCreationService#createOrder`.
