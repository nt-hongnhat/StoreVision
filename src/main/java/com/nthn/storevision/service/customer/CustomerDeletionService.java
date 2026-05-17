package com.nthn.storevision.service.customer;

import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.repository.CustomerRepository;
import com.nthn.storevision.service.generic.HardDeletable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for permanently deleting {@link CustomerEntity} entities.
 * This class implements the {@link HardDeletable} interface.
 */
@Service
@RequiredArgsConstructor
public class CustomerDeletionService implements HardDeletable<CustomerEntity, Long> {

  private final CustomerRepository repository;

  /**
   * Deletes an existing customer entity.
   *
   * @param entity The {@link CustomerEntity} to be deleted.
   */
  @Override
  @Transactional
  public void delete(final CustomerEntity entity) {
    repository.delete(entity);
  }

  /**
   * Permanently deletes a customer entity by its identifier.
   *
   * @param id The ID of the customer to be deleted.
   */
  @Override
  @Transactional
  public void permanentlyDelete(final Long id) {
    repository.deleteById(id);
  }
}
