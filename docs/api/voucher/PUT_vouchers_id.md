# PUT /api/v1/vouchers/{id}

## Overview

Updates an existing **voucher** using `VoucherDTO`. Use this endpoint for editing voucher settings. Input body is validated with `@Valid`, then applied to the target voucher by ID. The updated voucher is returned.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `PUT`                                                               |
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

| Field | Type         | Required | Description                                      |
|-------|--------------|----------|--------------------------------------------------|
| `dto` | `VoucherDTO` | Yes      | Updated voucher payload validated with `@Valid`. |

Example:

```json
{
  "code": "SUMMER15",
  "discountType": "PERCENTAGE",
  "discountValue": 15
}
```

---

## Response

### `200 OK`

Returns updated `VoucherDTO`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |
| `400 Bad Request`       | `INVALID_INPUT` | Request payload validation failed  |
| `404 Not Found`         | `NOT_FOUND`     | Voucher does not exist             |

---

## Notes

- Controller mapping: `VoucherControllerV1#updateVoucher` -> `VoucherUpdateService#update`.
- Endpoint is not annotated with `@RateLimited`.
