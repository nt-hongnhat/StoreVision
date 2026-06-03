package com.nthn.storevision.service.orderitem.impl;

import com.nthn.storevision.entity.OrderItemEntity;
import com.nthn.storevision.repository.OrderItemRepository;
import com.nthn.storevision.service.orderitem.OrderItemUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link OrderItemUpdateService} for persisting updates to
 * {@link OrderItemEntity} instances in the database.
 */
@Service
@RequiredArgsConstructor
public class OrderItemUpdateServiceImpl implements OrderItemUpdateService {

  private final OrderItemRepository repository;

  @Transactional
  @Override
  public void update(final OrderItemEntity orderItem) {
    repository.save(orderItem);
  }
}
