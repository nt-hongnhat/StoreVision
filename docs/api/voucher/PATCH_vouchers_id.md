# PATCH /api/v1/vouchers/{id}

## Overview

Restores a previously soft-deleted **voucher**. This endpoint uses a `VoucherRestoreRequest` body validated by `@Valid`. It triggers restore logic and returns the current voucher snapshot afterward. Use this endpoint for undoing soft delete operations.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `PATCH`                                                             |
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

### Request Body

| Field     | Type                    | Required | Description                              |
|-----------|-------------------------|----------|------------------------------------------|
| `request` | `VoucherRestoreRequest` | Yes      | Restore payload validated with `@Valid`. |

Example:

```json
{
  "deleted": false
}
```

---

## Response

### `200 OK`

Returns restored `VoucherDTO`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |
| `400 Bad Request`       | `INVALID_INPUT` | Request payload validation failed  |
| `404 Not Found`         | `NOT_FOUND`     | Voucher does not exist             |

---

## Notes

- Controller mapping: `VoucherControllerV1#restoreVoucher` -> `VoucherDeletionService#restore` then `VoucherRetrievalService#findById`.
- Endpoint is not annotated with `@RateLimited`.
