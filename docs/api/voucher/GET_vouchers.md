# GET /api/v1/vouchers

## Overview

Retrieves vouchers in a **paginated** format. Use this endpoint for management screens that need page-based loading. Pagination is controlled by `page` and `size` query params with default values from API constants. The response type is `Page<VoucherDTO>`.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `GET`                                                               |
| **URL**       | `/api/v1/vouchers`                                                  |
| **Auth**      | Bearer Token (JWT) — required                                       |
| **Privilege** | Not explicitly declared in controller (`@PreAuthorize` not present) |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Path / Query Parameters

| Name   | In    | Type  | Required | Default                            | Description            |
|--------|-------|-------|----------|------------------------------------|------------------------|
| `page` | Query | `int` | No       | `DefaultRequestParam.DEFAULT_PAGE` | Zero-based page index. |
| `size` | Query | `int` | No       | `DefaultRequestParam.DEFAULT_SIZE` | Page size.             |

No request body required.

---

## Response

### `200 OK`

Returns `Page<VoucherDTO>`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |

---

## Notes

- Controller mapping: `VoucherControllerV1#getAllVouchers` -> `VoucherRetrievalService#findVouchersPage`.
- Endpoint is not annotated with `@RateLimited`.
