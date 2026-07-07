# GET /api/v1/customers/{id}

## Overview

Fetches one **customer** by identifier. Use this endpoint for customer profile detail screens. The endpoint reads path variable `id` and returns customer data if found. Not-found is returned when the ID does not exist.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `GET`                                                               |
| **URL**       | `/api/v1/customers/{id}`                                            |
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

Returns `CustomerDTO`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |
| `404 Not Found`         | `NOT_FOUND`     | Customer does not exist            |

---

## Notes

- Controller mapping: `CustomerControllerV1#getCustomerById` -> `CustomerRetrievalService#findCustomerDTOById`.
