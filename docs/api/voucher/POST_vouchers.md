# POST /api/v1/vouchers

## Overview

Creates a new **voucher** record from the provided `VoucherDTO` payload. Use this endpoint when onboarding a new discount program into the system. Input is validated with `@Valid` before persistence. The response returns the created voucher data.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `POST`                                                              |
| **URL**       | `/api/v1/vouchers`                                                  |
| **Auth**      | Bearer Token (JWT) — required                                       |
| **Privilege** | Not explicitly declared in controller (`@PreAuthorize` not present) |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Request Body

| Field | Type | Required | Description |
|---|---|---|---|
| `dto` | `VoucherDTO` | Yes | Voucher payload validated with `@Valid`. |

Example:

```json
{
  "code": "SUMMER10",
  "discountType": "PERCENTAGE",
  "discountValue": 10
}
```

---

## Response

### `201 Created`

Returns the created `VoucherDTO` payload.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |
| `400 Bad Request`       | `INVALID_INPUT` | Request payload validation failed  |

---

## Notes

- Controller mapping: `VoucherControllerV1#createVoucher` -> `VoucherCreationService#create`.
- Endpoint is not annotated with `@RateLimited`.
