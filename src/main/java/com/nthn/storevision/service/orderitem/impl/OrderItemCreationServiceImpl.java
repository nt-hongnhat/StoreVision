package com.nthn.storevision.service.orderitem.impl;

import com.nthn.storevision.entity.OrderItemEntity;
import com.nthn.storevision.repository.OrderItemRepository;
import com.nthn.storevision.service.common.AbstractCreatable;
import com.nthn.storevision.service.orderitem.OrderItemCreationService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link OrderItemCreationService} that extends {@link AbstractCreatable}
 * to persist {@link OrderItemEntity} instances via {@link OrderItemRepository}.
 */
@Service
public class OrderItemCreationServiceImpl
    extends AbstractCreatable<OrderItemEntity, Long, OrderItemEntity>
    implements OrderItemCreationService {

  public OrderItemCreationServiceImpl(final OrderItemRepository repository) {
    super(repository::save);
  }
}
