# Technical Documentation Generation Prompt

Prompt tái sử dụng để sinh file technical documentation trong `docs/` theo đúng
*Documentation Conventions* của `CLAUDE.md`. Bao phủ 3 loại doc gắn với thay đổi code.

**Format được ghi sẵn đầy đủ trong prompt bên dưới — TỰ CHỨA, KHÔNG cần file mẫu nào tồn tại.**
Dùng được cho cả project chưa có doc mẫu. Nếu project đã có sẵn doc cùng loại thì đọc thêm
để bám house-style, nhưng format chuẩn là các template trong prompt.

| Loại thay đổi                     | Thư mục                      | Tên file BẮT BUỘC  | Mẫu tham chiếu (tùy chọn, nếu repo có)                        |
|-----------------------------------|------------------------------|--------------------|---------------------------------------------------------------|
| Feature / enhancement (có ticket) | `docs/features/<ticket-id>/` | `technical-doc.md` | `docs/features/PQF-20586/technical-doc.md`                    |
| Bug fix (có ticket)               | `docs/fix/<ticket-id>/`      | `root-cause.md`    | `docs/fix/PQF-20402/root-cause.md`                            |
| Refactor (slug hoặc ticket)       | `docs/refactoring/<slug>/`   | `refactor-doc.md`  | `docs/refactoring/seal-ai-provider-hierarchy/refactor-doc.md` |

Sao chép nguyên khối prompt bên dưới, điền loại doc + ticket-id/slug + phạm vi thay đổi.

---

## Prompt

````markdown
Tạo file technical documentation cho <loại: feature | fix | refactor> `<ticket-id | slug>`
(ví dụ: feature `PQF-20586`, fix `PQF-20402`, refactor `seal-ai-provider-hierarchy`).

## Nguồn dữ liệu (BẮT BUỘC đọc trước khi viết — KHÔNG được bịa)
Doc mô tả thay đổi code THỰC TẾ, nên phải trích xuất từ chính diff/commit và các class:
1. Xác định phạm vi thay đổi:
    - `git status` / `git diff <base>...HEAD` để lấy danh sách file thay đổi (base thường là `develop`).
    - Nếu là refactor xuất phát từ finding trong `docs/guides/service-component-complexity-solid-review.md`,
      đọc finding tương ứng để lấy motivation.
2. Đọc từng file thay đổi để mô tả CHÍNH XÁC:
    - Class/method thực tế bị thêm/sửa/xoá, đường dẫn đầy đủ (`src/main/java/...`).
    - Với feature: kiến trúc/luồng dữ liệu, entity/column/constraint mới, endpoint/DTO thay đổi.
    - Với fix: chuỗi lỗi (failure chain) từng bước, file + method + snippet minh hoạ nguyên nhân.
    - Với refactor: cấu trúc trước/sau, lý do (tech debt, vi phạm SOLID, trùng lặp), khẳng định hành vi KHÔNG đổi.
3. Đọc test liên quan (`src/test/...`) để ghi phần Testing bằng tên test/lớp test THỰC TẾ, không bịa.
4. Hằng số/tên header/privilege phải truy về constant thực (ví dụ `McpProtocolConstants.HEADER_WORKSPACE_ID` → `X-Tenant-ID`).

## Vị trí & tên file (theo CLAUDE.md — không được sai)
- feature → `docs/features/<ticket-id>/technical-doc.md`  (BẮT BUỘC có ticket-id)
- fix     → `docs/fix/<ticket-id>/root-cause.md`           (BẮT BUỘC có ticket-id)
- refactor→ `docs/refactoring/<slug|ticket-id>/refactor-doc.md`
- KHÔNG để file .md rời ở gốc `docs/`; KHÔNG đặt sai tên file; mỗi thư mục con đúng 1 file theo bảng trên.
- Nếu 1 thay đổi đụng nhiều ticket → mỗi ticket 1 file riêng.

## Format BẮT BUỘC — chọn template theo loại (giữ nguyên thứ tự heading)

### technical-doc.md (feature)
```markdown
# <Ticket-ID> – <Feature Title>

## Overview
Mô tả ngắn feature làm gì và vì sao cần. In đậm từ khoá quan trọng.

## Background
Bối cảnh, động cơ, ticket liên quan.

## Technical Design
### Architecture / Data Flow
### Component Changes
Bảng liệt kê từng file thay đổi: đường dẫn đầy đủ | vai trò/nội dung thay đổi.

## API Changes
Endpoint mới/đổi — method, path, request/response schema. Không có thì ghi rõ "No new REST endpoints".

## Database Changes
Schema/column/constraint mới, migration. DDL do `HIBERNATE_DDL_AUTO` (default `validate`).

## Implementation Notes
Quyết định không hiển nhiên, tradeoff, workaround (kèm snippet code thực tế).

## Testing
Unit + integration test bao phủ feature — nêu tên lớp/test THỰC TẾ.

## Rollout
Feature flag, phased rollout, backward-compat, yêu cầu cấu hình/env.
```

### root-cause.md (fix)
```markdown
# <Ticket-ID> – <Bug Title>

## Bug Description
Hành vi sai, bước tái hiện, môi trường ảnh hưởng.

## Root Cause
Phân tích từng bước vì sao lỗi xảy ra. Kèm: đường dẫn file, tên method, snippet minh hoạ.

## Fix
### Changes Made
Mô tả từng file thay đổi và lý do.

## Testing
Cách xác nhận fix đúng — bước thủ công và/hoặc tên test case.

## Summary
| # | File | Problem | Fix |
|---|------|---------|-----|
```

### refactor-doc.md (refactor)
```markdown
# <Slug | Ticket-ID> – <Refactor Title>

## Motivation
Lý do refactor: tech debt, vi phạm kiến trúc, trùng lặp, hiệu năng…
Khẳng định rõ: hành vi KHÔNG đổi (đây là refactor, không phải feature/fix).

## Scope
Phạm vi ảnh hưởng: module/package/class liên quan; những gì KHÔNG nằm trong scope.

## Before / After
So sánh cấu trúc trước/sau (diagram, snippet, hoặc bảng so sánh).

## Changes Made
Liệt kê từng file thay đổi: đường dẫn đầy đủ | mô tả thay đổi.

## Risk & Compatibility
Rủi ro, breaking change (nếu có), backward-compat, cách rollback.

## Testing
Bằng chứng hành vi không đổi: test cũ vẫn xanh, test bổ sung (nếu có).
```

## Ràng buộc
- Ngôn ngữ nội dung doc: tiếng Anh (đồng bộ với các mẫu). Định danh code giữ nguyên.
- Chỉ ghi những gì code THỰC SỰ có; không suy diễn schema/endpoint/test không tồn tại.
- Đúng thư mục + đúng tên file theo bảng trên; tạo doc CÙNG LÚC với thay đổi code, không phải sau.
- Không nhét nội dung doc bắt buộc này vào file khác (ví dụ viết vào `architecture/`) để thay thế.
````