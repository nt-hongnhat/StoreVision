/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: PaginationUtil.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 15:46:52
 */

package com.nthn.storevision.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Helpers and shared defaults for request pagination. Out-of-range values are clamped instead of
 * rejected: a negative page becomes the first page, and the size is bounded to [1, {@value
 * #MAX_SIZE}].
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum PaginationUtil {
  ;

  /** Upper bound for the page size to protect the database from oversized requests. */
  private static final int MAX_SIZE = 100;

  private static final int MIN_SIZE = 1;

  /**
   * Builds an unsorted {@link Pageable} from raw request parameters, clamping out-of-range values.
   *
   * @param page zero-based page index; negative values are clamped to 0
   * @param size requested page size; clamped to [1, {@value #MAX_SIZE}]
   * @return the resulting {@link Pageable}; never {@code null}
   */
  @NonNull
  public static Pageable toPageRequest(final int page, final int size) {
    return toPageRequest(page, size, Sort.unsorted());
  }

  /**
   * Builds a {@link Pageable} from raw request parameters, clamping out-of-range values.
   *
   * @param page zero-based page index; negative values are clamped to 0
   * @param size requested page size; clamped to [1, {@value #MAX_SIZE}]
   * @param sort the sort to apply; must not be {@code null}
   * @return the resulting {@link Pageable}; never {@code null}
   */
  @NonNull
  public static Pageable toPageRequest(final int page, final int size, final Sort sort) {
    return PageRequest.of(Math.max(page, 0), Math.clamp(size, MIN_SIZE, MAX_SIZE), sort);
  }
}
