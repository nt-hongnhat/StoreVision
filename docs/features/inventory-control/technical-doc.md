# INVENTORY-CONTROL – Inventory Control API Coverage

## Overview
This document describes the **Inventory Control** feature scope in StoreVision, covering stock visibility, receipt registration, adjustments, and product movement history.

## Background
The feature-level document consolidates inventory API capability in one place while retaining endpoint-level technical contract files.

## Technical Design
### Architecture / Data Flow
- Clients query or update inventory through `/api/v1/inventory` endpoints.
- Functional details are maintained in endpoint specs under `docs/api/inventory/*.md`.
- Standard API response/error conventions follow `docs/api/api-technical-reference.md`.

### Component Changes

| Full Path                                                 | Change Role                                                  |
|-----------------------------------------------------------|--------------------------------------------------------------|
| `docs/api/api-technical-reference.md`                     | Feature index includes Inventory Control and endpoint links. |
| `docs/api/inventory/GET_inventory.md`                     | Inventory listing endpoint contract.                         |
| `docs/api/inventory/GET_inventory_productId.md`           | Product-specific inventory endpoint contract.                |
| `docs/api/inventory/GET_inventory_productId_movements.md` | Inventory movement history endpoint contract.                |
| `docs/api/inventory/GET_inventory_receipts.md`            | Receipt listing endpoint contract.                           |
| `docs/api/inventory/POST_inventory_receipts.md`           | Inventory receipt creation endpoint contract.                |
| `docs/api/inventory/POST_inventory_adjustments.md`        | Inventory adjustment endpoint contract.                      |

## API Changes
- No new REST endpoints were introduced in this documentation batch.
- Documented Inventory endpoint set:
  - `GET /api/v1/inventory`
  - `GET /api/v1/inventory/{productId}`
  - `GET /api/v1/inventory/{productId}/movements`
  - `GET /api/v1/inventory/receipts`
  - `POST /api/v1/inventory/receipts`
  - `POST /api/v1/inventory/adjustments`

## Database Changes
No database schema or migration changes were introduced in this documentation batch.

## Implementation Notes
- Inventory behavior and payload-level details are defined in endpoint documents under `docs/api/inventory/`.
- This feature document serves as an aggregated technical entry point.

## Testing
- No unit/integration test files were changed in this documentation-only update.
- Validation is based on cross-checking endpoint links and names against `docs/api/inventory/*.md`.

## Rollout
- No feature flag is required.
- No phased rollout required.
- Backward compatibility is preserved because no runtime behavior or API contract changed.
- No additional environment configuration is required.
