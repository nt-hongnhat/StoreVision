package com.nthn.storevision.service.orderitem;

import com.nthn.storevision.entity.OrderItemEntity;
import com.nthn.storevision.repository.OrderItemRepository;
import com.nthn.storevision.service.common.AbstractCreatable;
import com.nthn.storevision.service.generic.Creatable;
import org.springframework.stereotype.Service;

/**
 * Service for creating and persisting {@link OrderItemEntity} entities. This class extends {@link
 * AbstractCreatable} to provide a generic implementation of the {@link Creatable} interface for
 * {@link OrderItemEntity} entities. It uses a {@link OrderItemRepository} to manage database interactions.
 *
 * @see com.nthn.storevision.service.common.AbstractCreatable
 * @see com.nthn.storevision.service.generic.Creatable
 * @see OrderItemRepository
 */
@Service
public class OrderItemCreationService extends AbstractCreatable<OrderItemEntity, Long, OrderItemEntity>
    implements Creatable<OrderItemEntity, Long> {

  /**
   * Constructs a {@code OrderItemCreationService} with the specified repository for managing {@link
   * OrderItemEntity} instances.
   *
   * @param repository the repository used for persisting and managing {@link OrderItemEntity} instances
   */
  public OrderItemCreationService(final OrderItemRepository repository) {
    super(repository);
  }
}
