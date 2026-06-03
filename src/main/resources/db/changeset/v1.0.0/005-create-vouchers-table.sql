/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: 005-create-vouchers-table.sql
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 15:33:35
 */

DO
$$
    BEGIN
        CREATE TABLE vouchers
        (
            id                  BIGSERIAL      NOT NULL,
            code                VARCHAR(50)    NOT NULL,
            discount_type       VARCHAR(20)    NOT NULL,
            discount_value      NUMERIC(10, 2) NOT NULL,
            max_discount_amount NUMERIC(15, 2),
            min_order_amount    NUMERIC(15, 2),
            start_date          DATE           NOT NULL,
            expiry_date         DATE           NOT NULL,
            max_usage           INTEGER        NOT NULL,
            usage_count         INTEGER        NOT NULL DEFAULT 0,
            is_active           BOOLEAN        NOT NULL DEFAULT TRUE,
            created_at          TIMESTAMP      NOT NULL,
            last_modified_at    TIMESTAMP      NOT NULL,
            created_by          BIGINT         NOT NULL,
            last_modified_by    BIGINT         NOT NULL,
            deleted_by          BIGINT,
            deleted_at          TIMESTAMP,
            CONSTRAINT pk_vouchers PRIMARY KEY (id),
            CONSTRAINT uq_vouchers_code UNIQUE (code),
            CONSTRAINT fk_vouchers_created_by FOREIGN KEY (created_by) REFERENCES users (id),
            CONSTRAINT fk_vouchers_last_modified_by FOREIGN KEY (last_modified_by) REFERENCES users (id),
            CONSTRAINT fk_vouchers_deleted_by FOREIGN KEY (deleted_by) REFERENCES users (id),
            CONSTRAINT chk_vouchers_discount_type CHECK (discount_type IN ('PERCENTAGE', 'FIXED_AMOUNT')),
            CONSTRAINT chk_vouchers_discount_value CHECK (discount_value > 0),
            CONSTRAINT chk_vouchers_max_usage CHECK (max_usage > 0),
            CONSTRAINT chk_vouchers_usage_count CHECK (usage_count >= 0),
            CONSTRAINT chk_vouchers_dates CHECK (expiry_date >= start_date)
        );
    END
$$;
