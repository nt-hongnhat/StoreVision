/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: HasIdentifier.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 22:42:01
 */

package com.nthn.storevision.dto.response;

/**
 * Represents an abstraction for entities or objects that have a unique identifier.
 *
 * @param <I> the type of the identifier
 */
public interface HasIdentifier<I> {

  /**
   * Sets the identifier of the object.
   *
   * @param id the identifier to set
   * @return the current instance with the updated identifier
   */
  HasIdentifier<I> setId(I id);

  /**
   * Retrieves the unique identifier of the object.
   *
   * @return the unique identifier of type {@code I}
   */
  I getId();
}
