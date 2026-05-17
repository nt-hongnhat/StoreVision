package com.nthn.storevision.service.orderitem;

import com.nthn.storevision.entity.OrderItemEntity;
import com.nthn.storevision.repository.OrderItemRepository; // Changed from JpaOrderItemRepository
import com.nthn.storevision.service.generic.HardDeletable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for permanently deleting {@link OrderItemEntity} entities.
 * This class implements the {@link HardDeletable} interface.
 */
@Service
@RequiredArgsConstructor
public class OrderItemDeletionService implements HardDeletable<OrderItemEntity, Long> {

  private final OrderItemRepository repository; // Changed from JpaOrderItemRepository

  /**
   * Deletes an existing order item entity.
   *
   * @param entity The {@link OrderItemEntity} to be deleted.
   */
  @Override
  @Transactional
  public void delete(final OrderItemEntity entity) {
    repository.delete(entity);
  }

  /**
   * Permanently deletes an order item entity by its identifier.
   *
   * @param id The ID of the order item to be deleted.
   */
  @Override
  @Transactional
  public void permanentlyDelete(final Long id) {
    repository.deleteById(id);
  }
}
