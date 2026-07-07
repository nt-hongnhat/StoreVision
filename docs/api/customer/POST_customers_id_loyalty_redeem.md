# POST /api/v1/customers/{id}/loyalty/redeem

## Overview

Redeems customer **loyalty points** to issue a voucher. Use this endpoint at checkout or loyalty redemption flows. The request body includes number of points and is validated by `@Valid`. On success, it returns issued `VoucherDTO`.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `POST`                                                              |
| **URL**       | `/api/v1/customers/{id}/loyalty/redeem`                             |
| **Auth**      | Bearer Token (JWT) — required                                       |
| **Privilege** | Not explicitly declared in controller (`@PreAuthorize` not present) |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Path / Query Parameters

| Name | In   | Type   | Required | Default | Description          |
|------|------|--------|----------|---------|----------------------|
| `id` | Path | `Long` | Yes      | -       | Customer identifier. |

### Request Body

| Field     | Type                  | Required | Description                                            |
|-----------|-----------------------|----------|--------------------------------------------------------|
| `request` | `RedeemPointsRequest` | Yes      | Redeem payload validated with `@Valid` (points value). |

Example:

```json
{
  "points": 500
}
```

---

## Response

### `201 Created`

Returns issued `VoucherDTO`.

### Error Responses

| Status                     | Code                   | Description                        |
|----------------------------|------------------------|------------------------------------|
| `401 Unauthorized`         | `UNAUTHORIZED`         | Missing or invalid JWT token       |
| `403 Forbidden`            | `ACCESS_DENIED`        | Access denied by security policy   |
| `400 Bad Request`          | `INVALID_INPUT`        | Request payload validation failed  |
| `404 Not Found`            | `NOT_FOUND`            | Customer does not exist            |
| `422 Unprocessable Entity` | `UNPROCESSABLE_ENTITY` | Insufficient points for redemption |

---

## Notes

- Controller mapping: `CustomerControllerV1#redeemPoints` -> `LoyaltyService#redeemPoints`.
