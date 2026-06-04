/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: AbstractCreatable.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 14:57:16
 */

package com.nthn.storevision.service.common;

import com.nthn.storevision.entity.mapped.BaseEntity;
import com.nthn.storevision.mapper.EntityMapper;
import com.nthn.storevision.repository.generic.SaveRepository;
import com.nthn.storevision.service.generic.Creatable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract base class providing a generic implementation of the {@link Creatable} interface for
 * creating and persisting entities.
 *
 * <p>The repository bound uses {@link SaveRepository} rather than {@code JpaRepository} so that the
 * service layer stays decoupled from Spring Data JPA.
 *
 * <p>Subclasses supply at construction time:
 *
 * <ul>
 *   <li>{@code repository} — the domain repository used to persist the entity (typically the
 *       domain-specific repo impl, e.g. {@code EmployeeRepository})
 *   <li>{@code toDTO} — converts the saved entity back to the output DTO
 *   <li>{@code toEntity} — converts the input DTO to a JPA entity ready for persistence
 * </ul>
 *
 * @param <T> the DTO type (input and output)
 * @param <E> the JPA entity type
 * @param <J> the domain repository type, must extend {@link SaveRepository}
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class AbstractCreatable<T, E extends BaseEntity<?>, J extends SaveRepository<E>>
    implements Creatable<T> {

  private final EntityMapper<T, ?, E> mapper;
  private final J repository;

  @Override
  public T create(final T request) {
    log.debug("Creating entity from request: {}", request);
    final T created = mapper.toDTO(repository.save(mapper.toEntity(request)));
    log.debug("Entity created successfully: {}", created);
    return created;
  }
}
