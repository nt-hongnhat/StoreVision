# PATCH /api/v1/products/{id}

## Overview

Restores a previously soft-deleted **product** using `ProductRestoreRequest`. Use this endpoint to reactivate archived products. Input is validated with `@Valid`, then restore operation is applied. The latest product state is returned.

## Endpoint

| Property      | Value                           |
|---------------|---------------------------------|
| **Method**    | `PATCH`                         |
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

| Field     | Type                    | Required | Description                              |
|-----------|-------------------------|----------|------------------------------------------|
| `request` | `ProductRestoreRequest` | Yes      | Restore payload validated with `@Valid`. |

Example:

```json
{
  "deleted": false
}
```

---

## Response

### `200 OK`

Returns restored `ProductDTO`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Missing required role/permission   |
| `400 Bad Request`       | `INVALID_INPUT` | Request payload validation failed  |
| `404 Not Found`         | `NOT_FOUND`     | Product does not exist             |

---

## Notes

- Controller mapping: `ProductControllerV1#restoreProduct` -> `ProductDeletionService#restore` then `ProductRetrievalService#findProductDTOById`.
- Endpoint is not annotated with `@RateLimited`.
