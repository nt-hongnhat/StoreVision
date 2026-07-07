# GET /api/v1/products

## Overview

Retrieves **active products** in paginated form with optional name/category filters. Use this endpoint for product list screens and searchable catalog views. Pagination and filters are read from query parameters. Returns `Page<ProductDTO>`.

## Endpoint

| Property      | Value                         |
|---------------|-------------------------------|
| **Method**    | `GET`                         |
| **URL**       | `/api/v1/products`            |
| **Auth**      | Bearer Token (JWT) — required |
| **Privilege** | `isAuthenticated()`           |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Path / Query Parameters

| Name       | In    | Type     | Required | Default                            | Description                   |
|------------|-------|----------|----------|------------------------------------|-------------------------------|
| `page`     | Query | `int`    | No       | `DefaultRequestParam.DEFAULT_PAGE` | Zero-based page index.        |
| `size`     | Query | `int`    | No       | `DefaultRequestParam.DEFAULT_SIZE` | Page size.                    |
| `name`     | Query | `String` | No       | -                                  | Optional product name filter. |
| `category` | Query | `String` | No       | -                                  | Optional category filter.     |

No request body required.

---

## Response

### `200 OK`

Returns `Page<ProductDTO>`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Missing required role/permission   |

---

## Notes

- Controller mapping: `ProductControllerV1#getAllProducts` -> `ProductRetrievalService#findProductsPage`.
- Endpoint is not annotated with `@RateLimited`.
