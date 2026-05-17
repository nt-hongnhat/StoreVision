package com.nthn.storevision.service.employee;

import com.nthn.storevision.entity.EmployeeEntity;
import com.nthn.storevision.repository.EmployeeRepository; // Changed from JpaEmployeeRepository
import com.nthn.storevision.service.common.AbstractCreatable;
import com.nthn.storevision.service.generic.Creatable;
import org.springframework.stereotype.Service;

/**
 * Service for creating and persisting {@link EmployeeEntity} entities. This class extends {@link
 * AbstractCreatable} to provide a generic implementation of the {@link Creatable} interface for
 * {@link EmployeeEntity} entities. It uses a {@link EmployeeRepository} to manage database interactions.
 *
 * @see com.nthn.storevision.service.common.AbstractCreatable
 * @see com.nthn.storevision.service.generic.Creatable
 * @see EmployeeRepository
 */
@Service
public class EmployeeCreationService extends AbstractCreatable<EmployeeEntity, Long, EmployeeEntity>
    implements Creatable<EmployeeEntity, Long> {

  /**
   * Constructs a {@code EmployeeCreationService} with the specified repository for managing {@link
   * EmployeeEntity} instances.
   *
   * @param repository the repository used for persisting and managing {@link EmployeeEntity} instances
   */
  public EmployeeCreationService(final EmployeeRepository repository) { // Changed from JpaEmployeeRepository
    super(repository);
  }
}
