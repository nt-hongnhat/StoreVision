/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: 008-create-stock-movements-table-rollback.sql
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 15:37:56
 */

DO
$$
    BEGIN
        DROP INDEX IF EXISTS idx_stock_movements_movement_date;
        DROP INDEX IF EXISTS idx_stock_movements_product_id;
        DROP TABLE IF EXISTS stock_movements;
    END
$$;
