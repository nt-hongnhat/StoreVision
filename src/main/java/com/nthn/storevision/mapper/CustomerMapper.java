/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerMapper.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 15:24:26
 */

package com.nthn.storevision.mapper;

import com.nthn.storevision.dto.CustomerDTO;
import com.nthn.storevision.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * Mapper interface for converting between {@link CustomerEntity} and {@link CustomerDTO}. This
 * interface uses MapStruct to generate the implementation.
 */
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    config = EntityMapper.class)
public interface CustomerMapper extends EntityMapper<CustomerDTO, Long, CustomerEntity> {}
