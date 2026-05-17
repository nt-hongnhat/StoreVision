/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: AbstractCreatable.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-17 14:15:02
 */

package com.nthn.storevision.service.common;

import com.nthn.storevision.service.generic.Creatable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Abstract base class providing a generic implementation of the {@link Creatable} interface for
 * creating and persisting entities.
 *
 * @param <M> the type of the entity to be created, extending {@link Persistable}
 * @param <I> the type of the entity's identifier
 * @param <E> the type of the JPA entity, extending {@code M}
 */
@RequiredArgsConstructor
@Getter
public class AbstractCreatable<M extends Persistable<I>, I, E extends M>
    implements Creatable<M, I> {

  private final JpaRepository<E, I> repository;

  @SuppressWarnings("unchecked")
  @Override
  public I create(final M entity) {
    final M saved = repository.save((E) entity);
    return saved.getId();
  }
}
