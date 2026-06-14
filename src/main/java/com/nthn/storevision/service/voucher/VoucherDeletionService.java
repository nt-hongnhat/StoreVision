/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherDeletionService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 15:46:05
 */

package com.nthn.storevision.service.voucher;

import com.nthn.storevision.entity.VoucherEntity;
import com.nthn.storevision.service.generic.HardDeletable;
import com.nthn.storevision.service.generic.SoftDeletable;

/**
 * Service interface for soft-deleting, restoring, and permanently deleting {@link VoucherEntity}
 * entities.
 *
 * @see SoftDeletable
 * @see HardDeletable
 */
public interface VoucherDeletionService
    extends SoftDeletable<VoucherEntity, Long>, HardDeletable<VoucherEntity, Long> {}
