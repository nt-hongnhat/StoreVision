# order-phase-1-policy-extraction – Order Phase 1 Policy and Transition Refactor

## Motivation

The refactor addresses concentrated business rules in order orchestration by extracting discount and validation rules into dedicated components and by moving status-transition branching out of the controller layer.
Behavior **does not change**: existing order creation calculations, stock checks, voucher validation flow, and status transition outcomes remain the same.

## Scope

- In scope:
  - `src/main/java/com/nthn/storevision/service/order/impl/OrderCreationServiceImpl.java`
  - `src/main/java/com/nthn/storevision/controller/v1/OrderControllerV1.java`
  - `src/main/java/com/nthn/storevision/service/order/OrderUpdateService.java`
  - `src/main/java/com/nthn/storevision/service/order/impl/OrderUpdateServiceImpl.java`
  - New extracted discount-policy Spring beans in `component/order/discount` (Strategy family) and stateless validation helpers in `util`.
- Out of scope:
  - API contract changes
  - Database schema or migration changes
  - Business rule changes for pricing, stock sufficiency, or state transition validity.

## Before / After

| Area                                | Before                                                                                    | After                                                                                            |
|-------------------------------------|-------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------|
| Discount decision in order creation | Inline methods and hardcoded `max(voucher, birthday)` logic in `OrderCreationServiceImpl` | Strategy-based discount policies (`DiscountPolicy`) are injected and aggregated in orchestration |
| Voucher eligibility check           | Inline null/blank check in order service                                                  | `VoucherCodeUtil` (stateless helper) handles the presence predicate                             |
| Stock sufficiency check at creation | Inline stock check implementation in order service                                        | `StockAvailabilityUtil` (stateless helper) encapsulates stock validation                        |
| Order status transition endpoint    | Controller contains `switch` and status-specific dispatch                                 | Controller delegates to service; transition branching centralized in `OrderUpdateServiceImpl`    |

## Changes Made

- `src/main/java/com/nthn/storevision/component/order/discount/DiscountPolicy.java`
  - Added strategy contract for order discount calculation.
- `src/main/java/com/nthn/storevision/component/order/discount/impl/BirthdayDiscountPolicy.java`
  - Extracted birthday loyalty-tier discount logic from order creation service.
- `src/main/java/com/nthn/storevision/component/order/discount/impl/VoucherDiscountPolicy.java`
  - Extracted voucher discount logic (percentage/fixed with cap) from order creation service.
- `src/main/java/com/nthn/storevision/util/VoucherCodeUtil.java`
  - Added stateless helper for the voucher-code presence predicate (no Spring bean, no instance state).
- `src/main/java/com/nthn/storevision/util/StockAvailabilityUtil.java`
  - Added stateless helper for aggregated order-item stock sufficiency validation.
- `src/main/java/com/nthn/storevision/service/order/impl/OrderCreationServiceImpl.java`
  - Replaced inline discount and stock/voucher checks with extracted strategy/specification components.
- `src/main/java/com/nthn/storevision/service/order/OrderUpdateService.java`
  - Added `transitionStatus(orderId, targetStatus)` entry point for status transition orchestration.
- `src/main/java/com/nthn/storevision/service/order/impl/OrderUpdateServiceImpl.java`
  - Added centralized status-transition dispatch (`COMPLETED`, `CANCELLED`, reject `PENDING`).
- `src/main/java/com/nthn/storevision/controller/v1/OrderControllerV1.java`
  - Removed controller-layer `switch` and delegated status transition to service.

## Risk & Compatibility

- Risks:
  - Injection order of multiple `DiscountPolicy` beans could affect behavior if aggregation rule changes in future.
  - Transactional boundary should remain monitored for service-dispatch flow.
- Compatibility:
  - No endpoint path/method changes.
  - No DTO or response contract changes.
  - No database change.
- Rollback:
  - Revert newly added policy/specification classes and restore inline logic in affected order service/controller classes.

## Testing

- Attempted to run: `src/test/java/com/nthn/storevision/service/order/impl/OrderCreationServiceImplTest.java`.
- Current test execution is blocked by existing unrelated compile errors in `AnalyticsRetrievalServiceImplTest` (invalid imports), not by the refactor changes in this scope.
- Local file-level lint checks on modified order service/controller files report no syntax errors for the main refactor path.
