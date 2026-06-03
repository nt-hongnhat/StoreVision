package com.nthn.storevision.controller;

import com.nthn.storevision.dto.CustomerDTO;
import jakarta.validation.Valid;
import java.util.List;

/**
 * API contract for customer management endpoints.
 *
 * @see com.nthn.storevision.controller.v1.CustomerControllerV1
 */
public interface CustomerController {

  /**
   * Creates a new customer.
   *
   * @param dto the customer data to persist
   * @return the created customer
   */
  CustomerDTO createCustomer(@Valid CustomerDTO dto);

  /**
   * Soft-deletes a customer by ID.
   *
   * @param id the ID of the customer to delete
   */
  void deleteCustomer(Long id);

  /**
   * Retrieves all active customers.
   *
   * @return list of all non-deleted customers
   */
  List<CustomerDTO> getAllCustomers();

  /**
   * Retrieves a customer by ID.
   *
   * @param id the ID of the customer to retrieve
   * @return the matching customer
   */
  CustomerDTO getCustomerById(Long id);

  /**
   * Updates an existing customer.
   *
   * @param id  the ID of the customer to update
   * @param dto the updated customer data
   * @return the updated customer
   */
  CustomerDTO updateCustomer(Long id, @Valid CustomerDTO dto);
}
