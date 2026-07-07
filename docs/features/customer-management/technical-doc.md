# CUSTOMER-MANAGEMENT – Customer Management API Coverage

## Overview
This document describes the **Customer Management** feature scope in StoreVision, focused on customer profile lifecycle, loyalty operations, and customer order history exposure through REST APIs.

## Background
The feature-level documentation is introduced to provide a single technical entry point per feature and to align with the project documentation convention for `docs/features/.../technical-doc.md`.

## Technical Design
### Architecture / Data Flow
- Client calls customer endpoints under `/api/v1/customers`.
- Controller and service flow details are documented at endpoint level in `docs/api/customer/*.md`.
- Responses follow the project-wide API response/error handling conventions documented in `docs/api/api-technical-reference.md`.

### Component Changes

| Full Path                                               | Change Role                                                          |
|---------------------------------------------------------|----------------------------------------------------------------------|
| `docs/api/api-technical-reference.md`                   | Feature index includes Customer Management scope and endpoint links. |
| `docs/api/customer/GET_customers.md`                    | Customer listing endpoint technical contract.                        |
| `docs/api/customer/GET_customers_id.md`                 | Customer detail endpoint technical contract.                         |
| `docs/api/customer/POST_customers.md`                   | Customer creation endpoint technical contract.                       |
| `docs/api/customer/PUT_customers_id.md`                 | Customer update endpoint technical contract.                         |
| `docs/api/customer/DELETE_customers_id.md`              | Customer deletion endpoint technical contract.                       |
| `docs/api/customer/GET_customers_id_loyalty.md`         | Loyalty retrieval endpoint contract.                                 |
| `docs/api/customer/POST_customers_id_loyalty_redeem.md` | Loyalty redemption endpoint contract.                                |
| `docs/api/customer/GET_customers_id_orders.md`          | Customer order history endpoint contract.                            |

## API Changes
- No new REST endpoints were introduced in this documentation batch.
- Documented Customer Management endpoint set:
  - `GET /api/v1/customers`
  - `GET /api/v1/customers/{id}`
  - `POST /api/v1/customers`
  - `PUT /api/v1/customers/{id}`
  - `DELETE /api/v1/customers/{id}`
  - `GET /api/v1/customers/{id}/loyalty`
  - `POST /api/v1/customers/{id}/loyalty/redeem`
  - `GET /api/v1/customers/{id}/orders`

## Database Changes
No database schema or migration changes were introduced in this documentation batch.

## Implementation Notes
- This feature document is derived from existing endpoint-level docs and the API technical reference index.
- Endpoint-level request/response payload details remain the source of truth in `docs/api/customer/*.md`.

## Testing
- No unit/integration test files were changed in this documentation-only update.
- Validation is based on link and scope consistency with:
  - `docs/api/api-technical-reference.md`
  - `docs/api/customer/*.md`

## Rollout
- No feature flag required.
- No phased rollout required.
- Backward compatibility is preserved because no runtime behavior or API contract changed.
- No additional environment configuration required.
