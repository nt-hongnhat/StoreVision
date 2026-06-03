/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: 006-create-orders-table.sql
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 15:33:35
 */

DO
$$
    BEGIN
        CREATE TABLE orders
        (
            id               BIGSERIAL      NOT NULL,
            customer_id      BIGINT         NOT NULL,
            order_date       TIMESTAMP      NOT NULL,
            status           VARCHAR(20)    NOT NULL,
            subtotal         NUMERIC(15, 2) NOT NULL,
            discount_amount  NUMERIC(15, 2) NOT NULL DEFAULT 0,
            tax_amount       NUMERIC(15, 2) NOT NULL DEFAULT 0,
            total_amount     NUMERIC(15, 2) NOT NULL,
            voucher_id       BIGINT,
            channel          VARCHAR(20)    NOT NULL,
            note             TEXT,
            created_at       TIMESTAMP      NOT NULL,
            last_modified_at TIMESTAMP      NOT NULL,
            created_by       BIGINT         NOT NULL,
            last_modified_by BIGINT         NOT NULL,
            deleted_by       BIGINT,
            deleted_at       TIMESTAMP,
            CONSTRAINT pk_orders PRIMARY KEY (id),
            CONSTRAINT fk_orders_customer_id FOREIGN KEY (customer_id) REFERENCES customers (id),
            CONSTRAINT fk_orders_voucher_id FOREIGN KEY (voucher_id) REFERENCES vouchers (id),
            CONSTRAINT fk_orders_created_by FOREIGN KEY (created_by) REFERENCES users (id),
            CONSTRAINT fk_orders_last_modified_by FOREIGN KEY (last_modified_by) REFERENCES users (id),
            CONSTRAINT fk_orders_deleted_by FOREIGN KEY (deleted_by) REFERENCES users (id),
            CONSTRAINT chk_orders_status CHECK (status IN ('PENDING', 'COMPLETED', 'CANCELLED')),
            CONSTRAINT chk_orders_channel CHECK (channel IN ('POS', 'ONLINE')),
            CONSTRAINT chk_orders_subtotal CHECK (subtotal >= 0),
            CONSTRAINT chk_orders_discount_amount CHECK (discount_amount >= 0),
            CONSTRAINT chk_orders_tax_amount CHECK (tax_amount >= 0),
            CONSTRAINT chk_orders_total_amount CHECK (total_amount >= 0)
        );

        CREATE INDEX idx_orders_customer ON orders (customer_id);
        CREATE INDEX idx_orders_status ON orders (status);
        CREATE INDEX idx_orders_date ON orders (order_date DESC);
        CREATE INDEX idx_orders_analytics ON orders (order_date, status, total_amount) WHERE deleted_at IS NULL;
    END
$$;
