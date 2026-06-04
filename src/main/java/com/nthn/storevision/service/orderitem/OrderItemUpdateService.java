/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderItemUpdateService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 16:08:27
 */

package com.nthn.storevision.service.orderitem;

import com.nthn.storevision.dto.OrderItemDTO;
import com.nthn.storevision.service.generic.Updatable;

/**
 * Service interface for updating order items.
 *
 * @see Updatable
 */
@FunctionalInterface
public interface OrderItemUpdateService extends Updatable<OrderItemDTO, Long> {}
