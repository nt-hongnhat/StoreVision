/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: 009-create-loyalty-point-histories-table.sql
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 15:33:35
 */

DO
$$
    BEGIN
        CREATE TABLE loyalty_point_histories
        (
            id                   BIGSERIAL   NOT NULL,
            customer_id          BIGINT      NOT NULL,
            type                 VARCHAR(20) NOT NULL,
            points               BIGINT      NOT NULL,
            balance_after        BIGINT      NOT NULL,
            reference_order_id   BIGINT,
            reference_voucher_id BIGINT,
            transaction_date     TIMESTAMP   NOT NULL,
            CONSTRAINT pk_loyalty_point_histories PRIMARY KEY (id),
            CONSTRAINT fk_loyalty_customer_id FOREIGN KEY (customer_id) REFERENCES customers (id),
            CONSTRAINT fk_loyalty_reference_order_id FOREIGN KEY (reference_order_id) REFERENCES orders (id),
            CONSTRAINT fk_loyalty_reference_voucher_id FOREIGN KEY (reference_voucher_id) REFERENCES vouchers (id),
            CONSTRAINT chk_loyalty_type CHECK (type IN ('EARN', 'REDEEM')),
            CONSTRAINT chk_loyalty_points CHECK (points > 0),
            CONSTRAINT chk_loyalty_balance_after CHECK (balance_after >= 0)
        );

        CREATE INDEX idx_loyalty_customer_id ON loyalty_point_histories (customer_id);
        CREATE INDEX idx_loyalty_transaction_date ON loyalty_point_histories (transaction_date DESC);
    END
$$;
