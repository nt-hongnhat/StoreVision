/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductMapper.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 14:53:15
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
public interface ProductMapper extends EntityMapper<ProductDTO, Long, ProductEntity> {}
