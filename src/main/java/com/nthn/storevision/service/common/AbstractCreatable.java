/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: AbstractCreatable.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 14:09:20
 */

package com.nthn.storevision.service.common;

import com.nthn.storevision.service.generic.Creatable;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract base class providing a generic implementation of the {@link Creatable} interface for
 * creating and persisting entities.
 *
 * <p>Subclasses supply three functions at construction time:
 *
 * <ul>
 *   <li>{@code toEntity} — converts the input DTO to a JPA entity ready for persistence
 *   <li>{@code saveFunction} — persists the entity and returns the saved instance (typically {@code
 *       repository::save})
 *   <li>{@code toDTO} — converts the saved entity back to the output DTO
 * </ul>
 *
 * <p>This design keeps the service layer decoupled from Spring Data JPA while providing a reusable
 * creation flow.
 *
 * @param <T> the DTO type (input and output)
 * @param <E> the JPA entity type
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class AbstractCreatable<T, E> implements Creatable<T> {

  private final UnaryOperator<E> saveFunction;
  private final Function<? super E, ? extends T> toDTO;
  private final Function<? super T, ? extends E> toEntity;

  @Override
  public T create(final T request) {
    log.debug("Creating entity from request: {}", request);
    final T created = toDTO.apply(saveFunction.apply(toEntity.apply(request)));
    log.debug("Entity created successfully: {}", created);
    return created;
  }
}
