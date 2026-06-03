package com.nthn.storevision.service.product.impl;

import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.service.product.ProductUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link ProductUpdateService} for persisting updates to
 * {@link ProductEntity} instances in the database.
 */
@Service
@RequiredArgsConstructor
public class ProductUpdateServiceImpl implements ProductUpdateService {

  private final JpaRepository<ProductEntity, Long> repository;

  @Transactional
  @Override
  public void update(final ProductEntity product) {
    repository.save(product);
  }
}
