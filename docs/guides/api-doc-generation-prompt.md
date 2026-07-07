# API Documentation Generation Prompt

Prompt tái sử dụng để sinh file API documentation theo đúng format chuẩn
(tham chiếu mẫu `docs/api/analytics/DELETE_analytics_cache.md`).

Sao chép nguyên khối prompt bên dưới, thay `<METHOD> <path>` bằng endpoint cần viết doc.

---

## Prompt

````markdown
Tạo file API documentation cho endpoint `<METHOD> <path>` (ví dụ: `POST /api/v1/mcp/sessions/initialize`).

## Nguồn dữ liệu (BẮT BUỘC đọc trước khi viết)
Trước khi viết, hãy đọc và trích xuất thông tin THỰC TẾ từ code — KHÔNG được bịa:
1. Controller impl `controller/v1/<Name>ControllerV1.java` và interface `controller/<Name>Controller.java`:
   - HTTP method, path (lấy từ hằng số `ApiEndpoint.Path`, không hardcode)
   - `@PreAuthorize("hasPrivilege(...)")` → privilege (hằng số `McpPrivilege.Constants`)
   - `@Operation` / `@ApiResponse` → mã trạng thái, mô tả
   - `@RateLimited` → có rate limit hay không
   - Request: `@RequestBody` DTO, `@RequestParam`, `@PathVariable`, `@ModelAttribute`, header (`McpProtocolConstants.HEADER_*`)
   - Bean Validation (`@Valid`, `@NotNull`, `@Size`...) trên DTO request
   - Return type thực tế (DTO / `Page<T>` / `void` / `ResponseEntity`)
2. DTO request/response tương ứng để mô tả schema từng field.
3. Service để nắm side effect / business note đáng ghi chú.

## Vị trí & tên file
- Đặt tại `docs/api/<category>/` với `<category>` theo domain (analytics, session, provider, quota, streaming…).
- Tên file: `<METHOD>_<path_slug>.md` (ví dụ: `POST_sessions_initialize.md`).

## Format BẮT BUỘC (bám sát mẫu dưới, giữ nguyên thứ tự & style bảng markdown)

```markdown
# <METHOD> <full-url-path>

## Overview

Mô tả ngắn gọn endpoint làm gì và khi nào dùng (2-4 câu). In đậm từ khóa quan trọng.

## Endpoint

| Property      | Value                         |
|---------------|-------------------------------|
| **Method**    | `<METHOD>`                    |
| **URL**       | `<full-url-path>`             |
| **Auth**      | Bearer Token (JWT) — required |
| **Privilege** | `<Privilege>`                 |

---

## Request

### Headers

| Header          | Required | Description          |
|-----------------|----------|----------------------|
| `Authorization` | Yes      | `Bearer <jwt_token>` |
| <header khác nếu có, ví dụ X-Tenant-ID> | | |

### Path / Query Parameters
(chỉ thêm mục này nếu có; mô tả tên, kiểu, required, default, ràng buộc)

### Request Body
(chỉ thêm nếu có body; bảng field | type | required | description, kèm ví dụ JSON)

---

## Response

### <2xx thành công>

Mô tả body trả về; nếu có schema thì bảng field | type | description + ví dụ JSON.
Nếu empty body thì ghi rõ "Empty body — ...".

### Error Responses

| Status                  | Code            | Description                        |
|-------------------------|-----------------|------------------------------------|
| `401 Unauthorized`      | `UNAUTHORIZED`  | Missing or invalid JWT token       |
| `403 Forbidden`         | `ACCESS_DENIED` | Missing `<Privilege>` privilege    |
| `429 Too Many Requests` | `RATE_LIMITED`  | Rate limit exceeded                |
| <các mã lỗi domain khác thực tế có thể xảy ra: 400/404/409...> | | |

---

## Notes

- Các lưu ý kỹ thuật quan trọng (cache được clear, side effect, ràng buộc phân trang max size, backward-compat…).
```

## Ràng buộc
- Ngôn ngữ nội dung doc: tiếng Anh (đồng bộ với mẫu). Định danh code giữ nguyên.
- Chỉ ghi những gì code THỰC SỰ có; endpoint nào không có body/param/header thì ghi rõ "No request body required." thay vì bịa bảng rỗng.
- Nếu endpoint không bị `@RateLimited` thì bỏ dòng `429` khỏi bảng lỗi.
- Mã lỗi phải khớp global `@RestControllerAdvice` (không tự chế mã lỗi).
````
