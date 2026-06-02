/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomAuditable.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02 21:37:53
 */

package com.nthn.storevision.model;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Auditable;

/**
 * The {@code CustomAuditable} interface extends the {@link Auditable} interface to provide
 * additional auditing capabilities specific to the application's needs.
 *
 * <p>This interface adds methods to track the user who deleted an entity and the date when the
 * entity was deleted.
 *
 * @param <I> the type of the entity identifier
 */
public interface CustomAuditable<I> extends Auditable<User, I, LocalDateTime> {

  /**
   * Retrieves the user who deleted the entity.
   *
   * @return an {@link Optional} containing the user who deleted the entity, or an empty {@link
   *     Optional} if the entity hasn't been deleted.
   */
  Optional<User> getDeletedBy();

  /**
   * Sets the user who deleted the entity.
   *
   * @param deletedBy the user who deleted the entity
   * @return this entity with the deletedBy user set
   */
  CustomAuditable<I> setDeletedBy(User deletedBy);

  /**
   * Retrieves the date and time when the entity was deleted.
   *
   * @return an {@link Optional} containing the date and time when the entity was deleted, or an
   *     empty {@link Optional} if the entity hasn't been deleted.
   */
  Optional<LocalDateTime> getDeletedDate();

  /**
   * Sets the date when the entity was deleted.
   *
   * @param deletedDate the date and time when the entity was deleted
   * @return this entity with the deletedDate set
   */
  CustomAuditable<I> setDeletedDate(LocalDateTime deletedDate);

  /**
   * Checks if the entity has been marked as deleted.
   *
   * @return true if the entity is deleted, false otherwise.
   */
  boolean isDeleted();

  /**
   * Sets the ID of the entity.
   *
   * @param id the ID to set
   */
  CustomAuditable<I> setId(I id);
}
