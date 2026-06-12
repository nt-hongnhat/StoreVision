/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherCreationService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 21:56:59
 */

package com.nthn.storevision.service.voucher;

import com.nthn.storevision.dto.VoucherDTO;
import com.nthn.storevision.service.generic.Creatable;

/**
 * Service interface for creating and persisting vouchers. Used by the loyalty redemption flow to
 * mint single-use discount vouchers from redeemed points.
 *
 * @see com.nthn.storevision.service.common.AbstractCreatable
 * @see Creatable
 */
@FunctionalInterface
public interface VoucherCreationService extends Creatable<VoucherDTO> {}
