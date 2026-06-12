/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerMapper.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.mapper;

import com.nthn.storevision.dto.CustomerDTO;
import com.nthn.storevision.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

/**
 * Mapper interface for converting between {@link CustomerEntity} and {@link CustomerDTO}. This
 * interface uses MapStruct to generate the implementation.
 *
 * <p>The loyalty fields ({@code loyaltyTier}, {@code loyaltyPoints}, {@code totalSpending}) are
 * never mapped from DTO to entity — they are managed exclusively by the loyalty flow (BR-C03,
 * BR-C04) and are exposed in the DTO for responses only.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, config = EntityMapperConfig.class)
public interface CustomerMapper extends EntityMapperConfig<CustomerDTO, Long, CustomerEntity> {

  /**
   * Converts a {@link CustomerDTO} to a {@link CustomerEntity}. The loyalty fields are ignored so
   * a new customer always starts with the entity defaults (BRONZE, zero points, zero spending).
   *
   * @param dto the CustomerDTO to convert.
   * @return the converted CustomerEntity.
   */
  @Override
  @Mapping(target = "loyaltyPoints", ignore = true)
  @Mapping(target = "loyaltyTier", ignore = true)
  @Mapping(target = "totalSpending", ignore = true)
  CustomerEntity toEntity(CustomerDTO dto);

  /**
   * Updates an existing {@link CustomerEntity} with values from the given {@link CustomerDTO}.
   * The {@code id} and the loyalty fields are ignored — point balance, tier and total spending
   * can only change through the loyalty flow.
   *
   * @param dto the source DTO carrying updated field values
   * @param entity the target entity to be updated in place
   */
  @Override
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "loyaltyPoints", ignore = true)
  @Mapping(target = "loyaltyTier", ignore = true)
  @Mapping(target = "totalSpending", ignore = true)
  void updateEntity(CustomerDTO dto, @MappingTarget CustomerEntity entity);
}
