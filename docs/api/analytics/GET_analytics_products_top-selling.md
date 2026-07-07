# GET /api/v1/analytics/products/top-selling

## Overview

Returns **top-selling products** for a selected analysis period. Use this endpoint for best-seller dashboards. Query params accept result limit and period enum. Response is `List<TopSellingProductDTO>`.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `GET`                                                               |
| **URL**       | `/api/v1/analytics/products/top-selling`                            |
| **Auth**      | Bearer Token (JWT) — required                                       |
| **Privilege** | Not explicitly declared in controller (`@PreAuthorize` not present) |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Path / Query Parameters

| Name     | In    | Type              | Required | Default                              | Description                   |
|----------|-------|-------------------|----------|--------------------------------------|-------------------------------|
| `limit`  | Query | `int`             | No       | `DefaultRequestParam.DEFAULT_LIMIT`  | Number of products to return. |
| `period` | Query | `AnalyticsPeriod` | No       | `DefaultRequestParam.DEFAULT_PERIOD` | Analysis period.              |

No request body required.

---

## Response

### `200 OK`

Returns `List<TopSellingProductDTO>`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |

---

## Notes

- Controller mapping: `AnalyticsControllerV1#getTopSellingProducts` -> `AnalyticsRetrievalService#getTopSellingProducts`.
