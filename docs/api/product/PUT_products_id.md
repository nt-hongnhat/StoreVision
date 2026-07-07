# PUT /api/v1/products/{id}

## Overview

Updates a **product** by identifier using `ProductDTO`. This endpoint is used for full update operations. Request data is validated via `@Valid` and business uniqueness constraints are checked in service. Returns updated `ProductDTO`.

## Endpoint

| Property      | Value                           |
|---------------|---------------------------------|
| **Method**    | `PUT`                           |
| **URL**       | `/api/v1/products/{id}`         |
| **Auth**      | Bearer Token (JWT) — required   |
| **Privilege** | `hasAnyRole('ADMIN','MANAGER')` |

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

### Request Body

| Field | Type         | Required | Description                                      |
|-------|--------------|----------|--------------------------------------------------|
| `dto` | `ProductDTO` | Yes      | Updated product payload validated with `@Valid`. |

Example:

```json
{
  "name": "Arabica Coffee 1kg",
  "category": "Beverage"
}
```

---

## Response

### `200 OK`

Returns updated `ProductDTO`.

### Error Responses

| Status             | Code            | Description                             |
|--------------------|-----------------|-----------------------------------------|
| `401 Unauthorized` | `UNAUTHORIZED`  | Missing or invalid JWT token            |
| `403 Forbidden`    | `ACCESS_DENIED` | Missing required role/permission        |
| `400 Bad Request`  | `INVALID_INPUT` | Request payload validation failed       |
| `404 Not Found`    | `NOT_FOUND`     | Product does not exist                  |
| `409 Conflict`     | `CONFLICT`      | Product name already exists in category |

---

## Notes

- Controller mapping: `ProductControllerV1#updateProduct` -> `ProductUpdateService#update`.
- Endpoint is not annotated with `@RateLimited`.
