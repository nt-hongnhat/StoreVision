/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: PageDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 23:26:47
 */

package com.nthn.storevision.dto.response;

import java.util.List;

import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;

/**
 * Stable pagination envelope returned by list endpoints instead of exposing Spring Data's {@link
 * Page} implementation directly.
 *
 * @param <T> the element type of the page
 * @param content the elements of the current page; never {@code null}
 * @param page zero-based index of the current page
 * @param size requested page size
 * @param totalElements total number of matching elements across all pages
 * @param totalPages total number of pages
 */
public record PageDTO<T>(List<T> content, int page, int size, long totalElements, int totalPages) {

  /**
   * Converts a Spring Data {@link Page} into this stable envelope.
   *
   * @param source the page to convert; must not be {@code null}
   * @param <T> the element type of the page
   * @return the converted {@code PageDTO}; never {@code null}
   */
  public static <T> PageDTO<T> from(final @NonNull Page<T> source) {
    return new PageDTO<>(
        source.getContent(),
        source.getNumber(),
        source.getSize(),
        source.getTotalElements(),
        source.getTotalPages());
  }
}
