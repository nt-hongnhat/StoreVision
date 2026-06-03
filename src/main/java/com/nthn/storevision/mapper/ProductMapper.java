/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductMapper.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02 20:27:49
 */

package com.nthn.storevision.mapper;

import com.nthn.storevision.dto.ProductDTO;
import com.nthn.storevision.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * Mapper interface for converting between {@link ProductEntity} and {@link ProductDTO}. This
 * interface uses MapStruct to generate the implementation.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, config = BaseEntityMapperConfig.class)
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
}
