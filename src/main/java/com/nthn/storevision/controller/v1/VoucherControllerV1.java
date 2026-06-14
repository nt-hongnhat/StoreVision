/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherControllerV1.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 12:35:11
 */

package com.nthn.storevision.controller.v1;

import com.nthn.storevision.constant.api.ResponseCode;
import com.nthn.storevision.constant.api.ResponseDescription;
import com.nthn.storevision.controller.VoucherController;
import com.nthn.storevision.dto.VoucherDTO;
import com.nthn.storevision.dto.request.VoucherRestoreRequest;
import com.nthn.storevision.dto.response.PageDTO;
import com.nthn.storevision.service.voucher.VoucherCreationService;
import com.nthn.storevision.service.voucher.VoucherDeletionService;
import com.nthn.storevision.service.voucher.VoucherRetrievalService;
import com.nthn.storevision.service.voucher.VoucherUpdateService;
import com.nthn.storevision.util.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Version 1 implementation of {@link VoucherController}. */
@Tag(name = "Voucher", description = "Voucher management endpoints")
@Slf4j
@RestController
@RequestMapping(value = "/vouchers", version = "v1")
@RequiredArgsConstructor
public class VoucherControllerV1 implements VoucherController {

  private final VoucherCreationService creationService;
  private final VoucherDeletionService deletionService;
  private final VoucherRetrievalService retrievalService;
  private final VoucherUpdateService updateService;

  /** {@inheritDoc} */
  @Operation(summary = "Create a new voucher", description = "Creates a new promotional voucher.")
  @ApiResponse(responseCode = ResponseCode.CREATED, description = ResponseDescription.CREATED)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.INVALID_INPUT)
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  @Override
  public VoucherDTO createVoucher(@Valid @RequestBody final VoucherDTO dto) {
    log.debug("POST /vouchers");
    return creationService.create(dto);
  }

  /** {@inheritDoc} */
  @Operation(
      summary = "Retrieve vouchers (paginated)",
      description = "Returns one page of active (non-deleted) voucher records, most recent first.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @GetMapping
  @Override
  public PageDTO<VoucherDTO> getAllVouchers(
      @Parameter(description = ResponseDescription.PAGE_INDEX)
          @RequestParam(defaultValue = PaginationUtil.DEFAULT_PAGE)
          final int page,
      @Parameter(description = ResponseDescription.PAGE_SIZE)
          @RequestParam(defaultValue = PaginationUtil.DEFAULT_SIZE)
          final int size) {
    log.debug("GET /vouchers — page: {}, size: {}", page, size);
    return retrievalService.findVouchersPage(PaginationUtil.toPageRequest(page, size));
  }

  /** {@inheritDoc} */
  @Operation(
      summary = "Retrieve a voucher by ID",
      description = "Returns the voucher record with the specified ID.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @GetMapping("/{id}")
  @Override
  public VoucherDTO getVoucherById(
      @Parameter(description = ResponseDescription.VOUCHER_ID) @PathVariable final Long id) {
    log.debug("GET /vouchers/{}", id);
    return retrievalService.findById(id);
  }

  /** {@inheritDoc} */
  @Operation(summary = "Update a voucher by ID", description = "Updates an existing voucher.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.INVALID_INPUT)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @PutMapping("/{id}")
  @Override
  public VoucherDTO updateVoucher(
      @Parameter(description = ResponseDescription.VOUCHER_ID) @PathVariable final Long id,
      @Valid @RequestBody final VoucherDTO dto) {
    log.debug("PUT /vouchers/{}", id);
    return updateService.update(id, dto);
  }

  /** {@inheritDoc} */
  @Operation(
      summary = "Soft-delete a voucher by ID",
      description =
          "Marks the voucher as deleted without physically removing the record."
              + " The record can be restored later.")
  @ApiResponse(responseCode = ResponseCode.NO_CONTENT, description = ResponseDescription.DELETED)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  @Override
  public void deleteVoucher(
      @Parameter(description = ResponseDescription.VOUCHER_ID) @PathVariable final Long id) {
    log.debug("DELETE /vouchers/{}", id);
    deletionService.markAsDeleted(id);
  }

  /** {@inheritDoc} */
  @Operation(
      summary = "Restore a soft-deleted voucher by ID",
      description =
          "Clears the soft-delete markers, making the voucher active again. The request body's"
              + " `deleted` field must be `false`.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.RESTORED)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.INVALID_INPUT)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @PatchMapping("/{id}")
  @Override
  public VoucherDTO restoreVoucher(
      @Parameter(description = ResponseDescription.VOUCHER_ID) @PathVariable final Long id,
      @Valid @RequestBody final VoucherRestoreRequest request) {
    log.debug("PATCH /vouchers/{}", id);
    return deletionService.restore(id);
  }
}
