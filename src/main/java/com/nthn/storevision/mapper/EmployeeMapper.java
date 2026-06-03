/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeMapper.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02 20:27:49
 */

package com.nthn.storevision.mapper;

import com.nthn.storevision.dto.EmployeeDTO;
import com.nthn.storevision.entity.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * Mapper interface for converting between {@link EmployeeEntity} and {@link EmployeeDTO}. This
 * interface uses MapStruct to generate the implementation.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, config = BaseEntityMapperConfig.class)
public interface EmployeeMapper {
  /**
   * Converts an {@link EmployeeEntity} to an {@link EmployeeDTO}.
   *
   * @param employee The EmployeeEntity to convert.
   * @return The converted EmployeeDTO.
   */
  EmployeeDTO toDTO(EmployeeEntity employee);

  /**
   * Converts an {@link EmployeeDTO} to an {@link EmployeeEntity}.
   *
   * @param employeeDTO The EmployeeDTO to convert.
   * @return The converted EmployeeEntity.
   */
  EmployeeEntity toEntity(EmployeeDTO employeeDTO);
}
