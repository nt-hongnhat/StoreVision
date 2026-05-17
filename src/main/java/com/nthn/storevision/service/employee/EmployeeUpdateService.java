package com.nthn.storevision.service.employee;

import com.nthn.storevision.entity.EmployeeEntity;
import com.nthn.storevision.repository.EmployeeRepository; // Changed from JpaEmployeeRepository
import com.nthn.storevision.service.generic.Updatable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for updating {@link EmployeeEntity} instances in the application.
 *
 * <p>This class implements the {@link Updatable} interface, providing functionality for persisting
 * updates to the {@link EmployeeEntity} objects in the underlying database. It uses a {@link
 * EmployeeRepository} for database access and is marked as a Spring {@link Service} to enable dependency
 * injection and manage its lifecycle. The {@code update} method is executed within a transactional
 * context, ensuring that changes are committed consistently.
 */
@Service
@RequiredArgsConstructor
public class EmployeeUpdateService implements Updatable<EmployeeEntity> {

  private final EmployeeRepository repository; // Changed from JpaEmployeeRepository

  /**
   * Updates the provided {@link EmployeeEntity} object in the database.
   *
   * @param employee The {@link EmployeeEntity} object to update.
   */
  @Transactional
  @Override
  public void update(final EmployeeEntity employee) {
    repository.save(employee);
  }
}
