package com.nthn.storevision.repository.jpa;

import com.nthn.storevision.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** JPA Repository for {@link ProductEntity}. Provides standard and product-specific queries. */
@Repository
public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {

  /** BR-P01: checks uniqueness of name + category among active (non-soft-deleted) records. */
  boolean existsByNameAndCategoryAndDeletedDateIsNull(String name, String category);

  /**
   * BR-P01 (update path): same check but excludes the record being updated to allow renaming to the
   * same value.
   */
  boolean existsByNameAndCategoryAndDeletedDateIsNullAndIdNot(
      String name, String category, Long id);

  /** Returns all non-soft-deleted products. */
  List<ProductEntity> findByDeletedDateIsNull();

  /**
   * Full-text–style search over active products. Both parameters are optional: passing {@code null}
   * skips that filter entirely.
   */
  @Query(
      "SELECT p FROM ProductEntity p WHERE p.deletedDate IS NULL"
          + " AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))"
          + " AND (:category IS NULL OR p.category = :category)")
  List<ProductEntity> searchByNameAndCategory(
      @Param("name") String name, @Param("category") String category);
}
