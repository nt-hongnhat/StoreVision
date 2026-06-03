/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: 009-create-loyalty-point-histories-table-rollback.sql
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 15:37:56
 */

DO
$$
    BEGIN
        DROP INDEX IF EXISTS idx_loyalty_transaction_date;
        DROP INDEX IF EXISTS idx_loyalty_customer_id;
        DROP TABLE IF EXISTS loyalty_point_histories;
    END
$$;
