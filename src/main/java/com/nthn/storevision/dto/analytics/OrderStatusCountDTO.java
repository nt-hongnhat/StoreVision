/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderStatusCountDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.dto.analytics;

import com.nthn.storevision.constant.OrderStatus;

/**
 * Internal projection: number of orders per status within a date range. Used to assemble the
 * order summary report.
 *
 * @param status the order status being counted
 * @param count number of active orders in that status; never {@code null}
 */
public record OrderStatusCountDTO(OrderStatus status, Long count) {}
