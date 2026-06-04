/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ApiResponseHandler.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 17:21:41
 */

package com.nthn.storevision.advice;

import com.nthn.storevision.dto.response.ApiResponse;
import com.nthn.storevision.dto.response.Error;
import com.nthn.storevision.factory.ApiResponseFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Handles API responses by wrapping them in a standardized {@link ApiResponse} format. This class
 * implements {@link ResponseBodyAdvice} to modify the response body before it is sent to the
 * client.
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiResponseHandler implements ResponseBodyAdvice<Object> {

  private final ApiResponseFactory apiErrorResponseFactory;
  private final ApiResponseFactory apiSuccessResponseFactory;
  private final SpringDocConfigProperties springDocConfigProperties;

  @Override
  public boolean supports(
      @NonNull final MethodParameter returnType,
      @NonNull final Class<? extends HttpMessageConverter<?>> converterType) {

    return !returnType.getParameterType().equals(ResponseEntity.class);
  }

  @SuppressWarnings("MethodParameterNamingConvention")
  @Override
  public Object beforeBodyWrite(
      final Object body,
      @NonNull final MethodParameter returnType,
      @NonNull final MediaType selectedContentType,
      @NonNull final Class<? extends HttpMessageConverter<?>> selectedConverterType,
      @NonNull final ServerHttpRequest request,
      @NonNull final ServerHttpResponse response) {
    final String path = request.getURI().getPath();
    log.debug("Wrapping response body for path: {}", path);

    if (path.contains(springDocConfigProperties.getApiDocs().getPath())) {
      return body;
    }

    return switch (body) {
      case final ApiResponse apiResponse -> apiResponse;
      case final Throwable exception ->
          apiErrorResponseFactory.create(path, new Error(null, exception.getMessage()));
      case final Error error -> apiErrorResponseFactory.create(path, error);
      case null, default -> apiSuccessResponseFactory.create(path, body);
    };
  }
}
