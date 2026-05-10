/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: HardDeletable.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-10 13:07:34
 */

package com.nthn.storevision.service.generic;

/**
 * Interface for entities that can be permanently deleted.
 *
 * @param <T> the type of the entity
 * @param <I> the type of the entity's identifier
 */
public interface HardDeletable<T, I> extends Deletable<T> {

  /**
   * Permanently deletes an entity by its identifier.
   *
   * @param id the identifier of the entity to be deleted
   */
  void permanentlyDelete(I id);
}
