/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherCreationService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 17:06:35
 */

package com.nthn.storevision.service.voucher;

import com.nthn.storevision.dto.VoucherDTO;
import com.nthn.storevision.entity.VoucherEntity;
import com.nthn.storevision.service.generic.Creatable;

/**
 * Service interface for creating {@link VoucherEntity} instances.
 */
@FunctionalInterface
public interface VoucherCreationService extends Creatable<VoucherDTO> {
}