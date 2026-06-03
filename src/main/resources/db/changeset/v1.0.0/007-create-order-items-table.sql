/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: 007-create-order-items-table.sql
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 15:33:35
 */

DO
$$
    BEGIN
        CREATE TABLE order_items
        (
            id               BIGSERIAL      NOT NULL,
            order_id         BIGINT         NOT NULL,
            product_id       BIGINT         NOT NULL,
            quantity         INTEGER        NOT NULL,
            unit_price       NUMERIC(15, 2) NOT NULL,
            created_at       TIMESTAMP      NOT NULL,
            last_modified_at TIMESTAMP      NOT NULL,
            created_by       BIGINT         NOT NULL,
            last_modified_by BIGINT         NOT NULL,
            deleted_by       BIGINT,
            deleted_at       TIMESTAMP,
            CONSTRAINT pk_order_items PRIMARY KEY (id),
            CONSTRAINT fk_order_items_order_id FOREIGN KEY (order_id) REFERENCES orders (id),
            CONSTRAINT fk_order_items_product_id FOREIGN KEY (product_id) REFERENCES products (id),
            CONSTRAINT fk_order_items_created_by FOREIGN KEY (created_by) REFERENCES users (id),
            CONSTRAINT fk_order_items_last_modified_by FOREIGN KEY (last_modified_by) REFERENCES users (id),
            CONSTRAINT fk_order_items_deleted_by FOREIGN KEY (deleted_by) REFERENCES users (id),
            CONSTRAINT chk_order_items_quantity CHECK (quantity >= 1),
            CONSTRAINT chk_order_items_unit_price CHECK (unit_price > 0)
        );

        CREATE INDEX idx_order_items_order_id ON order_items (order_id);
        CREATE INDEX idx_order_items_product_id ON order_items (product_id);
    END
$$;
