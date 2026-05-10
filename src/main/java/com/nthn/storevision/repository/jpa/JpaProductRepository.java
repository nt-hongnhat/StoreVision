package com.nthn.storevision.repository.jpa;

import com.nthn.storevision.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for {@link ProductEntity}.
 * Provides standard JPA operations for Product entities.
 */
@Repository
public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {
}
