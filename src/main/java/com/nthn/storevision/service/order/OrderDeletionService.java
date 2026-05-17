package com.nthn.storevision.service.order;

import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.repository.OrderRepository;
import com.nthn.storevision.service.generic.HardDeletable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for permanently deleting {@link OrderEntity} entities. This class implements the {@link
 * HardDeletable} interface.
 */
@Service
@RequiredArgsConstructor
public class OrderDeletionService implements HardDeletable<OrderEntity, Long> {

  private final OrderRepository repository;

  /**
   * Deletes an existing order entity.
   *
   * @param entity The {@link OrderEntity} to be deleted.
   */
  @Override
  @Transactional
  public void delete(final OrderEntity entity) {
    repository.delete(entity);
  }

  /**
   * Permanently deletes an order entity by its identifier.
   *
   * @param id The ID of the order to be deleted.
   */
  @Override
  @Transactional
  public void permanentlyDelete(final Long id) {
    repository.deleteById(id);
  }
}
