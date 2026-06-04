/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: DeleteRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 14:31:23
 */

package com.nthn.storevision.repository.generic;

/**
 * Minimal delete contract shared by all domain repository interfaces.
 *
 * @param <I> the identifier type
 */
public interface DeleteRepository<I> {

  /**
   * Permanently removes the entity with the given identifier from the data source.
   *
   * @param id the identifier of the entity to remove
   */
  void deleteById(I id);

  /**
   * Soft-deletes the entity with the given identifier by setting its {@code isDeleted} flag to
   * {@code true}. The {@code CustomAuditingEntityListener} then automatically populates
   * {@code deletedBy} and {@code deletedDate} on {@code @PreUpdate}.
   *
   * @param id the identifier of the entity to soft-delete
   */
  void markDeletedById(I id);

  /**
   * Restores a previously soft-deleted entity by clearing its {@code deletedBy}, {@code
   * deletedDate} fields and resetting {@code isDeleted} to {@code false}.
   *
   * @param id the identifier of the entity to restore
   */
  void restoreById(I id);
}
