/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderCreationService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 11:21:37
 */

package com.nthn.storevision.service.order;

import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.service.generic.Creatable;

/**
 * Service interface for creating and persisting {@link OrderEntity} entities.
 *
 * @see com.nthn.storevision.service.common.AbstractCreatable
 * @see Creatable
 */
public interface OrderCreationService extends Creatable<OrderEntity, Long> {}
