/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductRepositoryImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 23:08:21
 */

package com.nthn.storevision.repository.impl;

import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.repository.ProductRepository;
import com.nthn.storevision.repository.jpa.JpaProductRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the {@link ProductRepository} interface. This class acts as an adapter,
 * delegating calls to the underlying {@link JpaProductRepository}.
 */
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

  private final JpaProductRepository jpaRepository;

  @Override
  public boolean decreaseStockIfAvailable(final Long id, final Integer quantity) {
    return 0 < jpaRepository.decreaseStockIfAvailable(id, quantity);
  }

  @Override
  public List<ProductEntity> findAllActive() {
    return jpaRepository.findByDeletedDateIsNull();
  }

  @Override
  public List<ProductEntity> findLowStock(final int threshold) {
    return jpaRepository.findByStockQuantityLessThanEqualAndDeletedDateIsNull(threshold);
  }

  @Override
  public boolean increaseStock(final Long id, final Integer quantity) {
    return 0 < jpaRepository.increaseStock(id, quantity);
  }

  @Override
  public boolean isProductActiveAndInCategory(final String name, final String category) {
    return jpaRepository.existsByNameAndCategoryAndDeletedDateIsNull(name, category);
  }

  @Override
  public boolean isProductActiveInCategoryExcludingId(
      final String name, final String category, final Long id) {
    return jpaRepository.existsByNameAndCategoryAndDeletedDateIsNullAndIdNot(name, category, id);
  }

  @Override
  public List<ProductEntity> searchActive(final String name, final String category) {
    return jpaRepository.searchByNameAndCategory(name, category);
  }

  @Override
  public void deleteById(final Long id) {
    jpaRepository.deleteById(id);
  }

  @Override
  public void markDeletedById(final Long id) {
    findById(id)
        .ifPresent(
            entity -> {
              entity.setDeleted(true);
              save(entity);
            });
  }

  @Override
  public void restoreById(final Long id) {
    findById(id)
        .ifPresent(
            entity -> {
              entity.setDeletedBy(null).setDeletedDate(null);
              entity.setDeleted(false);
              save(entity);
            });
  }

  @Override
  public List<ProductEntity> findAll() {
    return jpaRepository.findByDeletedDateIsNull();
  }

  @Override
  public Optional<ProductEntity> findById(final Long id) {
    return jpaRepository.findById(id);
  }

  @Override
  public boolean isExistsById(final Long id) {
    return jpaRepository.existsById(id);
  }

  @Override
  public ProductEntity save(final ProductEntity product) {
    return jpaRepository.save(product);
  }
}
