/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: 002-create-employees-table.sql
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 15:30:18
 */
DO
$$
    BEGIN
        CREATE TABLE employees
        (
            id               BIGSERIAL    NOT NULL,
            username         VARCHAR(100) NOT NULL,
            password         VARCHAR(255) NOT NULL,
            role             VARCHAR(20)  NOT NULL,
            created_at       TIMESTAMP    NOT NULL,
            last_modified_at TIMESTAMP    NOT NULL,
            created_by       BIGINT       NOT NULL,
            last_modified_by BIGINT       NOT NULL,
            deleted_by       BIGINT,
            deleted_at       TIMESTAMP,
            CONSTRAINT pk_employees PRIMARY KEY (id),
            CONSTRAINT uq_employees_username UNIQUE (username),
            CONSTRAINT fk_employees_created_by FOREIGN KEY (created_by) REFERENCES users (id),
            CONSTRAINT fk_employees_last_modified_by FOREIGN KEY (last_modified_by) REFERENCES users (id),
            CONSTRAINT fk_employees_deleted_by FOREIGN KEY (deleted_by) REFERENCES users (id),
            CONSTRAINT chk_employees_role CHECK (role IN ('ADMIN', 'MANAGER', 'STAFF'))
        );
    END
$$

