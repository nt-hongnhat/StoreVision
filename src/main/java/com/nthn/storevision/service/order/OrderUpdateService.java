package com.nthn.storevision.service.order;

import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.repository.OrderRepository;
import com.nthn.storevision.service.generic.Updatable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for updating {@link OrderEntity} instances in the application.
 *
 * <p>This class implements the {@link Updatable} interface, providing functionality for persisting
 * updates to the {@link OrderEntity} objects in the underlying database. It uses a {@link
 * OrderRepository} for database access and is marked as a Spring {@link Service} to enable dependency
 * injection and manage its lifecycle. The {@code update} method is executed within a transactional
 * context, ensuring that changes are committed consistently.
 */
@Service
@RequiredArgsConstructor
public class OrderUpdateService implements Updatable<OrderEntity> {

  private final OrderRepository repository;

  /**
   * Updates the provided {@link OrderEntity} object in the database.
   *
   * @param order The {@link OrderEntity} object to update.
   */
  @Transactional
  @Override
  public void update(final OrderEntity order) {
    repository.save(order);
  }
}
