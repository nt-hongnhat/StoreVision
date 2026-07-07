# DELETE /api/v1/orders/{id}

## Overview

Soft-deletes an **order** by identifier. Use this endpoint to hide/cancel order records from active operations while preserving history. Response body is empty on success.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `DELETE`                                                            |
| **URL**       | `/api/v1/orders/{id}`                                               |
| **Auth**      | Bearer Token (JWT) — required                                       |
| **Privilege** | Not explicitly declared in controller (`@PreAuthorize` not present) |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Path / Query Parameters

| Name | In   | Type   | Required | Default | Description       |
|------|------|--------|----------|---------|-------------------|
| `id` | Path | `Long` | Yes      | -       | Order identifier. |

No request body required.

---

## Response

### `204 No Content`

Empty body — order is marked as deleted.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |
| `404 Not Found`         | `NOT_FOUND`     | Order does not exist               |

---

## Notes

- Controller mapping: `OrderControllerV1#deleteOrder` -> `OrderDeletionService#markAsDeleted`.
