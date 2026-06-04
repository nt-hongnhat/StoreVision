package com.nthn.storevision.service.customer.impl;

import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.repository.CustomerRepository;
import com.nthn.storevision.service.customer.CustomerDeletionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link CustomerDeletionService} that soft-deletes
 * {@link CustomerEntity} instances by setting the {@code isDeleted} flag, which triggers
 * {@code CustomAuditingEntityListener} to populate {@code deletedBy} and {@code deletedDate}.
 */
@Service
@RequiredArgsConstructor
public class CustomerDeletionServiceImpl implements CustomerDeletionService {

  private final CustomerRepository repository;

  @Override
  @Transactional
  public void delete(final CustomerEntity entity) {
    entity.setDeleted(true);
    repository.save(entity);
  }

  @Override
  @Transactional
  public void markAsDeleted(final Long id) {
    repository.markDeletedById(id);
  }

  @Override
  @Transactional
  public void restore(final Long id) {
    repository.restoreById(id);
  }

  @Override
  @Transactional
  public void permanentlyDelete(final Long id) {
    repository.deleteById(id);
  }
}
