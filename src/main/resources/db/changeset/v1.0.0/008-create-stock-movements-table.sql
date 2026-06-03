/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: 008-create-stock-movements-table.sql
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 15:33:35
 */

DO
$$
    BEGIN
        CREATE TABLE stock_movements
        (
            id                 BIGSERIAL      NOT NULL,
            product_id         BIGINT         NOT NULL,
            type               VARCHAR(20)    NOT NULL,
            quantity           INTEGER        NOT NULL,
            unit_cost          NUMERIC(15, 2),
            reference_order_id BIGINT,
            performed_by       BIGINT         NOT NULL,
            movement_date      TIMESTAMP      NOT NULL,
            note               TEXT,
            CONSTRAINT pk_stock_movements PRIMARY KEY (id),
            CONSTRAINT fk_stock_movements_product_id FOREIGN KEY (product_id) REFERENCES products (id),
            CONSTRAINT fk_stock_movements_reference_order_id FOREIGN KEY (reference_order_id) REFERENCES orders (id),
            CONSTRAINT fk_stock_movements_performed_by FOREIGN KEY (performed_by) REFERENCES users (id),
            CONSTRAINT chk_stock_movements_type CHECK (type IN ('RECEIPT', 'ISSUE', 'ADJUSTMENT')),
            CONSTRAINT chk_stock_movements_quantity CHECK (quantity > 0)
        );

        CREATE INDEX idx_stock_movements_product_id ON stock_movements (product_id);
        CREATE INDEX idx_stock_movements_movement_date ON stock_movements (movement_date DESC);
    END
$$;
