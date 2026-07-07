# GET /api/v1/inventory/{productId}/movements

## Overview

Returns **stock movement history** for a product. Use this endpoint for audit trails and movement timelines. Input is product ID path parameter. Response is `List<StockMovementDTO>`.

## Endpoint

| Property      | Value                                     |
|---------------|-------------------------------------------|
| **Method**    | `GET`                                     |
| **URL**       | `/api/v1/inventory/{productId}/movements` |
| **Auth**      | Bearer Token (JWT) — required             |
| **Privilege** | `hasAnyRole('ADMIN','MANAGER')`           |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Path / Query Parameters

| Name        | In   | Type   | Required | Default | Description         |
|-------------|------|--------|----------|---------|---------------------|
| `productId` | Path | `Long` | Yes      | -       | Product identifier. |

No request body required.

---

## Response

### `200 OK`

Returns `List<StockMovementDTO>`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Missing required role/permission   |
| `404 Not Found`         | `NOT_FOUND`     | Product/inventory does not exist   |

---

## Notes

- Controller mapping: `InventoryControllerV1#getMovementsByProductId` -> `InventoryRetrievalService#findMovementsByProductId`.
