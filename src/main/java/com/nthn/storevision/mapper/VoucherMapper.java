/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherMapper.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 17:01:33
 */

package com.nthn.storevision.mapper;

import com.nthn.storevision.dto.voucher.VoucherDTO;
import com.nthn.storevision.entity.VoucherEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/** MapStruct mapper for converting between {@link VoucherDTO} and {@link VoucherEntity}. */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, config = EntityMapperConfig.class)
public interface VoucherMapper extends EntityMapperConfig<VoucherDTO, Long, VoucherEntity> {}
