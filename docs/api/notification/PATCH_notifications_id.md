# PATCH /api/v1/notifications/{id}

## Overview

Marks a **notification** as read by ID. This endpoint accepts `NotificationReadRequest` body and validates it with `@Valid`. Use this endpoint to update read status in notification UIs. Returns updated `NotificationDTO`.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `PATCH`                                                             |
| **URL**       | `/api/v1/notifications/{id}`                                        |
| **Auth**      | Bearer Token (JWT) — required                                       |
| **Privilege** | Not explicitly declared in controller (`@PreAuthorize` not present) |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

### Path / Query Parameters

| Name | In   | Type   | Required | Default | Description              |
|------|------|--------|----------|---------|--------------------------|
| `id` | Path | `Long` | Yes      | -       | Notification identifier. |

### Request Body

| Field     | Type                      | Required | Description                                 |
|-----------|---------------------------|----------|---------------------------------------------|
| `request` | `NotificationReadRequest` | Yes      | Read-state payload validated with `@Valid`. |

Example:

```json
{"read":true}
```

---

## Response

### `200 OK`

Returns updated `NotificationDTO`.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |
| `400 Bad Request`       | `INVALID_INPUT` | Request payload validation failed  |
| `404 Not Found`         | `NOT_FOUND`     | Notification does not exist        |

---

## Notes

- Controller mapping: `NotificationControllerV1#markNotificationAsRead` -> `NotificationUpdateService#markAsRead`.
