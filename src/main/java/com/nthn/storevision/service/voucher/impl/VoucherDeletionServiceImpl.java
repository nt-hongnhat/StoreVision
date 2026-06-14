/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherDeletionServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 15:46:16
 */

package com.nthn.storevision.service.voucher.impl;

import com.nthn.storevision.entity.VoucherEntity;
import com.nthn.storevision.repository.VoucherRepository;
import com.nthn.storevision.service.common.AbstractDeletable;
import com.nthn.storevision.service.voucher.VoucherDeletionService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link VoucherDeletionService} that soft-deletes, restores, and
 * permanently deletes {@link VoucherEntity} instances via {@link VoucherRepository}.
 */
@Service
public class VoucherDeletionServiceImpl
    extends AbstractDeletable<VoucherEntity, Long, VoucherRepository>
    implements VoucherDeletionService {

  protected VoucherDeletionServiceImpl(final VoucherRepository repository) {
    super(repository);
  }
}
