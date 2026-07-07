# DELETE /api/v1/vouchers/{id}

## Overview

Performs a **soft delete** on a voucher by ID. Use this endpoint to deactivate a voucher without removing historical references. The method returns no content on success. Downstream systems can restore the voucher later via restore endpoint.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `DELETE`                                                            |
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

### `204 No Content`

Empty body — voucher is marked as deleted.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |
| `404 Not Found`         | `NOT_FOUND`     | Voucher does not exist             |

---

## Notes

- Controller mapping: `VoucherControllerV1#deleteVoucher` -> `VoucherDeletionService#markAsDeleted`.
- Endpoint is not annotated with `@RateLimited`.
