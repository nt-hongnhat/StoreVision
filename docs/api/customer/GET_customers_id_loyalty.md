# GET /api/v1/customers/{id}/loyalty

## Overview

Retrieves **loyalty details** for a customer. Use this endpoint for showing points balance and tier status. The endpoint takes customer ID from path. Returns `LoyaltyDetailsDTO`.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `GET`                                                               |
| **URL**       | `/api/v1/customers/{id}/loyalty`                                    |
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

No request body required.

---

## Response

### `200 OK`

Returns `LoyaltyDetailsDTO`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |
| `404 Not Found`         | `NOT_FOUND`     | Customer does not exist            |

---

## Notes

- Controller mapping: `CustomerControllerV1#getLoyaltyDetails` -> `LoyaltyService#getLoyaltyDetails`.
