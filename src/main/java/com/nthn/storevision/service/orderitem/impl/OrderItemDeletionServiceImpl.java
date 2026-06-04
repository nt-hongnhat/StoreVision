package com.nthn.storevision.service.orderitem.impl;

import com.nthn.storevision.entity.OrderItemEntity;
import com.nthn.storevision.repository.OrderItemRepository;
import com.nthn.storevision.service.orderitem.OrderItemDeletionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link OrderItemDeletionService} that soft-deletes
 * {@link OrderItemEntity} instances by setting the {@code isDeleted} flag, which triggers
 * {@code CustomAuditingEntityListener} to populate {@code deletedBy} and {@code deletedDate}.
 */
@Service
@RequiredArgsConstructor
public class OrderItemDeletionServiceImpl implements OrderItemDeletionService {

  private final OrderItemRepository repository;

  @Override
  @Transactional
  public void delete(final OrderItemEntity entity) {
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
