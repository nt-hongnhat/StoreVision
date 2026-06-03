package com.nthn.storevision.service.employee.impl;

import com.nthn.storevision.entity.EmployeeEntity;
import com.nthn.storevision.repository.EmployeeRepository;
import com.nthn.storevision.service.common.AbstractCreatable;
import com.nthn.storevision.service.employee.EmployeeCreationService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link EmployeeCreationService} that extends {@link AbstractCreatable}
 * to persist {@link EmployeeEntity} instances via {@link EmployeeRepository}.
 */
@Service
public class EmployeeCreationServiceImpl
    extends AbstractCreatable<EmployeeEntity, Long, EmployeeEntity>
    implements EmployeeCreationService {

  public EmployeeCreationServiceImpl(final EmployeeRepository repository) {
    super(repository);
  }
}
