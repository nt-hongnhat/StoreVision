/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: 004-create-products-table-rollback.sql
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 15:37:56
 */

DO
$$
    BEGIN
        DROP INDEX IF EXISTS idx_products_stock;
        DROP INDEX IF EXISTS idx_products_name_category;
        DROP INDEX IF EXISTS idx_products_category;
        DROP TABLE IF EXISTS products;
    END
$$;
