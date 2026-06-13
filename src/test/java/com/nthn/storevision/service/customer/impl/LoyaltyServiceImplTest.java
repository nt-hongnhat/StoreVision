/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: LoyaltyServiceImplTest.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 14:38:59
 */

package com.nthn.storevision.service.customer.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nthn.storevision.constant.DiscountType;
import com.nthn.storevision.constant.LoyaltyPointType;
import com.nthn.storevision.constant.LoyaltyTier;
import com.nthn.storevision.dto.LoyaltyDetailsDTO;
import com.nthn.storevision.dto.LoyaltyPointHistoryDTO;
import com.nthn.storevision.dto.VoucherDTO;
import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.entity.LoyaltyPointHistoryEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.exception.InsufficientLoyaltyPointsException;
import com.nthn.storevision.mapper.LoyaltyPointHistoryMapper;
import com.nthn.storevision.repository.CustomerRepository;
import com.nthn.storevision.repository.LoyaltyPointHistoryRepository;
import com.nthn.storevision.service.voucher.VoucherCreationService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

/** Unit tests for {@link LoyaltyServiceImpl}. */
@ExtendWith(MockitoExtension.class)
class LoyaltyServiceImplTest {

  private static final Long CUSTOMER_ID = 1L;
  private static final Long ORDER_ID = 100L;

  @Mock private CustomerRepository customerRepository;
  @Mock private LoyaltyPointHistoryMapper loyaltyPointHistoryMapper;
  @Mock private LoyaltyPointHistoryRepository loyaltyPointHistoryRepository;
  private LoyaltyServiceImpl service;
  @Mock private VoucherCreationService voucherCreationService;

