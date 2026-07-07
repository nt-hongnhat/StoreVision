# DELETE /api/v1/order-items/{id}

## Overview

Soft-deletes an **order item** by ID. Use this endpoint to remove line items from active views while preserving historical traceability. Response body is empty on success.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `DELETE`                                                            |
| **URL**       | `/api/v1/order-items/{id}`                                          |
| **Auth**      | Bearer Token (JWT) — required                                       |
| **Privilege** | Not explicitly declared in controller (`@PreAuthorize` not present) |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Path / Query Parameters

| Name | In   | Type   | Required | Default | Description            |
|------|------|--------|----------|---------|------------------------|
| `id` | Path | `Long` | Yes      | -       | Order-item identifier. |

No request body required.

---

## Response

### `204 No Content`

Empty body — order item is marked as deleted.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |
| `404 Not Found`         | `NOT_FOUND`     | Order item does not exist          |

---

## Notes

- Controller mapping: `OrderItemControllerV1#deleteOrderItem` -> `OrderItemDeletionService#markAsDeleted`.
