/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherMapper.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.mapper;

import com.nthn.storevision.dto.VoucherDTO;
import com.nthn.storevision.entity.VoucherEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * Mapper interface for converting between {@link VoucherEntity} and {@link VoucherDTO}. This
 * interface uses MapStruct to generate the implementation.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, config = EntityMapperConfig.class)
public interface VoucherMapper extends EntityMapperConfig<VoucherDTO, Long, VoucherEntity> {}
