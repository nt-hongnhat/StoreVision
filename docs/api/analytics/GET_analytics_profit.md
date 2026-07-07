# GET /api/v1/analytics/profit

## Overview

Computes **gross profit** metrics over an optional date range. Use this endpoint for profitability dashboards. Date filters are optional and validated by service logic. Response is `ProfitDTO`.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `GET`                                                               |
| **URL**       | `/api/v1/analytics/profit`                                          |
| **Auth**      | Bearer Token (JWT) — required                                       |
| **Privilege** | Not explicitly declared in controller (`@PreAuthorize` not present) |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Path / Query Parameters

| Name   | In    | Type        | Required | Default | Description          |
|--------|-------|-------------|----------|---------|----------------------|
| `from` | Query | `LocalDate` | No       | -       | Optional start date. |
| `to`   | Query | `LocalDate` | No       | -       | Optional end date.   |

No request body required.

---

## Response

### `200 OK`

Returns `ProfitDTO`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |
| `400 Bad Request`       | `INVALID_INPUT` | Invalid date range                 |

---

## Notes

- Controller mapping: `AnalyticsControllerV1#getProfit` -> `AnalyticsRetrievalService#getProfit`.
