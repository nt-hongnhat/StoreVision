/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: Updatable.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-10 13:06:47
 */

package com.nthn.storevision.service.generic;

/**
 * Functional interface for updating entities.
 *
 * @param <T> the type of the entity to be updated
 */
@FunctionalInterface
public interface Updatable<T> {

  /**
   * Updates an existing entity.
   *
   * @param entity the entity to be updated
   */
  void update(T entity);
}
