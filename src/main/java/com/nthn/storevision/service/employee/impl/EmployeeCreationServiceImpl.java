/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeCreationServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 17:25:32
 */

package com.nthn.storevision.service.employee.impl;

import com.nthn.storevision.dto.EmployeeDTO;
import com.nthn.storevision.dto.UserDTO;
import com.nthn.storevision.entity.EmployeeEntity;
import com.nthn.storevision.mapper.EntityMapperConfig;
import com.nthn.storevision.repository.EmployeeRepository;
import com.nthn.storevision.service.common.AbstractCreatable;
import com.nthn.storevision.service.employee.EmployeeCreationService;
import com.nthn.storevision.service.user.UserCreationService;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link EmployeeCreationService} that extends {@link AbstractCreatable}
 * to persist {@link EmployeeEntity} instances via {@link EmployeeRepository}.
 */
@Slf4j // Added Slf4j annotation
@Service
public class EmployeeCreationServiceImpl
    extends AbstractCreatable<EmployeeDTO, EmployeeEntity, EmployeeRepository>
    implements EmployeeCreationService {

  private final PasswordEncoder passwordEncoder;
  private final UserCreationService userCreationService;

  protected EmployeeCreationServiceImpl(
      final EntityMapperConfig<EmployeeDTO, ?, EmployeeEntity> mapper,
      final EmployeeRepository repository,
      final UserCreationService userCreationService,
      final PasswordEncoder passwordEncoder) {
    super(mapper, repository);
    this.userCreationService = userCreationService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  @Transactional
  public EmployeeDTO create(final @NonNull EmployeeDTO dto) {
    log.debug("Creating employee: username={}, email={}", dto.getUsername(), dto.getEmail());

    // 1. Create a UserEntity for the employee
    final UserDTO userDto =
        UserDTO.builder().email(dto.getEmail()).firstName(dto.getName()).build();
    final UserDTO userDTO = userCreationService.create(userDto);

    // 2. Map EmployeeDTO to EmployeeEntity and set userId
    final EmployeeEntity employeeEntity = getMapper().toEntity(dto);
    employeeEntity.setUserId(userDTO.getId());
    employeeEntity.setPassword(passwordEncoder.encode(dto.getPassword()));

    // 3. Save EmployeeEntity
    final EmployeeEntity savedEmployee = getRepository().save(employeeEntity);

    log.info(
        "Employee created: id={}, username={}, userId={}",
        savedEmployee.getId(),
        savedEmployee.getUsername(),
        savedEmployee.getUserId());
    return getMapper().toDTO(savedEmployee);
  }
}
