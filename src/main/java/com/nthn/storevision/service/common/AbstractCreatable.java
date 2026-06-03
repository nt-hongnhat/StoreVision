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
import java.util.function.UnaryOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Persistable;

/**
 * Abstract base class providing a generic implementation of the {@link Creatable} interface for
 * creating and persisting entities.
 *
 * <p>A {@link UnaryOperator} is used for the save operation so that the service layer remains
 * decoupled from Spring Data JPA. Subclasses supply {@code repository::save} as the operator.
 *
 * @param <M> the domain model type, extending {@link Persistable}
 * @param <I> the type of the entity's identifier
 * @param <E> the JPA entity type, extending {@code M}
 */
@Slf4j
public abstract class AbstractCreatable<M extends Persistable<I>, I, E extends M>
    implements Creatable<M, I> {

  private final UnaryOperator<E> saveFunction;

  protected AbstractCreatable(final UnaryOperator<E> saveFunction) {
    this.saveFunction = saveFunction;
  }

  @SuppressWarnings("unchecked")
  @Override
  public I create(final M entity) {
    log.debug("Creating entity: {}", entity);
    final M saved = saveFunction.apply((E) entity);
    log.info("Entity created with id: {}", saved.getId());
    return saved.getId();
  }
}
