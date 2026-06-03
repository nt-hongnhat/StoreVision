package com.nthn.storevision.service.order.impl;

import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.repository.OrderRepository;
import com.nthn.storevision.service.order.OrderDeletionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link OrderDeletionService} that soft-deletes
 * {@link OrderEntity} instances by setting the {@code isDeleted} flag, which triggers
 * {@code CustomAuditingEntityListener} to populate {@code deletedBy} and {@code deletedDate}.
 */
@Service
@RequiredArgsConstructor
public class OrderDeletionServiceImpl implements OrderDeletionService {

  private final OrderRepository repository;

  @Override
  @Transactional
  public void delete(final OrderEntity entity) {
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
