# POST /api/v1/inventory/adjustments

## Overview

Records an **inventory adjustment** for a product. Use this endpoint for stock correction workflows. Request body is validated with `@Valid` before adjustment is applied. Returns updated `InventoryDTO`.

## Endpoint

| Property      | Value                           |
|---------------|---------------------------------|
| **Method**    | `POST`                          |
| **URL**       | `/api/v1/inventory/adjustments` |
| **Auth**      | Bearer Token (JWT) — required   |
| **Privilege** | `hasAnyRole('ADMIN','MANAGER')` |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Request Body

| Field     | Type                         | Required | Description                                 |
|-----------|------------------------------|----------|---------------------------------------------|
| `request` | `InventoryAdjustmentRequest` | Yes      | Adjustment payload validated with `@Valid`. |

Example:

```json
{"productId":2,"quantity":5}
```

---

## Response

### `200 OK`

Returns `InventoryDTO`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Missing required role/permission   |
| `400 Bad Request`       | `INVALID_INPUT` | Request payload validation failed  |
| `404 Not Found`         | `NOT_FOUND`     | Product/inventory does not exist   |

---

## Notes

- Controller mapping: `InventoryControllerV1#adjustStock` -> `InventoryAdjustmentService#adjustStock`.
