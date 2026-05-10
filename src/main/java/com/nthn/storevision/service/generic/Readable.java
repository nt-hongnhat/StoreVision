/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: Readable.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-10 13:06:47
 */

package com.nthn.storevision.service.generic;

import java.util.List;
import java.util.function.Function;

/**
 * Interface for entities that can be read from a data source.
 *
 * @param <T> the type of the entity
 * @param <I> the type of the entity's identifier
 */
public interface Readable<T, I> {

  /**
   * Retrieves all entities.
   *
   * @return a list of all entities
   * @param <R> the type of the result
   * @param mapper the function to map the entity to the result
   */
  <R> List<R> findAll(Function<T, R> mapper);

  /**
   * Retrieves an entity by its identifier.
   *
   * @param id the identifier of the entity to be retrieved
   * @return the entity with the specified identifier, or null if not found
   * @param <R> the type of the result
   * @param mapper the function to map the entity to the result
   */
  <R> R findById(I id, Function<T, R> mapper);
}
