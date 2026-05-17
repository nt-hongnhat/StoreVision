package com.nthn.storevision.service.orderitem;

import com.nthn.storevision.entity.OrderItemEntity;
import com.nthn.storevision.repository.OrderItemRepository; // Changed from JpaOrderItemRepository
import com.nthn.storevision.service.generic.Updatable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for updating {@link OrderItemEntity} instances in the application.
 *
 * <p>This class implements the {@link Updatable} interface, providing functionality for persisting
 * updates to the {@link OrderItemEntity} objects in the underlying database. It uses a {@link
 * OrderItemRepository} for database access and is marked as a Spring {@link Service} to enable dependency
 * injection and manage its lifecycle. The {@code update} method is executed within a transactional
 * context, ensuring that changes are committed consistently.
 */
@Service
@RequiredArgsConstructor
public class OrderItemUpdateService implements Updatable<OrderItemEntity> {

  private final OrderItemRepository repository; // Changed from JpaOrderItemRepository

  /**
   * Updates the provided {@link OrderItemEntity} object in the database.
   *
   * @param orderItem The {@link OrderItemEntity} object to update.
   */
  @Transactional
  @Override
  public void update(final OrderItemEntity orderItem) {
    repository.save(orderItem);
  }
}
