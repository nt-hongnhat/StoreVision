package com.nthn.storevision.service.order;

import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.repository.OrderRepository;
import com.nthn.storevision.service.common.AbstractCreatable;
import com.nthn.storevision.service.generic.Creatable;
import org.springframework.stereotype.Service;

/**
 * Service for creating and persisting {@link OrderEntity} entities. This class extends {@link
 * AbstractCreatable} to provide a generic implementation of the {@link Creatable} interface for
 * {@link OrderEntity} entities. It uses a {@link OrderRepository} to manage database interactions.
 *
 * @see com.nthn.storevision.service.common.AbstractCreatable
 * @see com.nthn.storevision.service.generic.Creatable
 * @see OrderRepository
 */
@Service
public class OrderCreationService extends AbstractCreatable<OrderEntity, Long, OrderEntity>
    implements Creatable<OrderEntity, Long> {

  /**
   * Constructs a {@code OrderCreationService} with the specified repository for managing {@link
   * OrderEntity} instances.
   *
   * @param repository the repository used for persisting and managing {@link OrderEntity} instances
   */
  public OrderCreationService(final OrderRepository repository) {
    super(repository);
  }
}
