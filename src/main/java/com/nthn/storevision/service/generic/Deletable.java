/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: Deletable.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-10 13:06:18
 */

package com.nthn.storevision.service.generic;

/**
 * Functional interface for deleting entities.
 *
 * @param <T> the type of the entity to be deleted
 */
@FunctionalInterface
public interface Deletable<T> {

  /**
   * Deletes an existing entity.
   *
   * @param entity the entity to be deleted
   */
  void delete(T entity);
}
