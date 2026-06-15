/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerCreationServiceImplTest.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 14:37:02
 */

package com.nthn.storevision.service.customer.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nthn.storevision.dto.CustomerDTO;
import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.exception.customer.CustomerEmailAlreadyExistsException;
import com.nthn.storevision.mapper.CustomerMapper;
import com.nthn.storevision.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Unit tests for {@link CustomerCreationServiceImpl}. */
@ExtendWith(MockitoExtension.class)
class CustomerCreationServiceImplTest {

  @Mock private CustomerMapper mapper;
  @Mock private CustomerRepository repository;

  private CustomerCreationServiceImpl service;

  @Test
  void create_shouldDelegateToSuperWhenEmailIsAvailable() {
    final CustomerDTO request =
        CustomerDTO.builder().name("Nguyen Van A").email("a@example.com").build();
    final CustomerEntity entityToSave =
        CustomerEntity.builder().name("Nguyen Van A").email("a@example.com").build();
    final CustomerEntity savedEntity =
        CustomerEntity.builder().id(1L).name("Nguyen Van A").email("a@example.com").build();
    final CustomerDTO expected =
        CustomerDTO.builder().id(1L).name("Nguyen Van A").email("a@example.com").build();

    when(repository.isEmailTaken("a@example.com")).thenReturn(false);
    when(mapper.toEntity(request)).thenReturn(entityToSave);
    when(repository.save(entityToSave)).thenReturn(savedEntity);
    when(mapper.toDTO(savedEntity)).thenReturn(expected);

    final CustomerDTO result = service.create(request);

    assertThat(result).isEqualTo(expected);
  }

  @Test
  void create_shouldThrowWhenEmailAlreadyExists() {
    final CustomerDTO request =
        CustomerDTO.builder().name("Nguyen Van A").email("a@example.com").build();
    when(repository.isEmailTaken("a@example.com")).thenReturn(true);

    assertThrows(CustomerEmailAlreadyExistsException.class, () -> service.create(request));

    verify(repository, never()).save(any());
  }

  @BeforeEach
  void setUp() {
    service = new CustomerCreationServiceImpl(mapper, repository);
  }
}
