package com.nthn.storevision.controller.v1;

import com.nthn.storevision.constant.api.ResponseCode;
import com.nthn.storevision.constant.api.ResponseDescription;
import com.nthn.storevision.controller.CustomerController;
import com.nthn.storevision.dto.CustomerDTO;
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

/**
 * Version 1 implementation of {@link CustomerController}.
 */
@Tag(name = "Customer", description = "Customer management endpoints")
@Slf4j
@RestController
@RequestMapping(value = "/customers", version = "v1")
@RequiredArgsConstructor
public class CustomerControllerV1 implements CustomerController {

  private static final String ID_DESC = "Customer ID";

  private final com.nthn.storevision.service.customer.CustomerService customerService;

  @Operation(
      summary = "Create a new customer",
      description = "Creates a new customer record and persists it to the database.")
  @ApiResponse(responseCode = ResponseCode.CREATED, description = ResponseDescription.CREATED)
  @ApiResponse(responseCode = ResponseCode.BAD_REQUEST, description = ResponseDescription.INVALID_INPUT)
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  @Override
  public CustomerDTO createCustomer(@Valid @RequestBody final CustomerDTO dto) {
    log.debug("POST /customers");
    return customerService.create(dto);
  }

  @Operation(
      summary = "Soft-delete a customer by ID",
      description = "Marks the customer as deleted without physically removing the record."
          + " The record can be restored later.")
  @ApiResponse(responseCode = ResponseCode.NO_CONTENT, description = ResponseDescription.DELETED)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  @Override
  public void deleteCustomer(
      @Parameter(description = ID_DESC) @PathVariable final Long id) {
    log.debug("DELETE /customers/{}", id);
    customerService.delete(id);
  }

  @Operation(
      summary = "Retrieve all customers",
      description = "Returns a list of all active (non-deleted) customer records.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @GetMapping
  @Override
  public List<CustomerDTO> getAllCustomers() {
    log.debug("GET /customers");
    return customerService.findAll();
  }

  @Operation(
      summary = "Retrieve a customer by ID",
      description = "Returns the customer record with the specified ID.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @GetMapping("/{id}")
  @Override
  public CustomerDTO getCustomerById(
      @Parameter(description = ID_DESC) @PathVariable final Long id) {
    log.debug("GET /customers/{}", id);
    return customerService.findById(id);
  }

  @Operation(
      summary = "Update a customer by ID",
      description = "Updates the mutable fields (name, email, phone, address) of an existing customer.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.BAD_REQUEST, description = ResponseDescription.INVALID_INPUT)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @PutMapping("/{id}")
  @Override
  public CustomerDTO updateCustomer(
      @Parameter(description = ID_DESC) @PathVariable final Long id,
      @Valid @RequestBody final CustomerDTO dto) {
    log.debug("PUT /customers/{}", id);
    return customerService.update(id, dto);
  }
}
