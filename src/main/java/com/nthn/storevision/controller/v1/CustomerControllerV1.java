/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerControllerV1.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 22:06:27
 */

package com.nthn.storevision.controller.v1;

import com.nthn.storevision.constant.api.ResponseCode;
import com.nthn.storevision.constant.api.ResponseDescription;
import com.nthn.storevision.controller.CustomerController;
import com.nthn.storevision.dto.CustomerDTO;
import com.nthn.storevision.dto.LoyaltyDetailsDTO;
import com.nthn.storevision.dto.OrderDTO;
import com.nthn.storevision.dto.VoucherDTO;
import com.nthn.storevision.dto.request.RedeemPointsRequest;
import com.nthn.storevision.service.customer.CustomerCreationService;
import com.nthn.storevision.service.customer.CustomerDeletionService;
import com.nthn.storevision.service.customer.CustomerRetrievalService;
import com.nthn.storevision.service.customer.CustomerUpdateService;
import com.nthn.storevision.service.customer.LoyaltyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Version 1 implementation of {@link CustomerController}. */
@Tag(name = "Customer", description = "Customer management endpoints")
@Slf4j
@RestController
@RequestMapping(value = "/customers", version = "v1")
@RequiredArgsConstructor
public class CustomerControllerV1 implements CustomerController {

  private static final String EMAIL_EXISTS_DESC =
      "Customer email already exists, including on soft-deleted records (BR-C01)";
  private static final String HAS_PENDING_ORDERS_DESC =
      "Customer still has PENDING orders and cannot be deleted (BR-C02)";
  private static final String INSUFFICIENT_POINTS_DESC =
      "Requested points exceed the customer's current balance (BR-C03)";
  private static final String LOYALTY_DESC =
      "Returns the customer's loyalty tier, point balance, total spending and the immutable point"
          + " transaction history (BR-C05).";
  private static final String REDEEM_DESC =
      "Redeems loyalty points for a single-use FIXED_AMOUNT voucher. The redemption is rejected"
          + " when it exceeds the current point balance — the balance can never go negative"
          + " (BR-C03). Tier and total spending are not affected (BR-C04).";
  private final CustomerCreationService creationService;
  private final CustomerDeletionService deletionService;
  private final LoyaltyService loyaltyService;
  private final CustomerRetrievalService retrievalService;
  private final CustomerUpdateService updateService;

  /** {@inheritDoc} */
  @Operation(
      summary = "Create a new customer",
      description =
          "Creates a new customer record. The email must be unique across the whole system,"
              + " including soft-deleted records (BR-C01). New customers start at BRONZE tier"
              + " with zero points.")
  @ApiResponse(responseCode = ResponseCode.CREATED, description = ResponseDescription.CREATED)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.INVALID_INPUT)
  @ApiResponse(responseCode = ResponseCode.CONFLICT, description = EMAIL_EXISTS_DESC)
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  @Override
  public CustomerDTO createCustomer(@Valid @RequestBody final CustomerDTO dto) {
    log.debug("POST /customers");
    return creationService.create(dto);
  }

  /** {@inheritDoc} */
  @Operation(
      summary = "Soft-delete a customer by ID",
      description =
          "Marks the customer as deleted without physically removing the record."
              + " The record can be restored later."
              + " Rejected while the customer still has PENDING orders (BR-C02).")
  @ApiResponse(responseCode = ResponseCode.NO_CONTENT, description = ResponseDescription.DELETED)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @ApiResponse(responseCode = ResponseCode.CONFLICT, description = HAS_PENDING_ORDERS_DESC)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  @Override
  public void deleteCustomer(@Parameter(description = ResponseDescription.CUSTOMER_ID) @PathVariable final Long id) {
    log.debug("DELETE /customers/{}", id);
    deletionService.markAsDeleted(id);
  }

  @Operation(
      summary = "Retrieve all customers",
      description = "Returns a list of all active (non-deleted) customer records.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @GetMapping
  @Override
  public List<CustomerDTO> getAllCustomers() {
    log.debug("GET /customers");
    return retrievalService.findAllCustomersAsDTO();
  }

  @Operation(
      summary = "Retrieve a customer by ID",
      description = "Returns the customer record with the specified ID.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @GetMapping("/{id}")
  @Override
  public CustomerDTO getCustomerById(
      @Parameter(description = ResponseDescription.CUSTOMER_ID) @PathVariable final Long id) {
    log.debug("GET /customers/{}", id);
    return retrievalService.findCustomerDTOById(id);
  }

  /** {@inheritDoc} */
  @Operation(
      summary = "Retrieve a customer's order history",
      description = "Returns all active orders of the customer, most recent first.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @GetMapping("/{id}/orders")
  @Override
  public List<OrderDTO> getCustomerOrders(
      @Parameter(description = ResponseDescription.CUSTOMER_ID) @PathVariable final Long id) {
    log.debug("GET /customers/{}/orders", id);
    return retrievalService.findOrderHistory(id);
  }

  /** {@inheritDoc} */
  @Operation(summary = "Retrieve a customer's loyalty details", description = LOYALTY_DESC)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @GetMapping("/{id}/loyalty")
  @Override
  public LoyaltyDetailsDTO getLoyaltyDetails(
      @Parameter(description = ResponseDescription.CUSTOMER_ID) @PathVariable final Long id) {
    log.debug("GET /customers/{}/loyalty", id);
    return loyaltyService.getLoyaltyDetails(id);
  }

  /** {@inheritDoc} */
  @Operation(summary = "Redeem loyalty points for a voucher", description = REDEEM_DESC)
  @ApiResponse(responseCode = ResponseCode.CREATED, description = ResponseDescription.CREATED)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.INVALID_INPUT)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @ApiResponse(
      responseCode = ResponseCode.UNPROCESSABLE_ENTITY,
      description = INSUFFICIENT_POINTS_DESC)
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/{id}/loyalty/redeem")
  @Override
  public VoucherDTO redeemPoints(
      @Parameter(description = ResponseDescription.CUSTOMER_ID) @PathVariable final Long id,
      @Valid @RequestBody final RedeemPointsRequest request) {
    log.debug("POST /customers/{}/loyalty/redeem — points: {}", id, request.getPoints());
    return loyaltyService.redeemPoints(id, request.getPoints());
  }

  @Operation(
      summary = "Update a customer by ID",
      description =
          "Updates the mutable fields (name, email, phone, address) of an existing customer.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.INVALID_INPUT)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @PutMapping("/{id}")
  @Override
  public CustomerDTO updateCustomer(
      @Parameter(description = ResponseDescription.CUSTOMER_ID) @PathVariable final Long id,
      @Valid @RequestBody final CustomerDTO dto) {
    log.debug("PUT /customers/{}", id);
    return updateService.update(id, dto);
  }
}
