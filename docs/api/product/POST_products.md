# POST /api/v1/products

## Overview

Creates a new **product** from `ProductDTO`. This endpoint is used when adding a new catalog item. The body is validated with `@Valid` and uniqueness rules are enforced by service logic. On success, the created product is returned.

## Endpoint

| Property      | Value                           |
|---------------|---------------------------------|
| **Method**    | `POST`                          |
| **URL**       | `/api/v1/products`              |
| **Auth**      | Bearer Token (JWT) — required   |
| **Privilege** | `hasAnyRole('ADMIN','MANAGER')` |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Request Body

| Field | Type         | Required | Description                              |
|-------|--------------|----------|------------------------------------------|
| `dto` | `ProductDTO` | Yes      | Product payload validated with `@Valid`. |

Example:

```json
{
  "name": "Arabica Coffee 500g",
  "category": "Beverage"
}
```

---

## Response

### `201 Created`

Returns created `ProductDTO`.

### Error Responses

| Status             | Code            | Description                             |
|--------------------|-----------------|-----------------------------------------|
| `401 Unauthorized` | `UNAUTHORIZED`  | Missing or invalid JWT token            |
| `403 Forbidden`    | `ACCESS_DENIED` | Missing required role/permission        |
| `400 Bad Request`  | `INVALID_INPUT` | Request payload validation failed       |
| `409 Conflict`     | `CONFLICT`      | Product name already exists in category |

---

## Notes

- Controller mapping: `ProductControllerV1#createProduct` -> `ProductCreationService#create`.
- Endpoint is not annotated with `@RateLimited`.
