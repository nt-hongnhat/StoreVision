package com.nthn.storevision.service.customer.impl;

import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.repository.CustomerRepository;
import com.nthn.storevision.service.common.AbstractCreatable;
import com.nthn.storevision.service.customer.CustomerCreationService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link CustomerCreationService} that extends {@link AbstractCreatable}
 * to persist {@link CustomerEntity} instances via {@link CustomerRepository}.
 */
@Service
public class CustomerCreationServiceImpl
    extends AbstractCreatable<CustomerEntity, Long, CustomerEntity>
    implements CustomerCreationService {

  public CustomerCreationServiceImpl(final CustomerRepository repository) {
    super(repository);
  }
}
