/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderDeletionServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04
 */

package com.nthn.storevision.service.order.impl;

import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.repository.OrderRepository;
import com.nthn.storevision.service.common.AbstractDeletable;
import com.nthn.storevision.service.order.OrderDeletionService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link OrderDeletionService} that soft-deletes {@link OrderEntity}
 * instances by setting the {@code isDeleted} flag, which triggers
 * {@code CustomAuditingEntityListener} to populate {@code deletedBy} and {@code deletedDate}.
 */
@Service
public class OrderDeletionServiceImpl
    extends AbstractDeletable<OrderEntity, Long, OrderRepository>
    implements OrderDeletionService {

  protected OrderDeletionServiceImpl(final OrderRepository repository) {
    super(repository);
  }
}
