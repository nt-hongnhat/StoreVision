/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderItemDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-17 14:19:08
 */

package com.nthn.storevision.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Data Transfer Object for OrderItem.
 * Used to transfer order item data between layers, often for API requests and responses.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {
    private Long id;

    @NotNull(message = "{orderitem.orderId.required}")
    private Long orderId;

    @NotNull(message = "{orderitem.productId.required}")
    private Long productId;

    @NotNull(message = "{orderitem.quantity.required}")
    @Min(value = 1, message = "{orderitem.quantity.min}")
    private Integer quantity;

    @NotNull(message = "{orderitem.unitPrice.required}")
    @Min(value = 0, message = "{orderitem.unitPrice.min}")
    private BigDecimal unitPrice;
}
