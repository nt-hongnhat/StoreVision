# GET /api/v1/employees

## Overview

Retrieves employees in **paginated** form for management screens. Uses `page` and `size` query parameters. Returns `Page<EmployeeDTO>`.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `GET`                                                               |
| **URL**       | `/api/v1/employees`                                                 |
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

Returns `Page<EmployeeDTO>`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |

---

## Notes

- Controller mapping: `EmployeeControllerV1#getAllEmployees` -> `EmployeeRetrievalService#findEmployeesPage`.
