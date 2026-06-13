/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherUpdateService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13
 */

package com.nthn.storevision.service.voucher;

import com.nthn.storevision.dto.VoucherDTO;
import com.nthn.storevision.entity.VoucherEntity;
import com.nthn.storevision.service.generic.Updatable;

/**
 * Service interface for updating {@link VoucherEntity} instances.
 */
public interface VoucherUpdateService extends Updatable<VoucherDTO, VoucherEntity> {
}