# DELETE /api/v1/customers/{id}

## Overview

Soft-deletes a **customer** by identifier. Use this endpoint to deactivate customer profiles while preserving historical links. If the customer has pending orders, deletion is rejected by domain rules. Successful response has empty body.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `DELETE`                                                            |
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

### `204 No Content`

Empty body — customer is marked as deleted.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |
| `404 Not Found`         | `NOT_FOUND`     | Customer does not exist            |
| `409 Conflict`          | `CONFLICT`      | Customer has pending orders        |

---

## Notes

- Controller mapping: `CustomerControllerV1#deleteCustomer` -> `CustomerDeletionService#markAsDeleted`.
