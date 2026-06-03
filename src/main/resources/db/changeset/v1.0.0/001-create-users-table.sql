/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: 001-create-users-table.sql
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 15:33:35
 */

DO
$$
    BEGIN
        CREATE TABLE users
        (
            id               BIGSERIAL    NOT NULL,
            email            VARCHAR(255) NOT NULL,
            name             VARCHAR(255) NOT NULL,
            avatar_url       TEXT,
            provider         VARCHAR(50)  NOT NULL,
            provider_id      VARCHAR(255),
            created_at       TIMESTAMP    NOT NULL,
            last_modified_at TIMESTAMP    NOT NULL,
            created_by       BIGINT       NOT NULL,
            last_modified_by BIGINT       NOT NULL,
            deleted_by       BIGINT,
            deleted_at       TIMESTAMP,
            CONSTRAINT pk_users PRIMARY KEY (id),
            CONSTRAINT uq_users_email UNIQUE (email),
            CONSTRAINT fk_users_created_by FOREIGN KEY (created_by) REFERENCES users (id),
            CONSTRAINT fk_users_last_modified_by FOREIGN KEY (last_modified_by) REFERENCES users (id),
            CONSTRAINT fk_users_deleted_by FOREIGN KEY (deleted_by) REFERENCES users (id)
        );
    END
$$;
