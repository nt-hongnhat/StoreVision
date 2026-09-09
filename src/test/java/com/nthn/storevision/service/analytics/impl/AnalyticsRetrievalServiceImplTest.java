/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.test
 * File: AnalyticsRetrievalServiceImplTest.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-09-09 16:36:15
 */

package com.nthn.storevision.service.analytics.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nthn.storevision.constant.order.OrderStatus;
import com.nthn.storevision.dto.analytics.OrderStatusCountDTO;
import com.nthn.storevision.dto.order.OrderSummaryDTO;
import com.nthn.storevision.exception.InvalidDateRangeException;
import com.nthn.storevision.repository.AnalyticsRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Unit tests for {@link AnalyticsRetrievalServiceImpl}. */
@ExtendWith(MockitoExtension.class)
class AnalyticsRetrievalServiceImplTest {

  @Mock private AnalyticsRepository analyticsRepository;

  @InjectMocks private AnalyticsRetrievalServiceImpl service;

  @Test
  void getOrderSummary_shouldAggregateStatusesAndCompletedRevenue() {
    final LocalDate from = LocalDate.of(2026, 7, 1);
    final LocalDate to = LocalDate.of(2026, 7, 7);
    when(analyticsRepository.countOrdersByStatus(
            from.atStartOfDay(), to.plusDays(1).atStartOfDay()))
        .thenReturn(
            List.of(
                new OrderStatusCountDTO(OrderStatus.PENDING, 2L),
                new OrderStatusCountDTO(OrderStatus.COMPLETED, 3L),
                new OrderStatusCountDTO(OrderStatus.CANCELLED, 1L)));
    when(analyticsRepository.sumCompletedRevenue(
            from.atStartOfDay(), to.plusDays(1).atStartOfDay()))
        .thenReturn(Optional.of(new BigDecimal("250.50")));

    final OrderSummaryDTO result = service.getOrderSummary(from, to);

    assertThat(result.from()).isEqualTo(from);
    assertThat(result.to()).isEqualTo(to);
    assertThat(result.totalOrders()).isEqualTo(6);
    assertThat(result.pendingOrders()).isEqualTo(2);
    assertThat(result.completedOrders()).isEqualTo(3);
    assertThat(result.cancelledOrders()).isEqualTo(1);
    assertThat(result.totalRevenue()).isEqualByComparingTo("250.50");
  }

  @Test
  void getOrderSummary_shouldThrowWhenFromAfterTo() {
    final LocalDate from = LocalDate.of(2026, 7, 10);
    final LocalDate to = LocalDate.of(2026, 7, 1);

    assertThrows(InvalidDateRangeException.class, () -> service.getOrderSummary(from, to));
  }

  @Test
  void getOrderSummary_shouldUseCurrentMonthWhenRangeMissing() {
    when(analyticsRepository.countOrdersByStatus(
            org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()))
        .thenReturn(List.of());
    when(analyticsRepository.sumCompletedRevenue(
            org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()))
        .thenReturn(Optional.empty());

    service.getOrderSummary(null, null);

    final ArgumentCaptor<LocalDateTime> fromCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
    final ArgumentCaptor<LocalDateTime> toCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
    verify(analyticsRepository).countOrdersByStatus(fromCaptor.capture(), toCaptor.capture());

    final YearMonth currentMonth = YearMonth.now();
    assertThat(fromCaptor.getValue()).isEqualTo(currentMonth.atDay(1).atStartOfDay());
    assertThat(toCaptor.getValue())
        .isEqualTo(currentMonth.atEndOfMonth().plusDays(1).atStartOfDay());
  }
}
