/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: LoyaltyPointHistoryMapper.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 21:56:48
 */

package com.nthn.storevision.mapper;

import com.nthn.storevision.dto.LoyaltyPointHistoryDTO;
import com.nthn.storevision.entity.LoyaltyPointHistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * MapStruct mapper for {@link LoyaltyPointHistoryEntity}. Does not extend {@link
 * EntityMapperConfig} because {@link LoyaltyPointHistoryEntity} is not auditable (no soft-delete,
 * no BaseEntity). One-way only: history entries are never created from DTOs (BR-C05).
 */
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoyaltyPointHistoryMapper {

  /**
   * Maps a {@link LoyaltyPointHistoryEntity} to a {@link LoyaltyPointHistoryDTO}.
   *
   * @param entity the entity to map; must not be {@code null}
   * @return the mapped DTO
   */
  @Mapping(source = "customer.id", target = "customerId")
  LoyaltyPointHistoryDTO toDTO(LoyaltyPointHistoryEntity entity);
}
