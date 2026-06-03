package com.nthn.storevision.service.product.impl;

import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.service.common.AbstractCreatable;
import com.nthn.storevision.service.product.ProductCreationService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link ProductCreationService} that extends {@link AbstractCreatable}
 * to persist {@link ProductEntity} instances via {@link JpaRepository}.
 */
@Service
public class ProductCreationServiceImpl
    extends AbstractCreatable<ProductEntity, Long, ProductEntity>
    implements ProductCreationService {

  public ProductCreationServiceImpl(final JpaRepository<ProductEntity, Long> repository) {
    super(repository);
  }
}
