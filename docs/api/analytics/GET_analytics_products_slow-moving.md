# GET /api/v1/analytics/products/slow-moving

## Overview

Returns **slow-moving products** based on lookback window and limit. Use this endpoint for inventory optimization analysis. Query params control top-N and lookback days. Response is `List<SlowMovingProductDTO>`.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `GET`                                                               |
| **URL**       | `/api/v1/analytics/products/slow-moving`                            |
| **Auth**      | Bearer Token (JWT) — required                                       |
| **Privilege** | Not explicitly declared in controller (`@PreAuthorize` not present) |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Path / Query Parameters

| Name    | In    | Type  | Required | Default                             | Description                   |
|---------|-------|-------|----------|-------------------------------------|-------------------------------|
| `limit` | Query | `int` | No       | `DefaultRequestParam.DEFAULT_LIMIT` | Number of products to return. |
| `days`  | Query | `int` | No       | `DefaultRequestParam.DEFAULT_DAYS`  | Lookback days window.         |

No request body required.

---

## Response

### `200 OK`

Returns `List<SlowMovingProductDTO>`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |

---

## Notes

- Controller mapping: `AnalyticsControllerV1#getSlowMovingProducts` -> `AnalyticsRetrievalService#getSlowMovingProducts`.
