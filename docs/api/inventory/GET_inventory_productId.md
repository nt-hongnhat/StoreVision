# GET /api/v1/inventory/{productId}

## Overview

Returns stock information for a single **product inventory** record. Use this endpoint for detail checks in POS and back-office tools. Path parameter `productId` identifies the product. Response is `InventoryDTO`.

## Endpoint

| Property      | Value                                   |
|---------------|-----------------------------------------|
| **Method**    | `GET`                                   |
| **URL**       | `/api/v1/inventory/{productId}`         |
| **Auth**      | Bearer Token (JWT) — required           |
| **Privilege** | `hasAnyRole('ADMIN','MANAGER','STAFF')` |

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

Returns `InventoryDTO`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Missing required role/permission   |
| `404 Not Found`         | `NOT_FOUND`     | Inventory record not found         |

---

## Notes

- Controller mapping: `InventoryControllerV1#getInventoryByProductId` -> `InventoryRetrievalService#findInventoryByProductId`.
