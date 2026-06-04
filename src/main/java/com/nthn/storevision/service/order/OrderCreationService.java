/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderCreationService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 14:12:00
 */

package com.nthn.storevision.service.order;

import com.nthn.storevision.dto.OrderDTO;
import com.nthn.storevision.service.generic.Creatable;

/**
 * Service interface for creating and persisting orders.
 *
 * @see com.nthn.storevision.service.common.AbstractCreatable
 * @see Creatable
 */
@FunctionalInterface
public interface OrderCreationService extends Creatable<OrderDTO> {}
