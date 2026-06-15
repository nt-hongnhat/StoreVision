/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: VoucherRetrievalServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 22:37:03
 */

package com.nthn.storevision.service.voucher.impl;

import com.nthn.storevision.constant.message.LocalizedMessageCode;
import com.nthn.storevision.constant.message.VoucherMessageCode;
import com.nthn.storevision.dto.voucher.VoucherDTO;
import com.nthn.storevision.entity.VoucherEntity;
import com.nthn.storevision.mapper.VoucherMapper;
import com.nthn.storevision.repository.VoucherRepository;
import com.nthn.storevision.service.common.AbstractReadable;
import com.nthn.storevision.service.voucher.VoucherRetrievalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link VoucherRetrievalService} that extends {@link AbstractReadable}
 * to retrieve {@link VoucherEntity} instances via {@link VoucherRepository}.
 */
@Slf4j
@Service
public class VoucherRetrievalServiceImpl
    extends AbstractReadable<VoucherEntity, Long, VoucherRepository>
    implements VoucherRetrievalService {

  private final VoucherMapper voucherMapper;

  /**
   * Constructs a {@code VoucherRetrievalServiceImpl} with the specified repository and mapper.
   *
   * @param repository the repository used for accessing {@link VoucherEntity} entities; must not be
   *     {@code null}
   * @param voucherMapper the mapper used for converting between entity and DTO; must not be {@code
   *     null}
   */
  public VoucherRetrievalServiceImpl(
      final VoucherRepository repository, final VoucherMapper voucherMapper) {
    super(repository);
    this.voucherMapper = voucherMapper;
  }

  /** {@inheritDoc} */
  @Override
  @Transactional(readOnly = true)
  public VoucherDTO findById(final Long id) {
    log.debug("Fetching voucher DTO by id: {}", id);
    return findById(id, voucherMapper::toDTO);
  }

  /** {@inheritDoc} */
  @Override
  @Transactional(readOnly = true)
  public Page<VoucherDTO> findVouchersPage(final Pageable pageable) {
    log.debug("Fetching vouchers page: {}", pageable);
    return getRepository().findAllActive(pageable).map(voucherMapper::toDTO);
  }

  /**
   * Returns the message code for when a voucher is not found.
   *
   * @return the {@link LocalizedMessageCode} for "voucher not found"; never {@code null}
   */
  @Override
  protected LocalizedMessageCode getNotFoundMessageCode() {
    return VoucherMessageCode.NOT_FOUND;
  }
}
