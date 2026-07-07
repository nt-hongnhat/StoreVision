# POST /api/v1/inventory/receipts

## Overview

Creates a **stock receipt** entry for inbound stock. Use this endpoint when receiving goods into inventory. Request body is validated with `@Valid`. Returns created `StockReceiptDTO`.

## Endpoint

| Property      | Value                           |
|---------------|---------------------------------|
| **Method**    | `POST`                          |
| **URL**       | `/api/v1/inventory/receipts`    |
| **Auth**      | Bearer Token (JWT) — required   |
| **Privilege** | `hasAnyRole('ADMIN','MANAGER')` |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Request Body

| Field     | Type                  | Required | Description                              |
|-----------|-----------------------|----------|------------------------------------------|
| `request` | `StockReceiptRequest` | Yes      | Receipt payload validated with `@Valid`. |

Example:

```json
{"productId":2,"quantity":50}
```

---

## Response

### `201 Created`

Returns `StockReceiptDTO`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Missing required role/permission   |
| `400 Bad Request`       | `INVALID_INPUT` | Request payload validation failed  |
| `404 Not Found`         | `NOT_FOUND`     | Product does not exist             |

---

## Notes

- Controller mapping: `InventoryControllerV1#createReceipt` -> `InventoryCreationService#receiveStock`.
