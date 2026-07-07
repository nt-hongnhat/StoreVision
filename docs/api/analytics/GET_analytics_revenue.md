# GET /api/v1/analytics/revenue

## Overview

Returns **revenue series** grouped by selected dimension. Use this endpoint for trend charts. Optional date range and `groupBy` are accepted through query params. Response is `List<RevenuePointDTO>`.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `GET`                                                               |
| **URL**       | `/api/v1/analytics/revenue`                                         |
| **Auth**      | Bearer Token (JWT) — required                                       |
| **Privilege** | Not explicitly declared in controller (`@PreAuthorize` not present) |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Path / Query Parameters

| Name      | In    | Type             | Required | Default                                | Description            |
|-----------|-------|------------------|----------|----------------------------------------|------------------------|
| `from`    | Query | `LocalDate`      | No       | -                                      | Optional start date.   |
| `to`      | Query | `LocalDate`      | No       | -                                      | Optional end date.     |
| `groupBy` | Query | `RevenueGroupBy` | No       | `DefaultRequestParam.DEFAULT_GROUP_BY` | Revenue grouping mode. |

No request body required.

---

## Response

### `200 OK`

Returns `List<RevenuePointDTO>`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |
| `400 Bad Request`       | `INVALID_INPUT` | Invalid date range                 |

---

## Notes

- Controller mapping: `AnalyticsControllerV1#getRevenue` -> `AnalyticsRetrievalService#getRevenue`.
