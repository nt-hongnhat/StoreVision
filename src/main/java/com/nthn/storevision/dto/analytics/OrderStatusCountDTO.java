/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: OrderStatusCountDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:52:00
 */

package com.nthn.storevision.dto.analytics;

import com.nthn.storevision.constant.order.OrderStatus;

/**
 * Internal projection: number of orders per status within a date range. Used to assemble the
 * order summary report.
 *
 * @param status the order status being counted
 * @param count number of active orders in that status; never {@code null}
 */
public record OrderStatusCountDTO(OrderStatus status, Long count) {}
