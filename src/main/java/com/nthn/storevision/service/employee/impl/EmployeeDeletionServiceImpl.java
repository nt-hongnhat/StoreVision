package com.nthn.storevision.service.employee.impl;

import com.nthn.storevision.entity.EmployeeEntity;
import com.nthn.storevision.repository.EmployeeRepository;
import com.nthn.storevision.service.employee.EmployeeDeletionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link EmployeeDeletionService} that soft-deletes
 * {@link EmployeeEntity} instances by setting the {@code isDeleted} flag, which triggers
 * {@code CustomAuditingEntityListener} to populate {@code deletedBy} and {@code deletedDate}.
 */
@Service
@RequiredArgsConstructor
public class EmployeeDeletionServiceImpl implements EmployeeDeletionService {

  private final EmployeeRepository repository;

  @Override
  @Transactional
  public void delete(final EmployeeEntity entity) {
    entity.setDeleted(true);
    repository.save(entity);
  }

  @Override
  @Transactional
  public void markAsDeleted(final Long id) {
    repository.markDeletedById(id);
  }

  @Override
  @Transactional
  public void restore(final Long id) {
    repository.restoreById(id);
  }

  @Override
  @Transactional
  public void permanentlyDelete(final Long id) {
    repository.deleteById(id);
  }
}
