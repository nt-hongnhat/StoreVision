package com.nthn.storevision.service.employee.impl;

import com.nthn.storevision.entity.EmployeeEntity;
import com.nthn.storevision.repository.EmployeeRepository;
import com.nthn.storevision.service.employee.EmployeeUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link EmployeeUpdateService} for persisting updates to
 * {@link EmployeeEntity} instances in the database.
 */
@Service
@RequiredArgsConstructor
public class EmployeeUpdateServiceImpl implements EmployeeUpdateService {

  private final EmployeeRepository repository;

  @Transactional
  @Override
  public void update(final EmployeeEntity employee) {
    repository.save(employee);
  }
}
