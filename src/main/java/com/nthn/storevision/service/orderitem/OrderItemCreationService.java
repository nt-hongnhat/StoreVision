/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: OrderItemCreationService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 22:32:10
 */

package com.nthn.storevision.service.orderitem;

import com.nthn.storevision.dto.order_item.OrderItemDTO;
import com.nthn.storevision.service.generic.Creatable;

/**
 * Service interface for creating and persisting order items.
 *
 * @see com.nthn.storevision.service.common.AbstractCreatable
 * @see Creatable
 */
@FunctionalInterface
public interface OrderItemCreationService extends Creatable<OrderItemDTO> {}
