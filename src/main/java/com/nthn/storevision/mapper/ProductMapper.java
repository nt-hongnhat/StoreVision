/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductMapper.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 14:10:16
 */

package com.nthn.storevision.mapper;

import com.nthn.storevision.dto.ProductDTO;
import com.nthn.storevision.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

/**
 * Mapper interface for converting between {@link ProductEntity} and {@link ProductDTO}. This
 * interface uses MapStruct to generate the implementation.
 */
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    config = BaseEntityMapperConfig.class)
public interface ProductMapper {
  /**
   * Converts a {@link ProductEntity} to a {@link ProductDTO}.
   *
   * @param product The ProductEntity to convert.
   * @return The converted ProductDTO.
   */
  ProductDTO toDTO(ProductEntity product);

  /**
   * Converts a {@link ProductDTO} to a {@link ProductEntity}.
   *
   * @param productDTO The ProductDTO to convert.
   * @return The converted ProductEntity.
   */
  ProductEntity toEntity(ProductDTO productDTO);

  /**
   * Updates an existing {@link ProductEntity} with values from the given {@link ProductDTO}. The
   * {@code id} field is intentionally ignored to preserve the entity's identity.
   *
   * @param dto the source DTO carrying updated field values
   * @param entity the target entity to be updated in place
   */
  @Mapping(target = "id", ignore = true)
  void updateEntity(ProductDTO dto, @MappingTarget ProductEntity entity);
}
