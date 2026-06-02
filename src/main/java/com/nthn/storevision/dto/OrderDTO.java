/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-17 14:15:54
 */

package com.nthn.storevision.dto;

import com.nthn.storevision.constant.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Order.
 * Used to transfer order data between layers, often for API requests and responses.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Long id;

    @NotNull(message = "{order.orderDate.required}")
    private LocalDateTime orderDate;

    @NotNull(message = "{order.totalAmount.required}")
    private BigDecimal totalAmount;

    @NotNull(message = "{order.status.required}")
    private OrderStatus status;

    @NotNull(message = "{order.customerId.required}")
    private Long customerId;
}
