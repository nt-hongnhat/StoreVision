package com.nthn.storevision.service.customer;

import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.repository.CustomerRepository;
import com.nthn.storevision.service.common.AbstractCreatable;
import com.nthn.storevision.service.generic.Creatable;
import org.springframework.stereotype.Service;

/**
 * Service for creating and persisting {@link CustomerEntity} entities. This class extends {@link
 * AbstractCreatable} to provide a generic implementation of the {@link Creatable} interface for
 * {@link CustomerEntity} entities. It uses a {@link CustomerRepository} to manage database interactions.
 *
 * @see com.nthn.storevision.service.common.AbstractCreatable
 * @see com.nthn.storevision.service.generic.Creatable
 * @see CustomerRepository
 */
@Service
public class CustomerCreationService extends AbstractCreatable<CustomerEntity, Long, CustomerEntity>
    implements Creatable<CustomerEntity, Long> {

  /**
   * Constructs a {@code CustomerCreationService} with the specified repository for managing {@link
   * CustomerEntity} instances.
   *
   * @param repository the repository used for persisting and managing {@link CustomerEntity} instances
   */
  public CustomerCreationService(final CustomerRepository repository) {
    super(repository);
  }
}
