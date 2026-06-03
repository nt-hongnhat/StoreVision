/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: 003-create-customers-table.sql
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 15:33:35
 */

DO
$$
    BEGIN
        CREATE TABLE customers
        (
            id               BIGSERIAL      NOT NULL,
            name             VARCHAR(255)   NOT NULL,
            email            VARCHAR(255)   NOT NULL,
            phone            VARCHAR(20),
            address          TEXT,
            loyalty_tier     VARCHAR(20)    NOT NULL DEFAULT 'BRONZE',
            loyalty_points   BIGINT         NOT NULL DEFAULT 0,
            total_spending   NUMERIC(18, 2) NOT NULL DEFAULT 0,
            date_of_birth    DATE,
            created_at       TIMESTAMP      NOT NULL,
            last_modified_at TIMESTAMP      NOT NULL,
            created_by       BIGINT         NOT NULL,
            last_modified_by BIGINT         NOT NULL,
            deleted_by       BIGINT,
            deleted_at       TIMESTAMP,
            CONSTRAINT pk_customers PRIMARY KEY (id),
            CONSTRAINT uq_customers_email UNIQUE (email),
            CONSTRAINT fk_customers_created_by FOREIGN KEY (created_by) REFERENCES users (id),
            CONSTRAINT fk_customers_last_modified_by FOREIGN KEY (last_modified_by) REFERENCES users (id),
            CONSTRAINT fk_customers_deleted_by FOREIGN KEY (deleted_by) REFERENCES users (id),
            CONSTRAINT chk_customers_loyalty_tier CHECK (loyalty_tier IN ('BRONZE', 'SILVER', 'GOLD')),
            CONSTRAINT chk_customers_loyalty_points CHECK (loyalty_points >= 0),
            CONSTRAINT chk_customers_total_spending CHECK (total_spending >= 0)
        );
    END
$$;
