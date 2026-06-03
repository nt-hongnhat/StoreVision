package com.nthn.storevision.repository;

import java.util.List;
import java.util.Optional;

/**
 * Minimal read contract shared by all domain repository interfaces. {@link
 * com.nthn.storevision.service.common.AbstractReadable} depends on this interface rather than
 * {@code JpaRepository} so that the service layer stays decoupled from Spring Data JPA.
 *
 * @param <E> the entity type
 * @param <I> the identifier type
 */
public interface ReadRepository<E, I> {

  List<E> findAll();

  Optional<E> findById(I id);
}
