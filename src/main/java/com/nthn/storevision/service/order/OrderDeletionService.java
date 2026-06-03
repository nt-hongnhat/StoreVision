/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderDeletionService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 11:21:37
 */

package com.nthn.storevision.service.order;

import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.service.generic.HardDeletable;
import com.nthn.storevision.service.generic.SoftDeletable;

/**
 * Service interface for soft-deleting, restoring, and permanently deleting
 * {@link OrderEntity} entities.
 *
 * @see SoftDeletable
 * @see HardDeletable
 */
public interface OrderDeletionService
    extends SoftDeletable<OrderEntity, Long>, HardDeletable<OrderEntity, Long> {}
