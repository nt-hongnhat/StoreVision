/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: 012-add-user-id-to-employees-table.sql
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 16:34:58
 */

DO
$$
    BEGIN
        ALTER TABLE employees
            ADD COLUMN user_id BIGINT;

        ALTER TABLE employees
            ADD CONSTRAINT fk_employees_users FOREIGN KEY (user_id) REFERENCES users (id);
    END
$$;