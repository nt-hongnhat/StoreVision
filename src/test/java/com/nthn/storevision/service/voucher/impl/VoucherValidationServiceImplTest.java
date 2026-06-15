/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherValidationServiceImplTest.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 14:38:59
 */

package com.nthn.storevision.service.voucher.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.nthn.storevision.constant.DiscountType;
import com.nthn.storevision.dto.VoucherDTO;
import com.nthn.storevision.entity.VoucherEntity;
import com.nthn.storevision.exception.voucher.VoucherExpiredException;
import com.nthn.storevision.exception.voucher.VoucherInactiveException;
import com.nthn.storevision.exception.voucher.VoucherMinOrderAmountNotMetException;
import com.nthn.storevision.exception.voucher.VoucherNotFoundException;
import com.nthn.storevision.exception.voucher.VoucherUsageLimitReachedException;
import com.nthn.storevision.mapper.VoucherMapper;
import com.nthn.storevision.repository.VoucherRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Consumer;

import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Unit tests for {@link VoucherValidationServiceImpl}. */
@ExtendWith(MockitoExtension.class)
class VoucherValidationServiceImplTest {

  private static final String CODE = "SALE10";
  private static final BigDecimal ORDER_AMOUNT = new BigDecimal("200000");
  private VoucherValidationServiceImpl service;
  @Mock private VoucherMapper voucherMapper;
  @Mock private VoucherRepository voucherRepository;

  @BeforeEach
  void setUp() {
    service = new VoucherValidationServiceImpl(voucherMapper, voucherRepository);
  }

  @Test
  void validate_shouldReturnVoucherDTO_whenAllRulesPass() {
    final VoucherEntity voucher = buildVoucher(v -> v.setMinOrderAmount(new BigDecimal("100000")));
    final VoucherDTO expected = VoucherDTO.builder().id(1L).code(CODE).build();
    when(voucherRepository.findByCode(CODE)).thenReturn(Optional.of(voucher));
    when(voucherMapper.toDTO(voucher)).thenReturn(expected);

    final VoucherDTO result = service.validate(CODE, ORDER_AMOUNT);

    assertThat(result).isEqualTo(expected);
  }

  @Test
  void validate_shouldThrowVoucherExpiredException_whenExpiryDateIsPast() {
    final VoucherEntity voucher = buildVoucher(v -> v.setExpiryDate(LocalDate.now().minusDays(1)));
    when(voucherRepository.findByCode(CODE)).thenReturn(Optional.of(voucher));

    assertThrows(VoucherExpiredException.class, () -> service.validate(CODE, ORDER_AMOUNT));
  }

  @Test
  void validate_shouldThrowVoucherInactiveException_whenVoucherIsNotActive() {
    final VoucherEntity voucher = buildVoucher(v -> v.setActive(false));
    when(voucherRepository.findByCode(CODE)).thenReturn(Optional.of(voucher));

    assertThrows(VoucherInactiveException.class, () -> service.validate(CODE, ORDER_AMOUNT));
  }

  @Test
  void validate_shouldThrowVoucherMinOrderAmountNotMetException_whenOrderAmountBelowMinimum() {
    final VoucherEntity voucher = buildVoucher(v -> v.setMinOrderAmount(new BigDecimal("500000")));
    when(voucherRepository.findByCode(CODE)).thenReturn(Optional.of(voucher));

    assertThrows(
        VoucherMinOrderAmountNotMetException.class, () -> service.validate(CODE, ORDER_AMOUNT));
  }

  @Test
  void validate_shouldThrowVoucherNotFoundException_whenCodeDoesNotExist() {
    when(voucherRepository.findByCode(CODE)).thenReturn(Optional.empty());

    assertThrows(VoucherNotFoundException.class, () -> service.validate(CODE, ORDER_AMOUNT));
  }

  @Test
  void validate_shouldThrowVoucherNotFoundException_whenVoucherIsSoftDeleted() {
    final VoucherEntity voucher = buildVoucher(v -> v.setDeletedDate(LocalDateTime.now()));
    when(voucherRepository.findByCode(CODE)).thenReturn(Optional.of(voucher));

    assertThrows(VoucherNotFoundException.class, () -> service.validate(CODE, ORDER_AMOUNT));
  }

  @Test
  void validate_shouldThrowVoucherUsageLimitReachedException_whenUsageCountReachesMax() {
    final VoucherEntity voucher = buildVoucher(v -> v.setUsageCount(v.getMaxUsage()));
    when(voucherRepository.findByCode(CODE)).thenReturn(Optional.of(voucher));

    assertThrows(
        VoucherUsageLimitReachedException.class, () -> service.validate(CODE, ORDER_AMOUNT));
  }

  /** Builds a voucher that satisfies every rule unless overridden via the given customizer. */
  private static VoucherEntity buildVoucher(
      final @NonNull Consumer<? super VoucherEntity> customizer) {
    final VoucherEntity voucher =
        VoucherEntity.builder()
            .id(1L)
            .code(CODE)
            .active(true)
            .discountType(DiscountType.PERCENTAGE)
            .discountValue(new BigDecimal("10"))
            .startDate(LocalDate.now().minusDays(1))
            .expiryDate(LocalDate.now().plusDays(30))
            .maxUsage(5)
            .usageCount(0)
            .build();
    customizer.accept(voucher);
    return voucher;
  }
}
