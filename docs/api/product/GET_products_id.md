# GET /api/v1/products/{id}

## Overview

Fetches one **product** by ID. Use this endpoint for product detail pages and downstream validation checks. The ID is provided in path. Returns `ProductDTO` if found.

## Endpoint

| Property      | Value                                   |
|---------------|-----------------------------------------|
| **Method**    | `GET`                                   |
| **URL**       | `/api/v1/products/{id}`                 |
| **Auth**      | Bearer Token (JWT) — required           |
| **Privilege** | `hasAnyRole('ADMIN','MANAGER','STAFF')` |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Path / Query Parameters

| Name | In   | Type   | Required | Default | Description         |
|------|------|--------|----------|---------|---------------------|
| `id` | Path | `Long` | Yes      | -       | Product identifier. |

No request body required.

---

## Response

### `200 OK`

Returns `ProductDTO`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Missing required role/permission   |
| `404 Not Found`         | `NOT_FOUND`     | Product does not exist             |

---

## Notes

- Controller mapping: `ProductControllerV1#getProductById` -> `ProductRetrievalService#findProductDTOById`.
- Endpoint is not annotated with `@RateLimited`.
