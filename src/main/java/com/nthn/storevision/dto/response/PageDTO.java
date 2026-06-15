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

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Stable pagination envelope returned by list endpoints.")
public record PageDTO<T>(
    @Schema(description = "The elements of the current page.") List<T> content,
    @Schema(description = "Zero-based index of the current page.", example = "0") int page,
    @Schema(description = "Requested page size.", example = "20") int size,
    @Schema(description = "Total number of matching elements across all pages.", example = "42")
        long totalElements,
    @Schema(description = "Total number of pages.", example = "3") int totalPages) {

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
