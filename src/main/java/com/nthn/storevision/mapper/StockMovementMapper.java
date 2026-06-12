/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: StockMovementMapper.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-05 00:00:00
 */

package com.nthn.storevision.mapper;

import com.nthn.storevision.dto.StockMovementDTO;
import com.nthn.storevision.dto.StockReceiptDTO;
import com.nthn.storevision.entity.StockMovementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * MapStruct mapper for {@link StockMovementEntity}. Does not extend {@link EntityMapperConfig}
 * because {@link StockMovementEntity} is not auditable (no soft-delete, no BaseEntity).
 */
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StockMovementMapper {

  /**
   * Maps a {@link StockMovementEntity} to a {@link StockReceiptDTO} for RECEIPT-type movements.
   *
   * @param entity the entity to map; must not be {@code null}
   * @return the mapped receipt DTO
   */
  @Mapping(source = "product.id", target = "productId")
  @Mapping(source = "product.name", target = "productName")
  @Mapping(source = "performedBy.email", target = "performedBy")
  StockReceiptDTO toReceiptDTO(StockMovementEntity entity);

  /**
   * Maps a {@link StockMovementEntity} to a {@link StockMovementDTO} for any movement type.
   *
   * @param entity the entity to map; must not be {@code null}
   * @return the mapped movement DTO
   */
  @Mapping(source = "product.id", target = "productId")
  @Mapping(source = "product.name", target = "productName")
  @Mapping(source = "performedBy.email", target = "performedBy")
  StockMovementDTO toMovementDTO(StockMovementEntity entity);
}
