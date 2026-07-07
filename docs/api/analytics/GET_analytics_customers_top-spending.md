# GET /api/v1/analytics/customers/top-spending

## Overview

Returns **top-spending customers** with configurable limit. Use this endpoint for CRM and loyalty insights dashboards. Only query parameter is result limit. Response is `List<TopSpendingCustomerDTO>`.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `GET`                                                               |
| **URL**       | `/api/v1/analytics/customers/top-spending`                          |
| **Auth**      | Bearer Token (JWT) — required                                       |
| **Privilege** | Not explicitly declared in controller (`@PreAuthorize` not present) |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Path / Query Parameters

| Name    | In    | Type  | Required | Default                             | Description                    |
|---------|-------|-------|----------|-------------------------------------|--------------------------------|
| `limit` | Query | `int` | No       | `DefaultRequestParam.DEFAULT_LIMIT` | Number of customers to return. |

No request body required.

---

## Response

### `200 OK`

Returns `List<TopSpendingCustomerDTO>`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |

---

## Notes

- Controller mapping: `AnalyticsControllerV1#getTopSpendingCustomers` -> `AnalyticsRetrievalService#getTopSpendingCustomers`.
