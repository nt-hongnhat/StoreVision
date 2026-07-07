# GET /api/v1/analytics/inventory/valuation

## Overview

Returns current **inventory valuation** metrics. Use this endpoint for financial dashboard widgets showing stock value. No input payload is required. Response is `InventoryValuationDTO`.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `GET`                                                               |
| **URL**       | `/api/v1/analytics/inventory/valuation`                             |
| **Auth**      | Bearer Token (JWT) — required                                       |
| **Privilege** | Not explicitly declared in controller (`@PreAuthorize` not present) |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

No request body required.

---

## Response

### `200 OK`

Returns `InventoryValuationDTO`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |

---

## Notes

- Controller mapping: `AnalyticsControllerV1#getInventoryValuation` -> `AnalyticsRetrievalService#getInventoryValuation`.
