# GET /api/v1/vouchers/{id}

## Overview

Fetches a single **voucher** by identifier. Use this endpoint when opening voucher detail pages or validating an ID exists. The endpoint reads `id` from path and delegates to retrieval service. If not found, a domain not-found exception is returned.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `GET`                                                               |
| **URL**       | `/api/v1/vouchers/{id}`                                             |
| **Auth**      | Bearer Token (JWT) — required                                       |
| **Privilege** | Not explicitly declared in controller (`@PreAuthorize` not present) |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Path / Query Parameters

| Name | In   | Type   | Required | Default | Description         |
|------|------|--------|----------|---------|---------------------|
| `id` | Path | `Long` | Yes      | -       | Voucher identifier. |

No request body required.

---

## Response

### `200 OK`

Returns `VoucherDTO`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |
| `404 Not Found`         | `NOT_FOUND`     | Voucher does not exist             |

---

## Notes

- Controller mapping: `VoucherControllerV1#getVoucherById` -> `VoucherRetrievalService#findById`.
- Endpoint is not annotated with `@RateLimited`.
