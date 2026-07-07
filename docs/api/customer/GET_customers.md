# GET /api/v1/customers

## Overview

Retrieves **customers** as a paginated list. Use this endpoint for customer management views. Pagination is controlled by `page` and `size` query parameters with defaults from constants. Returns `Page<CustomerDTO>`.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `GET`                                                               |
| **URL**       | `/api/v1/customers`                                                 |
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

Returns `Page<CustomerDTO>`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |

---

## Notes

- Controller mapping: `CustomerControllerV1#getAllCustomers` -> `CustomerRetrievalService#findCustomersPage`.
