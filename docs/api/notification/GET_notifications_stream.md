# GET /api/v1/notifications/stream

## Overview

Opens a long-lived **SSE stream** for real-time notifications. Use this endpoint for push updates in dashboards. The endpoint creates an `SseEmitter` and registers it in emitter service. Response is stream content rather than JSON object payload.

## Endpoint

| Property      | Value                                                               |
|---------------|---------------------------------------------------------------------|
| **Method**    | `GET`                                                               |
| **URL**       | `/api/v1/notifications/stream`                                      |
| **Auth**      | Bearer Token (JWT) — required                                       |
| **Privilege** | Not explicitly declared in controller (`@PreAuthorize` not present) |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |

No request body required.

---

## Response

### `200 OK`

Returns `SseEmitter` stream.

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Access denied by security policy   |

---

## Notes

- Controller mapping: `NotificationControllerV1#streamNotifications` -> `SseEmitterServiceImpl#addEmitter`.
- Connection timeout configured with `Long.MAX_VALUE` in controller.
