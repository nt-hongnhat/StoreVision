package com.nthn.storevision.repository.impl;

import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.repository.ProductRepository;
import com.nthn.storevision.repository.jpa.JpaProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link ProductRepository} interface.
 * This class acts as an adapter, delegating calls to the underlying {@link JpaProductRepository}.
 */
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

  private final JpaProductRepository jpaRepository;

  /**
   * Deletes a product by its ID.
   *
   * @param id The ID of the product to delete.
   */
  @Override
  public void deleteById(final Long id) {
    jpaRepository.deleteById(id);
  }

  /**
   * Checks if a product with the given ID exists.
   *
   * @param id The ID of the product to check.
   * @return {@code true} if a product with the given ID exists, {@code false} otherwise.
   */
  @Override
  public boolean existsById(final Long id) {
    return jpaRepository.existsById(id);
  }

  /**
   * Retrieves all products.
   *
   * @return A list of all {@link ProductEntity} instances.
   */
  @Override
  public List<ProductEntity> findAll() {
    return jpaRepository.findAll();
  }

  /**
   * Retrieves a product by its ID.
   *
   * @param id The ID of the product to retrieve.
   * @return An {@link Optional} containing the {@link ProductEntity} if found, or empty if not.
   */
  @Override
  public Optional<ProductEntity> findById(final Long id) {
    return jpaRepository.findById(id);
  }

  /**
   * Saves a product entity.
   *
   * @param product The {@link ProductEntity} to save.
   * @return The saved {@link ProductEntity}.
   */
  @Override
  public ProductEntity save(final ProductEntity product) {
    return jpaRepository.save(product);
  }

  @Override
  public void markDeletedById(final Long id) {
    findById(id).ifPresent(entity -> {
      entity.setDeleted(true);
      save(entity);
    });
  }

  @Override
  public void restoreById(final Long id) {
    findById(id).ifPresent(entity -> {
      entity.setDeletedBy(null);
      entity.setDeletedDate(null);
      entity.setDeleted(false);
      save(entity);
    });
  }
}
