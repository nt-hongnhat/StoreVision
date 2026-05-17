package com.nthn.storevision.service.customer;

import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.repository.CustomerRepository;
import com.nthn.storevision.service.generic.Updatable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for updating {@link CustomerEntity} instances in the application.
 *
 * <p>This class implements the {@link Updatable} interface, providing functionality for persisting
 * updates to the {@link CustomerEntity} objects in the underlying database. It uses a {@link
 * CustomerRepository} for database access and is marked as a Spring {@link Service} to enable dependency
 * injection and manage its lifecycle. The {@code update} method is executed within a transactional
 * context, ensuring that changes are committed consistently.
 */
@Service
@RequiredArgsConstructor
public class CustomerUpdateService implements Updatable<CustomerEntity> {

  private final CustomerRepository repository;

  /**
   * Updates the provided {@link CustomerEntity} object in the database.
   *
   * @param customer The {@link CustomerEntity} object to update.
   */
  @Transactional
  @Override
  public void update(final CustomerEntity customer) {
    repository.save(customer);
  }
}
