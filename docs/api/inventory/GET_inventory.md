# GET /api/v1/inventory

## Overview

Lists **inventory stock levels** with optional low-stock filter. Use this endpoint for inventory dashboards and replenishment checks. Query parameter `lowStock` toggles filtered mode. Returns `List<InventoryDTO>`.

## Endpoint

| Property      | Value                           |
|---------------|---------------------------------|
| **Method**    | `GET`                           |
| **URL**       | `/api/v1/inventory`             |
| **Auth**      | Bearer Token (JWT) — required   |
| **Privilege** | `hasAnyRole('ADMIN','MANAGER')` |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Path / Query Parameters

| Name       | In    | Type      | Required | Default | Description                             |
|------------|-------|-----------|----------|---------|-----------------------------------------|
| `lowStock` | Query | `boolean` | No       | `false` | `true` returns low-stock products only. |

No request body required.

---

## Response

### `200 OK`

Returns `List<InventoryDTO>`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Missing required role/permission   |

---

## Notes

- Controller mapping: `InventoryControllerV1#getAllInventory` -> `InventoryRetrievalService#findLowStockProducts` or `#findAllInventory`.
