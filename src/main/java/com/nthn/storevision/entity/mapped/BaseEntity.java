/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: BaseEntity.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02 21:45:51
 */

package com.nthn.storevision.entity.mapped;

import com.nthn.storevision.annotation.DeletedBy;
import com.nthn.storevision.annotation.DeletedDate;
import com.nthn.storevision.component.audit.CustomAuditingEntityListener;
import com.nthn.storevision.entity.UserEntity;
import com.nthn.storevision.model.CustomAuditable;
import com.nthn.storevision.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.jspecify.annotations.NonNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * Represents a base entity class that provides common properties and behaviors for all entities in
 * the Book Thymeleaf application. This class implements the {@link CustomAuditable} interface and
 * serves as a superclass for other entity classes.
 *
 * @param <I> the type of the entity's identifier
 */
@MappedSuperclass
@EntityListeners(CustomAuditingEntityListener.class)
@Getter
@Setter
@ToString
@NoArgsConstructor
public abstract class BaseEntity<I> implements CustomAuditable<I> {
  /** The user who created this entity. */
  @CreatedBy
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "created_by", nullable = false)
  @ToString.Exclude
  private UserEntity createdBy;

  /** The date and time when this entity was created. */
  @CreatedDate
  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "created_at")
  private LocalDateTime createdDate;

  /** The user who deleted this entity. */
  @DeletedBy
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "deleted_by")
  @ToString.Exclude
  private UserEntity deletedBy;

  /** The date and time when this entity was deleted. */
  @DeletedDate
  @Column(name = "deleted_at")
  private LocalDateTime deletedDate;

  /** A transient field indicating whether this entity is marked as deleted. */
  @Transient private boolean isDeleted;

  /** The user who last modified this entity. */
  @LastModifiedBy
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "last_modified_by", nullable = false)
  @ToString.Exclude
  private UserEntity lastModifiedBy;

  /** The date and time when this entity was last modified. */
  @LastModifiedDate
  @Column(name = "last_modified_at")
  private LocalDateTime lastModifiedDate;

  /**
   * Returns the user who created this entity.
   *
   * @return An {@link Optional} containing the {@link User} who created this entity.
   */
  @NonNull
  @Override
  public Optional<User> getCreatedBy() {
    return Optional.of(createdBy);
  }

  /**
   * Sets the user who created this entity.
   *
   * @param createdBy The {@link User} who created this entity.
   */
  @Override
  public void setCreatedBy(@NonNull final User createdBy) {
    this.createdBy = (UserEntity) createdBy;
  }

  /**
   * Returns the date and time when this entity was created.
   *
   * @return An {@link Optional} containing the {@link LocalDateTime} when this entity was created.
   */
  @NonNull
  @Override
  public Optional<LocalDateTime> getCreatedDate() {
    return Optional.of(createdDate);
  }

  /**
   * Sets the date and time when this entity was created.
   *
   * @param createdDate The {@link LocalDateTime} when this entity was created.
   */
  @Override
  public void setCreatedDate(@NonNull final LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }

  /**
   * Returns the user who last modified this entity.
   *
   * @return An {@link Optional} containing the {@link User} who last modified this entity.
   */
  @NonNull
  @Override
  public Optional<User> getLastModifiedBy() {
    return Optional.of(lastModifiedBy);
  }

  /**
   * Sets the user who last modified this entity.
   *
   * @param lastModifiedBy The {@link User} who last modified this entity.
   */
  @Override
  public void setLastModifiedBy(@NonNull final User lastModifiedBy) {
    this.lastModifiedBy = (UserEntity) lastModifiedBy;
  }

  /**
   * Returns the date and time when this entity was last modified.
   *
   * @return An {@link Optional} containing the {@link LocalDateTime} when this entity was last
   *     modified.
   */
  @NonNull
  @Override
  public Optional<LocalDateTime> getLastModifiedDate() {
    return Optional.of(lastModifiedDate);
  }

  /**
   * Sets the date and time when this entity was last modified.
   *
   * @param lastModifiedDate The {@link LocalDateTime} when this entity was last modified.
   */
  @Override
  public void setLastModifiedDate(@NonNull final LocalDateTime lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  /**
   * Returns the user who deleted this entity.
   *
   * @return An {@link Optional} containing the {@link User} who deleted this entity.
   */
  @Override
  public Optional<User> getDeletedBy() {
    return Optional.ofNullable(deletedBy);
  }

  /**
   * Sets the user who deleted this entity.
   *
   * @param deletedBy The {@link User} who deleted this entity.
   * @return This {@link CustomAuditable} instance for method chaining.
   */
  @Override
  public CustomAuditable<I> setDeletedBy(final User deletedBy) {
    this.deletedBy = (UserEntity) deletedBy;
    return this;
  }

  /**
   * Returns the date and time when this entity was deleted.
   *
   * @return An {@link Optional} containing the {@link LocalDateTime} when this entity was deleted.
   */
  @Override
  public Optional<LocalDateTime> getDeletedDate() {
    return Optional.ofNullable(deletedDate);
  }

  /**
   * Sets the date and time when this entity was deleted.
   *
   * @param deletedDate The {@link LocalDateTime} when this entity was deleted.
   * @return This {@link CustomAuditable} instance for method chaining.
   */
  @Override
  public CustomAuditable<I> setDeletedDate(final LocalDateTime deletedDate) {
    this.deletedDate = deletedDate;
    return this;
  }

  /**
   * Checks if this entity is new (i.e., has not yet been persisted).
   *
   * @return {@code true} if the entity's ID is null, {@code false} otherwise.
   */
  @Override
  public boolean isNew() {
    return null == getId();
  }
}
