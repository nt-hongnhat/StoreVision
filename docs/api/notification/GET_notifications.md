# GET /api/v1/notifications

## Overview

Retrieves **notifications** in paginated format. Use this endpoint to render inbox-style notification lists. Query params control page/size. Returns `Page<NotificationDTO>`.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `GET`                                                               |
| **URL**       | `/api/v1/notifications`                                             |
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

Returns `Page<NotificationDTO>`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |

---

## Notes

- Controller mapping: `NotificationControllerV1#getNotifications` -> `NotificationRetrievalService#findNotifications`.
