package com.nthn.storevision.service.product.impl;

import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.repository.jpa.JpaProductRepository;
import com.nthn.storevision.service.product.ProductDeletionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link ProductDeletionService} that soft-deletes
 * {@link ProductEntity} instances by setting the {@code isDeleted} flag, which triggers
 * {@code CustomAuditingEntityListener} to populate {@code deletedBy} and {@code deletedDate}.
 */
@Service
@RequiredArgsConstructor
public class ProductDeletionServiceImpl implements ProductDeletionService {

  private final JpaProductRepository repository;

  @Override
  @Transactional
  public void delete(final ProductEntity entity) {
    entity.setDeleted(true);
    repository.save(entity);
  }

  @Override
  @Transactional
  public void markAsDeleted(final Long id) {
    repository.findById(id).ifPresent(entity -> {
      entity.setDeleted(true);
      repository.save(entity);
    });
  }

  @Override
  @Transactional
  public void restore(final Long id) {
    repository.findById(id).ifPresent(entity -> {
      entity.setDeletedBy(null);
      entity.setDeletedDate(null);
      entity.setDeleted(false);
      repository.save(entity);
    });
  }

  @Override
  @Transactional
  public void permanentlyDelete(final Long id) {
    repository.deleteById(id);
  }
}
