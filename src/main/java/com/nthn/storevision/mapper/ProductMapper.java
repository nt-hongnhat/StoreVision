/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductMapper.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-05 15:28:01
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
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, config = EntityMapperConfig.class)
public interface ProductMapper extends EntityMapperConfig<ProductDTO, Long, ProductEntity> {

  @Mapping(
      target = "createdBy",
      expression =
          "java(entity.getCreatedBy().map(com.nthn.storevision.model.User::getEmail).orElse(null))")
  @Mapping(target = "createdDate", expression = "java(entity.getCreatedDate().orElse(null))")
  @Mapping(
      target = "lastModifiedDate",
      expression = "java(entity.getLastModifiedDate().orElse(null))")
  @Override
  ProductDTO toDTO(ProductEntity entity);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "createdDate", ignore = true)
  @Mapping(target = "lastModifiedDate", ignore = true)
  @Mapping(target = "deletedBy", ignore = true)
  @Mapping(target = "deletedDate", ignore = true)
  @Override
  void updateEntity(ProductDTO dto, @MappingTarget ProductEntity entity);
}
