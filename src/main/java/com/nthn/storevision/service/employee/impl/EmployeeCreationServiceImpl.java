package com.nthn.storevision.service.employee.impl;

import com.nthn.storevision.dto.EmployeeDTO;
import com.nthn.storevision.entity.EmployeeEntity;
import com.nthn.storevision.mapper.EmployeeMapper;
import com.nthn.storevision.repository.EmployeeRepository;
import com.nthn.storevision.service.common.AbstractCreatable;
import com.nthn.storevision.service.employee.EmployeeCreationService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link EmployeeCreationService} that extends {@link AbstractCreatable}
 * to persist {@link EmployeeEntity} instances via {@link EmployeeRepository}.
 */
@Service
public class EmployeeCreationServiceImpl extends AbstractCreatable<EmployeeDTO, EmployeeEntity>
    implements EmployeeCreationService {

  public EmployeeCreationServiceImpl(
      final EmployeeRepository repository, final EmployeeMapper mapper) {
    super(repository::save, mapper::toDTO, mapper::toEntity);
  }
}
