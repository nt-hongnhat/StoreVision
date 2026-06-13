/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherDeletionService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 16:44:24
 */

package com.nthn.storevision.service.voucher;

import com.nthn.storevision.dto.VoucherDTO;
import com.nthn.storevision.entity.VoucherEntity;
import com.nthn.storevision.service.generic.SoftDeletable;

/**
 * Service interface for deleting {@link VoucherEntity} instances.
 */
public interface VoucherDeletionService extends SoftDeletable<VoucherDTO, VoucherEntity> {
}