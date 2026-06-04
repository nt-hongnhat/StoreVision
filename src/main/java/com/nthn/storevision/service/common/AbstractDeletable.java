/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: AbstractDeletable.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 17:03:41
 */

package com.nthn.storevision.service.common;

import com.nthn.storevision.entity.mapped.BaseEntity;
import com.nthn.storevision.repository.generic.DeleteRepository;
import com.nthn.storevision.repository.generic.SaveRepository;
import com.nthn.storevision.service.generic.HardDeletable;
import com.nthn.storevision.service.generic.SoftDeletable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * Abstract base class providing a default implementation of both {@link SoftDeletable} and {@link
 * HardDeletable} for domain deletion services.
 *
 * <p>Delegates all operations to the domain repository:
 *
 * <ul>
 *   <li>{@link #delete(BaseEntity)} — sets {@code isDeleted = true} then saves; the {@code
 *       CustomAuditingEntityListener} populates {@code deletedBy}/{@code deletedDate} automatically
 *       on {@code @PreUpdate}.
 *   <li>{@link #markAsDeleted(Object)} — delegates to {@link DeleteRepository#markDeletedById}.
 *   <li>{@link #restore(Object)} — delegates to {@link DeleteRepository#restoreById}.
 *   <li>{@link #permanentlyDelete(Object)} — delegates to {@link DeleteRepository#deleteById}.
 * </ul>
 *
 * <p>The repository bound requires {@link SaveRepository} (for soft delete via save) and {@link
 * DeleteRepository} (for mark/restore/hard delete). All domain repositories that extend both
 * automatically satisfy this compound bound.
 *
 * @param <E> the JPA entity type, must extend {@link BaseEntity}
 * @param <I> the entity identifier type
 * @param <J> the domain repository type, must extend both {@link SaveRepository} and {@link
 *     DeleteRepository}
 */
@Slf4j
@RequiredArgsConstructor
@Getter
public abstract class AbstractDeletable<
        E extends BaseEntity<I>, I, J extends SaveRepository<E> & DeleteRepository<I>>
    implements SoftDeletable<E, I>, HardDeletable<E, I> {

  private final J repository;

  @Transactional
  @Override
  public void delete(final E entity) {
    log.debug("Soft-deleting entity: {}", entity);
    entity.setDeleted(true);
    repository.save(entity);
    log.debug("Entity soft-deleted: {}", entity);
  }

  @Transactional
  @Override
  public void markAsDeleted(final I id) {
    log.debug("Marking entity as deleted, id: {}", id);
    repository.markDeletedById(id);
    log.debug("Entity marked as deleted, id: {}", id);
  }

  @Transactional
  @Override
  public void restore(final I id) {
    log.debug("Restoring entity, id: {}", id);
    repository.restoreById(id);
    log.debug("Entity restored, id: {}", id);
  }

  @Transactional
  @Override
  public void permanentlyDelete(final I id) {
    log.debug("Permanently deleting entity, id: {}", id);
    repository.deleteById(id);
    log.debug("Entity permanently deleted, id: {}", id);
  }
}
