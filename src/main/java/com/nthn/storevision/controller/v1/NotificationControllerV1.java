/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: NotificationControllerV1.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 15:00:00
 */

package com.nthn.storevision.controller.v1;

import com.nthn.storevision.constant.api.ResponseCode;
import com.nthn.storevision.constant.api.ResponseDescription;
import com.nthn.storevision.controller.NotificationController;
import com.nthn.storevision.dto.NotificationDTO;
import com.nthn.storevision.dto.response.PageDTO;
import com.nthn.storevision.service.notification.NotificationRetrievalService;
import com.nthn.storevision.service.notification.NotificationUpdateService;
import com.nthn.storevision.util.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Version 1 implementation of {@link NotificationController}. */
@Tag(name = "Notification", description = "Internal notification endpoints")
@Slf4j
@RestController
@RequestMapping(value = "/notifications", version = "v1")
@RequiredArgsConstructor
public class NotificationControllerV1 implements NotificationController {

  private static final String LIST_DESC =
      "Internal notifications (e.g. LOW_STOCK_ALERT raised by the scheduled job, section 3.2.3),"
          + " ordered by most recently created first.";

  private final NotificationRetrievalService retrievalService;
  private final NotificationUpdateService updateService;

  /** {@inheritDoc} */
  @Operation(summary = "List notifications (paginated)", description = LIST_DESC)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @GetMapping
  @Override
  public PageDTO<NotificationDTO> getNotifications(
      @Parameter(description = ResponseDescription.PAGE_INDEX)
          @RequestParam(defaultValue = PaginationUtil.DEFAULT_PAGE)
          final int page,
      @Parameter(description = ResponseDescription.PAGE_SIZE)
          @RequestParam(defaultValue = PaginationUtil.DEFAULT_SIZE)
          final int size) {
    log.debug("GET /notifications — page: {}, size: {}", page, size);
    return retrievalService.findNotifications(page, size);
  }

  /** {@inheritDoc} */
  @Operation(
      summary = "Mark a notification as read",
      description = "Sets the read flag of the specified notification to true.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @PatchMapping("/{id}/read")
  @Override
  public NotificationDTO markNotificationAsRead(
      @Parameter(description = ResponseDescription.NOTIFICATION_ID) @PathVariable final Long id) {
    log.debug("PATCH /notifications/{}/read", id);
    return updateService.markAsRead(id);
  }
}
