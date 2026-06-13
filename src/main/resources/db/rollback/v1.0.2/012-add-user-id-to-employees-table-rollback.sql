/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: 012-add-user-id-to-employees-table-rollback.sql
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 16:34:58
 */

DO
$$
    BEGIN
        ALTER TABLE employees
            DROP CONSTRAINT IF EXISTS fk_employees_users;

        ALTER TABLE employees
            DROP COLUMN IF EXISTS user_id;
    END
$$;