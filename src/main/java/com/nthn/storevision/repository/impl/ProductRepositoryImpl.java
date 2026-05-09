package com.nthn.storevision.repository.impl;

import com.nthn.storevision.entity.Product;
import com.nthn.storevision.repository.ProductRepository;
import com.nthn.storevision.repository.jpa.ProductJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

  private final ProductJpaRepository jpaRepository;

  public ProductRepositoryImpl(final ProductJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  @Override
  public void deleteById(final Long id) {
    jpaRepository.deleteById(id);
  }

  @Override
  public boolean existsById(final Long id) {
    return jpaRepository.existsById(id);
  }

  @Override
  public List<Product> findAll() {
    return jpaRepository.findAll();
  }

  @Override
  public Optional<Product> findById(final Long id) {
    return jpaRepository.findById(id);
  }

  @Override
  public Product save(final Product product) {
    return jpaRepository.save(product);
  }
}