  @Test
  void addPoints_shouldEarnPointsAndSaveHistory_forBronzeTier() {
    final CustomerEntity customer =
        CustomerEntity.builder()
            .id(CUSTOMER_ID)
            .loyaltyPoints(50L)
            .loyaltyTier(LoyaltyTier.BRONZE)
            .totalSpending(new BigDecimal("1000000"))
            .build();
    when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));

    service.addPoints(CUSTOMER_ID, new BigDecimal("100000"), ORDER_ID);

    assertThat(customer.getLoyaltyPoints()).isEqualTo(60L);
    assertThat(customer.getTotalSpending()).isEqualTo(new BigDecimal("1100000"));
    assertThat(customer.getLoyaltyTier()).isEqualTo(LoyaltyTier.BRONZE);
    verify(customerRepository).save(customer);

    final ArgumentCaptor<LoyaltyPointHistoryEntity> captor =
        ArgumentCaptor.forClass(LoyaltyPointHistoryEntity.class);
    verify(loyaltyPointHistoryRepository).save(captor.capture());
    final LoyaltyPointHistoryEntity history = captor.getValue();
    assertThat(history.getType()).isEqualTo(LoyaltyPointType.EARN);
    assertThat(history.getPoints()).isEqualTo(10L);
    assertThat(history.getBalanceAfter()).isEqualTo(60L);
    assertThat(history.getReferenceOrderId()).isEqualTo(ORDER_ID);
  }

  @Test
  void addPoints_shouldNotSaveHistory_whenZeroPointsEarned() {
    final CustomerEntity customer =
        CustomerEntity.builder()
            .id(CUSTOMER_ID)
            .loyaltyPoints(0L)
            .loyaltyTier(LoyaltyTier.BRONZE)
            .totalSpending(BigDecimal.ZERO)
            .build();
    when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));

    service.addPoints(CUSTOMER_ID, new BigDecimal("5000"), ORDER_ID);

    assertThat(customer.getLoyaltyPoints()).isZero();
    verify(customerRepository).save(customer);
    verify(loyaltyPointHistoryRepository, never()).save(any());
  }

  @Test
  void addPoints_shouldThrowWhenCustomerIsSoftDeleted() {
    final CustomerEntity customer =
        CustomerEntity.builder().id(CUSTOMER_ID).deletedDate(LocalDateTime.now()).build();
    when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));

    assertThrows(
        EntityNotFoundException.class,
        () -> service.addPoints(CUSTOMER_ID, new BigDecimal("100000"), ORDER_ID));
  }

  @Test
  void addPoints_shouldThrowWhenCustomerNotFound() {
    when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.empty());

    assertThrows(
        EntityNotFoundException.class,
        () -> service.addPoints(CUSTOMER_ID, new BigDecimal("100000"), ORDER_ID));
  }

  @Test
  void addPoints_shouldUpgradeTier_whenTotalSpendingCrossesSilverThreshold() {
    final CustomerEntity customer =
        CustomerEntity.builder()
            .id(CUSTOMER_ID)
            .loyaltyPoints(0L)
            .loyaltyTier(LoyaltyTier.BRONZE)
            .totalSpending(new BigDecimal("4900000"))
            .build();
    when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));

    service.addPoints(CUSTOMER_ID, new BigDecimal("200000"), ORDER_ID);

    assertThat(customer.getTotalSpending()).isEqualTo(new BigDecimal("5100000"));
    assertThat(customer.getLoyaltyTier()).isEqualTo(LoyaltyTier.SILVER);
  }

  @Test
  void getLoyaltyDetails_shouldReturnDetailsWithMappedHistories() {
    final CustomerEntity customer =
        CustomerEntity.builder()
            .id(CUSTOMER_ID)
            .loyaltyPoints(100L)
            .loyaltyTier(LoyaltyTier.SILVER)
            .totalSpending(new BigDecimal("6000000"))
            .build();
    final LoyaltyPointHistoryEntity historyEntity =
        LoyaltyPointHistoryEntity.builder().id(1L).build();
    final LoyaltyPointHistoryDTO historyDTO = LoyaltyPointHistoryDTO.builder().id(1L).build();
    when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));
    when(loyaltyPointHistoryRepository.findByCustomerId(CUSTOMER_ID))
        .thenReturn(List.of(historyEntity));
    when(loyaltyPointHistoryMapper.toDTO(historyEntity)).thenReturn(historyDTO);

    final LoyaltyDetailsDTO result = service.getLoyaltyDetails(CUSTOMER_ID);

    assertThat(result.getCustomerId()).isEqualTo(CUSTOMER_ID);
    assertThat(result.getLoyaltyTier()).isEqualTo(LoyaltyTier.SILVER);
    assertThat(result.getLoyaltyPoints()).isEqualTo(100L);
    assertThat(result.getTotalSpending()).isEqualTo(new BigDecimal("6000000"));
    assertThat(result.getHistories()).containsExactly(historyDTO);
  }

  @Test
  void getLoyaltyDetails_shouldThrowWhenCustomerNotFound() {
    when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> service.getLoyaltyDetails(CUSTOMER_ID));
  }

  @Test
  void redeemPoints_shouldMintVoucherAndSaveHistory_whenBalanceIsSufficient() {
    ReflectionTestUtils.setField(service, "pointValue", new BigDecimal("1000"));
    ReflectionTestUtils.setField(service, "redeemVoucherValidityDays", 30);

    final CustomerEntity customer =
        CustomerEntity.builder().id(CUSTOMER_ID).loyaltyPoints(100L).build();
    final VoucherDTO createdVoucher = VoucherDTO.builder().id(99L).code("RDM-ABCDEFGH").build();
    when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));
    when(voucherCreationService.create(any(VoucherDTO.class))).thenReturn(createdVoucher);

    final VoucherDTO result = service.redeemPoints(CUSTOMER_ID, 50L);

    assertThat(result).isEqualTo(createdVoucher);
    assertThat(customer.getLoyaltyPoints()).isEqualTo(50L);
    verify(customerRepository).save(customer);

    final ArgumentCaptor<VoucherDTO> voucherCaptor = ArgumentCaptor.forClass(VoucherDTO.class);
    verify(voucherCreationService).create(voucherCaptor.capture());
    final VoucherDTO requested = voucherCaptor.getValue();
    assertThat(requested.getDiscountType()).isEqualTo(DiscountType.FIXED_AMOUNT);
    assertThat(requested.getDiscountValue()).isEqualTo(new BigDecimal("50000"));
    assertThat(requested.getMaxUsage()).isEqualTo(1);
    assertThat(requested.getUsageCount()).isZero();
    assertThat(requested.isActive()).isTrue();
    assertThat(requested.getStartDate()).isEqualTo(LocalDate.now());
    assertThat(requested.getExpiryDate()).isEqualTo(LocalDate.now().plusDays(30));

    final ArgumentCaptor<LoyaltyPointHistoryEntity> historyCaptor =
        ArgumentCaptor.forClass(LoyaltyPointHistoryEntity.class);
    verify(loyaltyPointHistoryRepository).save(historyCaptor.capture());
    final LoyaltyPointHistoryEntity history = historyCaptor.getValue();
    assertThat(history.getType()).isEqualTo(LoyaltyPointType.REDEEM);
    assertThat(history.getPoints()).isEqualTo(50L);
    assertThat(history.getBalanceAfter()).isEqualTo(50L);
    assertThat(history.getReferenceVoucherId()).isEqualTo(99L);
  }

  @Test
  void redeemPoints_shouldThrowWhenBalanceIsInsufficient() {
    final CustomerEntity customer =
        CustomerEntity.builder().id(CUSTOMER_ID).loyaltyPoints(50L).build();
    when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));

    assertThrows(
        InsufficientLoyaltyPointsException.class, () -> service.redeemPoints(CUSTOMER_ID, 100L));

    verify(voucherCreationService, never()).create(any());
    verify(customerRepository, never()).save(any());
    verify(loyaltyPointHistoryRepository, never()).save(any());
  }

  @BeforeEach
  void setUp() {
    service =
        new LoyaltyServiceImpl(
            customerRepository,
            loyaltyPointHistoryMapper,
            loyaltyPointHistoryRepository,
            voucherCreationService);
  }
}
