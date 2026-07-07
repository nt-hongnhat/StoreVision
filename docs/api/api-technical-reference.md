# StoreVision API Technical Reference (v1)

## 1. Overview
- Base URL: `/api/v1`
- This document indexes **all main StoreVision features** through endpoint-level API documents.
- Request payload validation is based on Bean Validation annotations in DTOs (for example: `@Valid`, `@NotNull`, `@Size`).
- Response status codes and error handling follow controller annotations and global exception handling (`GlobalExceptionHandler`).

## 2. Main Features and API Coverage

### 2.1 Customer Management
- Purpose: manage customer profile, loyalty status, and customer order history.
- API docs:
  - [GET /customers](./customer/GET_customers.md)
  - [GET /customers/{id}](./customer/GET_customers_id.md)
  - [POST /customers](./customer/POST_customers.md)
  - [PUT /customers/{id}](./customer/PUT_customers_id.md)
  - [DELETE /customers/{id}](./customer/DELETE_customers_id.md)
  - [GET /customers/{id}/loyalty](./customer/GET_customers_id_loyalty.md)
  - [POST /customers/{id}/loyalty/redeem](./customer/POST_customers_id_loyalty_redeem.md)
  - [GET /customers/{id}/orders](./customer/GET_customers_id_orders.md)

### 2.2 Product Catalog Management
- Purpose: manage product lifecycle and product status updates.
- API docs:
  - [GET /products](./product/GET_products.md)
  - [GET /products/{id}](./product/GET_products_id.md)
  - [POST /products](./product/POST_products.md)
  - [PUT /products/{id}](./product/PUT_products_id.md)
  - [PATCH /products/{id}](./product/PATCH_products_id.md)
  - [DELETE /products/{id}](./product/DELETE_products_id.md)

### 2.3 Inventory Control
- Purpose: track stock levels, receipts, adjustments, and movement history.
- API docs:
  - [GET /inventory](./inventory/GET_inventory.md)
  - [GET /inventory/{productId}](./inventory/GET_inventory_productId.md)
  - [GET /inventory/{productId}/movements](./inventory/GET_inventory_productId_movements.md)
  - [GET /inventory/receipts](./inventory/GET_inventory_receipts.md)
  - [POST /inventory/receipts](./inventory/POST_inventory_receipts.md)
  - [POST /inventory/adjustments](./inventory/POST_inventory_adjustments.md)

### 2.4 Order Processing
- Purpose: manage order creation and lifecycle updates.
- API docs:
  - [GET /orders](./order/GET_orders.md)
  - [GET /orders/{id}](./order/GET_orders_id.md)
  - [POST /orders](./order/POST_orders.md)
  - [PUT /orders/{id}](./order/PUT_orders_id.md)
  - [PATCH /orders/{id}](./order/PATCH_orders_id.md)
  - [DELETE /orders/{id}](./order/DELETE_orders_id.md)

### 2.5 Order Item Management
- Purpose: manage line items linked to customer orders.
- API docs:
  - [GET /order-items](./order-item/GET_order-items.md)
  - [GET /order-items/{id}](./order-item/GET_order-items_id.md)
  - [POST /order-items](./order-item/POST_order-items.md)
  - [PUT /order-items/{id}](./order-item/PUT_order-items_id.md)
  - [DELETE /order-items/{id}](./order-item/DELETE_order-items_id.md)

### 2.6 Voucher & Promotion Management
- Purpose: manage voucher definitions and voucher status updates.
- API docs:
  - [GET /vouchers](./voucher/GET_vouchers.md)
  - [GET /vouchers/{id}](./voucher/GET_vouchers_id.md)
  - [POST /vouchers](./voucher/POST_vouchers.md)
  - [PUT /vouchers/{id}](./voucher/PUT_vouchers_id.md)
  - [PATCH /vouchers/{id}](./voucher/PATCH_vouchers_id.md)
  - [DELETE /vouchers/{id}](./voucher/DELETE_vouchers_id.md)

### 2.7 Employee Management
- Purpose: manage employee records and updates.
- API docs:
  - [GET /employees](./employee/GET_employees.md)
  - [GET /employees/{id}](./employee/GET_employees_id.md)
  - [POST /employees](./employee/POST_employees.md)
  - [PUT /employees/{id}](./employee/PUT_employees_id.md)
  - [DELETE /employees/{id}](./employee/DELETE_employees_id.md)

### 2.8 Notifications
- Purpose: read notifications, stream real-time notifications, and update notification state.
- API docs:
  - [GET /notifications](./notification/GET_notifications.md)
  - [GET /notifications/stream](./notification/GET_notifications_stream.md)
  - [PATCH /notifications/{id}](./notification/PATCH_notifications_id.md)

### 2.9 Analytics & Reporting
- Purpose: expose read-only business intelligence endpoints for operational insights.
- API docs:
  - [GET /analytics/revenue](./analytics/GET_analytics_revenue.md)
  - [GET /analytics/profit](./analytics/GET_analytics_profit.md)
  - [GET /analytics/products/top-selling](./analytics/GET_analytics_products_top-selling.md)
  - [GET /analytics/products/slow-moving](./analytics/GET_analytics_products_slow-moving.md)
  - [GET /analytics/orders/summary](./analytics/GET_analytics_orders_summary.md)
  - [GET /analytics/customers/top-spending](./analytics/GET_analytics_customers_top-spending.md)
  - [GET /analytics/inventory/valuation](./analytics/GET_analytics_inventory_valuation.md)

## 3. Endpoint Document Convention
- File location: `docs/api/<category>/`
- File naming: `<METHOD>_<path_slug>.md` (example: `GET_customers.md`, `DELETE_customers_id.md`)
- Each endpoint document follows the same structure:
  - `Overview`
  - `Endpoint`
  - `Request`
  - `Response`
  - `Notes`

## 4. Technical Notes
- OpenAPI annotations are used at controller level (`@Tag`, `@Operation`, `@ApiResponse`) and can be exposed through `/swagger-ui/**` and `/v3/api-docs/**`.
- Controllers mainly orchestrate request/response flow; business rules and side effects are implemented in service layer.
- Authorization requirements are documented per endpoint from `@PreAuthorize` rules when present.