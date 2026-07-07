# GET /api/v1/inventory/receipts

## Overview

Retrieves **stock receipt history** in paginated format with optional date-range filtering. Use this endpoint for inbound stock audit and reconciliation reports. Query parameters include `from`, `to`, `page`, and `size`. Returns `Page<StockReceiptDTO>`.

## Endpoint

| Property      | Value                           |
|---------------|---------------------------------|
| **Method**    | `GET`                           |
| **URL**       | `/api/v1/inventory/receipts`    |
| **Auth**      | Bearer Token (JWT) — required   |
| **Privilege** | `hasAnyRole('ADMIN','MANAGER')` |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Path / Query Parameters

| Name   | In    | Type            | Required | Default                            | Description               |
|--------|-------|-----------------|----------|------------------------------------|---------------------------|
| `from` | Query | `LocalDateTime` | No       | -                                  | Optional start timestamp. |
| `to`   | Query | `LocalDateTime` | No       | -                                  | Optional end timestamp.   |
| `page` | Query | `int`           | No       | `DefaultRequestParam.DEFAULT_PAGE` | Zero-based page index.    |
| `size` | Query | `int`           | No       | `DefaultRequestParam.DEFAULT_SIZE` | Page size.                |

No request body required.

---

## Response

### `200 OK`

Returns `Page<StockReceiptDTO>`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Missing required role/permission   |

---

## Notes

- Controller mapping: `InventoryControllerV1#getReceiptHistory` -> `InventoryRetrievalService#findReceiptHistory`.
