/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: PaginationUtil.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 23:26:47
 */

package com.nthn.storevision.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Helpers and shared defaults for request pagination. Out-of-range values are clamped instead of
 * rejected: a negative page becomes the first page, and the size is bounded to [1, {@value
 * #MAX_SIZE}].
 */
@UtilityClass
public class PaginationUtil {

  /** Default zero-based page index, as a string for {@code @RequestParam(defaultValue)}. */
  public final String DEFAULT_PAGE = "0";

  /** Default page size, as a string for {@code @RequestParam(defaultValue)}. */
  public final String DEFAULT_SIZE = "20";

  /** Upper bound for the page size to protect the database from oversized requests. */
  private final int MAX_SIZE = 100;

  private final int MIN_SIZE = 1;

  /**
   * Builds a {@link Pageable} from raw request parameters, clamping out-of-range values.
   *
   * @param page zero-based page index; negative values are clamped to 0
   * @param size requested page size; clamped to [1, {@value #MAX_SIZE}]
   * @param sort the sort to apply; must not be {@code null}
   * @return the resulting {@link Pageable}; never {@code null}
   */
  public Pageable toPageRequest(final int page, final int size, final Sort sort) {
    return PageRequest.of(Math.max(page, 0), Math.clamp(size, MIN_SIZE, MAX_SIZE), sort);
  }
}
