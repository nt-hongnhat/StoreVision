/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderItemDeletionServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04
 */

package com.nthn.storevision.service.orderitem.impl;

import com.nthn.storevision.entity.OrderItemEntity;
import com.nthn.storevision.repository.OrderItemRepository;
import com.nthn.storevision.service.common.AbstractDeletable;
import com.nthn.storevision.service.orderitem.OrderItemDeletionService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link OrderItemDeletionService} that soft-deletes
 * {@link OrderItemEntity} instances by setting the {@code isDeleted} flag, which triggers
 * {@code CustomAuditingEntityListener} to populate {@code deletedBy} and {@code deletedDate}.
 */
@Service
public class OrderItemDeletionServiceImpl
    extends AbstractDeletable<OrderItemEntity, Long, OrderItemRepository>
    implements OrderItemDeletionService {

  protected OrderItemDeletionServiceImpl(final OrderItemRepository repository) {
    super(repository);
  }
}
