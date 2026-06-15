/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: NotificationControllerV1.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:55:06
 */

package com.nthn.storevision.controller.v1;

import com.nthn.storevision.constant.api.*;
import com.nthn.storevision.controller.NotificationController;
import com.nthn.storevision.dto.notification.NotificationDTO;
import com.nthn.storevision.dto.request.NotificationReadRequest;
import com.nthn.storevision.dto.response.PageDTO;
import com.nthn.storevision.service.notification.NotificationRetrievalService;
import com.nthn.storevision.service.notification.NotificationUpdateService;
import com.nthn.storevision.service.notification.impl.SseEmitterServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/** Version 1 implementation of {@link NotificationController}. */
@Tag(name = "Notification", description = "Internal notification endpoints")
@Slf4j
@RestController
@RequestMapping(value = EndpointDefinitions.NOTIFICATIONS, version = "v1")
@RequiredArgsConstructor
public class NotificationControllerV1 implements NotificationController {

  private final NotificationRetrievalService retrievalService;
  private final SseEmitterServiceImpl sseEmitterService;
  private final NotificationUpdateService updateService;

  /** {@inheritDoc} */
  @Operation(
      summary = "List notifications (paginated)",
      description = OperationDescription.LOW_STOCK_ALERT_NOTIFICATION)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @GetMapping
  @Override
  public PageDTO<NotificationDTO> getNotifications(
      @Parameter(description = ParameterDescription.PAGE_INDEX)
          @RequestParam(defaultValue = DefaultRequestParam.DEFAULT_PAGE)
          final int page,
      @Parameter(description = ParameterDescription.PAGE_SIZE)
          @RequestParam(defaultValue = DefaultRequestParam.DEFAULT_SIZE)
          final int size) {
    log.debug("GET /notifications — page: {}, size: {}", page, size);
    return retrievalService.findNotifications(page, size);
  }

  /** {@inheritDoc} */
  @Operation(
      summary = "Mark a notification as read",
      description = OperationDescription.READ_NOTIFICATION)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.INVALID_INPUT)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @PatchMapping("/{id}")
  @Override
  public NotificationDTO markNotificationAsRead(
      @Parameter(description = ParameterDescription.NOTIFICATION_ID) @PathVariable final Long id,
      @Valid @RequestBody final NotificationReadRequest request) {
    log.debug("PATCH /notifications/{}", id);
    return updateService.markAsRead(id);
  }

  @Operation(
      summary = "Stream real-time notifications",
      description = OperationDescription.SSE_CONNECTION)
  @ApiResponse(
      responseCode = ResponseCode.OK,
      description = ResponseDescription.SSE_CONNECTION_ESTABLISHED)
  @GetMapping("/stream")
  @Override
  public SseEmitter streamNotifications() {
    log.info("Client connected for SSE notification stream.");
    final SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
    sseEmitterService.addEmitter(emitter);
    return emitter;
  }
}
