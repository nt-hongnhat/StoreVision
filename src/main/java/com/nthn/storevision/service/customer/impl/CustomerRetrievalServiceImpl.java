package com.nthn.storevision.service.customer.impl;

import com.nthn.storevision.dto.CustomerDTO;
import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.mapper.CustomerMapper;
import com.nthn.storevision.repository.CustomerRepository;
import com.nthn.storevision.service.common.AbstractReadable;
import com.nthn.storevision.service.customer.CustomerRetrievalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Service for retrieving {@link CustomerEntity} entities. Extends {@link AbstractReadable} to provide read
 * operations for {@link CustomerEntity} entities.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerRetrievalServiceImpl
    extends AbstractReadable<CustomerEntity, Long, CustomerRepository>
    implements CustomerRetrievalService {

  private final CustomerMapper customerMapper;

  /**
   * Constructs a {@code CustomerRetrievalServiceImpl} with the specified repository and mapper.
   *
   * @param repository the repository used for accessing {@link CustomerEntity} entities
   * @param customerMapper the mapper used for converting between entity and DTO
   */
  public CustomerRetrievalServiceImpl(final CustomerRepository repository, final CustomerMapper customerMapper) {
    super(repository);
    this.customerMapper = customerMapper;
  }

  /**
   * Retrieves all customers and maps them to {@link CustomerDTO}.
   *
   * @return a list of all {@link CustomerDTO}s.
   */
  @Override
  @Transactional(readOnly = true)
  public List<CustomerDTO> findAllCustomersAsDTO() {
    log.debug("Fetching all customers as DTO");
    return findAll(customerMapper::toDTO);
  }

  /**
   * Retrieves a customer by its identifier and maps it to {@link CustomerDTO}.
   *
   * @param id the identifier of the customer to be retrieved.
   * @return the {@link CustomerDTO} with the specified identifier, or null if not found.
   */
  @Override
  @Transactional(readOnly = true)
  public CustomerDTO findCustomerDTOById(Long id) {
    log.debug("Fetching customer DTO by id: {}", id);
    return findById(id, customerMapper::toDTO);
  }

  /**
   * Finds a customer by their email address and maps the result using the provided mapping function.
   * This operation is read-only and transactional.
   *
   * @param email the email address of the customer to retrieve
   * @param mapper a function to map the retrieved customer instance into the desired type
   * @param <T> the return type after applying the mapper function
   * @return an {@code Optional} containing the mapped result if the customer is found, or an empty
   *     {@code Optional} if no customer corresponds to the given email
   */
  @Override
  @Transactional(readOnly = true)
  public <T> Optional<T> findByEmail(String email, Function<? super CustomerEntity, T> mapper) {
    log.debug("Fetching customer by email: {}", email);
    return getRepository().findByEmail(email).map(mapper);
  }

  /**
   * Returns the message code for when a customer is not found.
   *
   * @return The message code for "customer not found".
   */
  @Override
  protected String getNotFoundMessageCode() {
    // Placeholder, replace with actual message code if available
    return "customer.not.found";
  }
}
