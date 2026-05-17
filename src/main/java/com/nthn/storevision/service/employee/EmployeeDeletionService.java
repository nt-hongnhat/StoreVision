package com.nthn.storevision.service.employee;

import com.nthn.storevision.entity.EmployeeEntity;
import com.nthn.storevision.repository.EmployeeRepository; // Changed from JpaEmployeeRepository
import com.nthn.storevision.service.generic.HardDeletable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for permanently deleting {@link EmployeeEntity} entities.
 * This class implements the {@link HardDeletable} interface.
 */
@Service
@RequiredArgsConstructor
public class EmployeeDeletionService implements HardDeletable<EmployeeEntity, Long> {

  private final EmployeeRepository repository; // Changed from JpaEmployeeRepository

  /**
   * Deletes an existing employee entity.
   *
   * @param entity The {@link EmployeeEntity} to be deleted.
   */
  @Override
  @Transactional
  public void delete(final EmployeeEntity entity) {
    repository.delete(entity);
  }

  /**
   * Permanently deletes an employee entity by its identifier.
   *
   * @param id The ID of the employee to be deleted.
   */
  @Override
  @Transactional
  public void permanentlyDelete(final Long id) {
    repository.deleteById(id);
  }
}
