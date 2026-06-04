/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderUpdateService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 16:12:23
 */

package com.nthn.storevision.service.order;

import com.nthn.storevision.dto.OrderDTO;
import com.nthn.storevision.service.generic.Updatable;

/**
 * Service interface for updating orders.
 *
 * @see Updatable
 */
@FunctionalInterface
public interface OrderUpdateService extends Updatable<OrderDTO, Long> {}
