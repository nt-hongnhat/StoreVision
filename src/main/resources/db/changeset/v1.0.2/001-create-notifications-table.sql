/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: 001-create-notifications-table.sql
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 15:00:00
 */

DO
$$
    BEGIN
        CREATE TABLE notifications
        (
            id               BIGSERIAL    NOT NULL,
            type             VARCHAR(30)  NOT NULL,
            title            VARCHAR(255) NOT NULL,
            message          VARCHAR(500) NOT NULL,
            reference_id     BIGINT,
            is_read          BOOLEAN      NOT NULL DEFAULT FALSE,
            created_at       TIMESTAMP    NOT NULL,
            last_modified_at TIMESTAMP    NOT NULL,
            created_by       BIGINT       NOT NULL,
            last_modified_by BIGINT       NOT NULL,
            deleted_by       BIGINT,
            deleted_at       TIMESTAMP,
            CONSTRAINT pk_notifications PRIMARY KEY (id),
            CONSTRAINT fk_notifications_created_by FOREIGN KEY (created_by) REFERENCES users (id),
            CONSTRAINT fk_notifications_last_modified_by FOREIGN KEY (last_modified_by) REFERENCES users (id),
            CONSTRAINT fk_notifications_deleted_by FOREIGN KEY (deleted_by) REFERENCES users (id),
            CONSTRAINT chk_notifications_type CHECK (type IN ('LOW_STOCK_ALERT'))
        );
    END
$$;
