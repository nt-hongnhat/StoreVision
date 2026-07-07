# GET /api/v1/analytics/orders/summary

## Overview

Returns **order summary** statistics in an optional date range. Use this endpoint to display aggregated order KPIs. Date filters are optional; service validates date range rules. Response is `OrderSummaryDTO`.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `GET`                                                               |
| **URL**       | `/api/v1/analytics/orders/summary`                                  |
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

Returns `OrderSummaryDTO`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |
| `400 Bad Request`       | `INVALID_INPUT` | Invalid date range                 |

---

## Notes

- Controller mapping: `AnalyticsControllerV1#getOrderSummary` -> `AnalyticsRetrievalService#getOrderSummary`.
