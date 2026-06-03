/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: 004-create-products-table.sql
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 15:33:35
 */

DO
$$
    BEGIN
        CREATE TABLE products
        (
            id               BIGSERIAL      NOT NULL,
            name             VARCHAR(255)   NOT NULL,
            category         VARCHAR(100),
            sku              VARCHAR(100),
            price            NUMERIC(15, 2) NOT NULL,
            stock_quantity   INTEGER        NOT NULL DEFAULT 0,
            description      TEXT,
            attributes       JSONB,
            created_at       TIMESTAMP      NOT NULL,
            last_modified_at TIMESTAMP      NOT NULL,
            created_by       BIGINT         NOT NULL,
            last_modified_by BIGINT         NOT NULL,
            deleted_by       BIGINT,
            deleted_at       TIMESTAMP,
            CONSTRAINT pk_products PRIMARY KEY (id),
            CONSTRAINT uq_products_sku UNIQUE (sku),
            CONSTRAINT fk_products_created_by FOREIGN KEY (created_by) REFERENCES users (id),
            CONSTRAINT fk_products_last_modified_by FOREIGN KEY (last_modified_by) REFERENCES users (id),
            CONSTRAINT fk_products_deleted_by FOREIGN KEY (deleted_by) REFERENCES users (id),
            CONSTRAINT chk_products_price CHECK (price > 0),
            CONSTRAINT chk_products_stock_quantity CHECK (stock_quantity >= 0)
        );

        CREATE INDEX idx_products_category ON products (category) WHERE deleted_at IS NULL;
        CREATE INDEX idx_products_name_category ON products (name, category) WHERE deleted_at IS NULL;
        CREATE INDEX idx_products_stock ON products (stock_quantity) WHERE deleted_at IS NULL;
    END
$$;
