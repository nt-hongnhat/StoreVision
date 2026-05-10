/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: SoftDeletable.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-10 13:06:54
 */

package com.nthn.storevision.service.generic;

/**
 * Interface for entities that can be softly deleted and restored.
 *
 * @param <T> the type of the entity
 * @param <I> the type of the entity's identifier
 */
public interface SoftDeletable<T, I> extends Deletable<T> {

  /**
   * Marks an entity as deleted by its identifier.
   *
   * @param id the identifier of the entity to be marked as deleted
   */
  void markAsDeleted(I id);

  /**
   * Restores a previously soft-deleted entity by its identifier.
   *
   * @param id the identifier of the entity to be restored
   */
  void restore(I id);
}
