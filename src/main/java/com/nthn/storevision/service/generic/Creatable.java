/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: Creatable.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-10 13:06:18
 */

package com.nthn.storevision.service.generic;

import org.springframework.data.domain.Persistable;

/**
 * Functional interface for creating and persisting new entities.
 *
 * @param <M> the type of the entity to be created, extending {@link Persistable}
 * @param <I> the type of the entity's identifier
 */
@FunctionalInterface
public interface Creatable<M extends Persistable<I>, I> {

  /**
   * Creates and persists a new entity.
   *
   * @param entity the entity to be created and persisted
   * @return the identifier of the newly created entity
   */
  I create(M entity);
}
