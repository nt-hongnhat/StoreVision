package com.nthn.storevision.repository.jpa;

import com.nthn.storevision.entity.ProductEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** JPA Repository for {@link ProductEntity}. Provides standard and product-specific queries. */
@Repository
public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {

  /**
   * BR-O01: atomically decrements a product's stock by {@code quantity} only when enough stock
   * remains, in a single guarded UPDATE statement. Two concurrent order completions can therefore
   * never drive the stock negative. Bulk update — auditing fields are untouched; the change is
   * audited through the ISSUE stock movement record (BR-I01).
   *
   * @param id the product to update; must not be {@code null}
   * @param quantity units to deduct; must be positive
   * @return the number of rows updated: 1 on success, 0 when the product does not exist or the
   *     remaining stock is not enough
   */
  @Modifying
  @Query(
      "UPDATE ProductEntity p SET p.stockQuantity = p.stockQuantity - :quantity"
          + " WHERE p.id = :id AND p.stockQuantity >= :quantity")
  int decreaseStockIfAvailable(@Param("id") Long id, @Param("quantity") Integer quantity);

  /**
   * BR-P01: checks uniqueness of name + category among active (non-soft-deleted) records.
   *
   * @param category the category to check; must not be {@code null}
   * @param name the name to check; must not be {@code null}
   * @return {@code true} if the name + category combination is unique among active products
   */
  boolean existsByNameAndCategoryAndDeletedDateIsNull(String name, String category);

  /**
   * BR-P01 (update path): same check but excludes the record being updated to allow renaming to the
   * same value.
   *
   * @param category the category to check; must not be {@code null}
   * @param id the product to exclude from the check; must not be {@code null}
   * @param name the name to check; must not be {@code null}
   * @return {@code true} if the name + category combination is unique among active products
   *     excluding the product with the given ID
   */
  boolean existsByNameAndCategoryAndDeletedDateIsNullAndIdNot(
      String name, String category, Long id);

  /**
   * Returns all non-soft-deleted products.
   *
   * @return all non-soft-deleted products
   */
  List<ProductEntity> findByDeletedDateIsNull();

  /**
   * BR-I04: returns active products with stock at or below the threshold.
   *
   * @param threshold the stock threshold
   * @return active products with stock at or below the threshold
   */
  List<ProductEntity> findByStockQuantityLessThanEqualAndDeletedDateIsNull(int threshold);

  /**
   * Atomically increments a product's stock by {@code quantity} in a single UPDATE statement,
   * preventing lost updates from concurrent receipts. Bulk update — auditing fields are untouched;
   * the change is audited through the stock movement record (BR-I01).
   *
   * @param id the product to update; must not be {@code null}
   * @param quantity units to add; must be positive
   * @return the number of rows updated: 1 on success, 0 when the product does not exist
   */
  @Modifying
  @Query(
      "UPDATE ProductEntity p SET p.stockQuantity = p.stockQuantity + :quantity"
          + " WHERE p.id = :id")
  int increaseStock(@Param("id") Long id, @Param("quantity") Integer quantity);

  /**
   * Full-text–style search over active products. Both parameters are optional: passing {@code null}
   * skips that filter entirely.
   *
   * @param category the category to search for; may be {@code null}
   * @param name the name to search for; may be {@code null}
   * @return active products matching the search criteria
   */
  @Query(
      "SELECT p FROM ProductEntity p WHERE p.deletedDate IS NULL"
          + " AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))"
          + " AND (:category IS NULL OR p.category = :category)")
  List<ProductEntity> searchByNameAndCategory(
      @Param("name") String name, @Param("category") String category);
}
