# Flowable OSS Workflow Engine Roadmap (StoreVision)

## 1) Roadmap 4 tuần theo sprint

### Sprint 1 (Tuần 1): Foundation + POC flow đầu tiên
- **Mục tiêu:** Chạy được Flowable trong local và hoàn thành 1 flow POC end-to-end.
- **Deliverables:**
  - Tích hợp dependency Flowable OSS vào `build.gradle`.
  - Process BPMN đầu tiên: `order-approval.bpmn20.xml` (happy path).
  - API start process theo `businessKey = orderId`.
  - 1 service task gọi vào service hiện có (`OrderUpdateService`).
- **Công việc chi tiết:**
  - Chọn flow đầu tiên: `Order approval` (Staff tạo đơn -> Manager duyệt -> cập nhật trạng thái).
  - Định nghĩa process variables tối thiểu: `orderId`, `initiatorId`, `actorRole`, `decision`, `reason`.
  - Chuẩn naming:
    - Process key: `orderApprovalProcess`
    - Task key: `reviewOrderTask`, `applyApprovalTask`
  - Chuẩn logging: luôn log `processInstanceId`, `businessKey`, `taskDefinitionKey`.
- **Definition of Done:**
  - Start process từ API thành công.
  - Hoàn thành user task trên UI/API và thấy `OrderStatus` đổi đúng.
  - Có tài liệu ngắn cho cách chạy local.

### Sprint 2 (Tuần 2): Business-hardening + Security + Error handling
- **Mục tiêu:** Flow chạy an toàn trong nghiệp vụ thực tế (retry, validation, phân quyền).
- **Deliverables:**
  - Tách rõ `UserTask` và `ServiceTask` theo role.
  - Phân quyền claim/complete task theo `ADMIN/MANAGER/STAFF`.
  - Cơ chế xử lý lỗi nghiệp vụ + retry kỹ thuật.
- **Công việc chi tiết:**
  - Áp dụng idempotency cho các delegate/service task (theo `orderId`).
  - Mapping exception domain (`EntityNotFoundException`, business exception) sang BPMN error.
  - Thêm timeout/timer cho user task (quá hạn escalte/escalate).
  - Kiểm soát duplicate process theo business key.
- **Definition of Done:**
  - Không tạo trùng process cho cùng 1 đơn hàng khi gọi lặp.
  - Role sai không thể complete task.
  - Luồng lỗi nghiệp vụ đi đúng nhánh BPMN error.

### Sprint 3 (Tuần 3): Mở rộng voucher workflow + observability
- **Mục tiêu:** Áp dụng pattern đã ổn định sang Voucher và bổ sung monitoring.
- **Deliverables:**
  - Flow thứ 2: `voucher-approval` hoặc `voucher-revalidation`.
  - Dashboard/truy vết cơ bản: tra theo `businessKey`, trạng thái process/task.
  - Audit log nghiệp vụ liên kết giữa entity và process.
- **Công việc chi tiết:**
  - Dùng lại orchestration pattern cho `VoucherUpdateServiceImpl`.
  - Chuẩn hóa event log: `PROCESS_STARTED`, `TASK_COMPLETED`, `PROCESS_FAILED`.
  - Thêm endpoint query process state cho backoffice.
- **Definition of Done:**
  - Voucher flow chạy e2e tương tự order flow.
  - Truy ra được toàn bộ timeline theo `voucherId/orderId`.

### Sprint 4 (Tuần 4): Production readiness + test + rollout
- **Mục tiêu:** Đưa vào môi trường thật an toàn, có kiểm soát release.
- **Deliverables:**
  - Test plan đầy đủ (unit + integration + UAT checklist).
  - Runbook vận hành: incident, retry, dead-letter/manual resolution.
  - Feature toggle để bật/tắt workflow theo domain.
- **Công việc chi tiết:**
  - Tạo integration test cho critical paths (approve/reject/error/timeout).
  - Benchmark nhẹ (concurrency vừa phải, ví dụ 50-100 instance).
  - Rollout theo pha: nội bộ -> một nhóm cửa hàng -> toàn hệ thống.
- **Definition of Done:**
  - Tất cả test critical pass.
  - Có quy trình rollback rõ ràng.
  - Go-live được ký duyệt với checklist vận hành.

## 2) Skeleton package/code structure đề xuất

```text
src/main/java/com/nthn/storevision
├── workflow
│   ├── config
│   │   └── FlowableEngineConfig.java
│   ├── constant
│   │   ├── WorkflowProcessKey.java
│   │   └── WorkflowVariableKey.java
│   ├── facade
│   │   ├── OrderWorkflowFacade.java
│   │   └── VoucherWorkflowFacade.java
│   ├── delegate
│   │   ├── order
│   │   │   ├── ApplyOrderApprovalDelegate.java
│   │   │   └── RejectOrderDelegate.java
│   │   └── voucher
│   │       ├── ValidateVoucherDelegate.java
│   │       └── ApplyVoucherDecisionDelegate.java
│   ├── listener
│   │   ├── ProcessAuditEventListener.java
│   │   └── TaskAuditEventListener.java
│   ├── model
│   │   ├── StartOrderWorkflowCommand.java
│   │   ├── CompleteTaskCommand.java
│   │   └── WorkflowTaskView.java
│   ├── service
│   │   ├── WorkflowQueryService.java
│   │   ├── WorkflowCommandService.java
│   │   └── impl
│   │       ├── WorkflowQueryServiceImpl.java
│   │       └── WorkflowCommandServiceImpl.java
│   └── exception
│       ├── WorkflowAccessDeniedException.java
│       └── WorkflowBusinessException.java

src/main/resources
└── processes
    ├── order-approval.bpmn20.xml
    └── voucher-approval.bpmn20.xml

src/test/java/com/nthn/storevision
└── workflow
    ├── order
    │   └── OrderApprovalProcessIntegrationTest.java
    └── voucher
        └── VoucherApprovalProcessIntegrationTest.java
```

## 3) Mapping vào service hiện có
- `OrderWorkflowFacade` chỉ làm orchestration:
  - Start process khi tạo/yêu cầu duyệt đơn.
  - Complete task khi manager duyệt/từ chối.
- Delegate gọi lại service lõi hiện có:
  - `ApplyOrderApprovalDelegate` -> `OrderUpdateService` để đổi `OrderStatus`.
  - `ApplyVoucherDecisionDelegate` -> `VoucherUpdateService` để cập nhật voucher.
- Nguyên tắc: service domain giữ business logic; Flowable chỉ điều phối state machine.

## 4) Khung API tối thiểu
- `POST /api/v1/workflows/orders/{orderId}/start`
- `POST /api/v1/workflows/tasks/{taskId}/complete`
- `GET /api/v1/workflows/orders/{orderId}/status`
- `GET /api/v1/workflows/tasks?assignee=...&role=...`

## 5) Chuẩn kỹ thuật nên chốt sớm
- Dùng `businessKey = domainId` (`orderId`, `voucherId`) để truy vết nhất quán.
- Mỗi delegate phải idempotent để an toàn khi retry.
- Tách lỗi kỹ thuật và lỗi nghiệp vụ (BPMN Error cho nghiệp vụ).
- Log/audit bắt buộc có: `processInstanceId`, `businessKey`, `taskKey`, `actor`.