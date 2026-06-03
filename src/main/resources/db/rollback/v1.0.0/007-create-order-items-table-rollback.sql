/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: 007-create-order-items-table-rollback.sql
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 15:37:56
 */

DO
$$
    BEGIN
        DROP INDEX IF EXISTS idx_order_items_product_id;
        DROP INDEX IF EXISTS idx_order_items_order_id;
        DROP TABLE IF EXISTS order_items;
    END
$$;
