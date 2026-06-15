/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: StockMovement.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:51:59
 */

package com.nthn.storevision.model;

import com.nthn.storevision.constant.stock_movement.StockMovementType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents an immutable stock movement record in the StoreVision domain. Once created, a movement
 * cannot be deleted (BR-I03).
 *
 * <p>Unlike auditable entities, {@code StockMovement} does not extend {@link CustomAuditable} — the
 * {@link #getPerformedBy()} and {@link #getMovementDate()} fields serve as the audit trail and are
 * set at creation time.
 */
public interface StockMovement {

  /**
   * Gets the unique identifier of this stock movement.
   *
   * @return the movement id as a {@link Long}.
   */
  Long getId();

  /**
   * Sets the unique identifier of this stock movement.
   *
   * @param id the id to set.
   * @return the updated {@code StockMovement} instance.
   */
  StockMovement setId(Long id);

  /**
   * Gets the date and time at which this stock movement was recorded.
   *
   * @return the movement date as a {@link LocalDateTime}.
   */
  LocalDateTime getMovementDate();

  /**
   * Sets the date and time at which this stock movement was recorded.
   *
   * @param movementDate the movement date to set.
   * @return the updated {@code StockMovement} instance.
   */
  StockMovement setMovementDate(LocalDateTime movementDate);

  /**
   * Gets the optional note associated with this stock movement.
   *
   * @return the note as a {@link String}, or {@code null} if none was provided.
   */
  String getNote();

  /**
   * Sets an optional note for this stock movement.
   *
   * @param note the note to set; may be {@code null}.
   * @return the updated {@code StockMovement} instance.
   */
  StockMovement setNote(String note);

  /**
   * Gets the user who performed this stock movement (BR-I01).
   *
   * @return the performer as a {@link User} instance.
   */
  User getPerformedBy();

  /**
   * Sets the user who performed this stock movement.
   *
   * @param performedBy the user to set; must not be {@code null} (BR-I01).
   * @return the updated {@code StockMovement} instance.
   */
  StockMovement setPerformedBy(User performedBy);

  /**
   * Gets the product associated with this stock movement.
   *
   * @return the product as a {@link Product} instance.
   */
  Product getProduct();

  /**
   * Sets the product associated with this stock movement.
   *
   * @param product the product to set; must not be {@code null}.
   * @return the updated {@code StockMovement} instance.
   */
  StockMovement setProduct(Product product);

  /**
   * Gets the quantity of units involved in this stock movement. Always a positive value; direction
   * is encoded in {@link #getType()}.
   *
   * @return the quantity as a positive {@link Integer}.
   */
  Integer getQuantity();

  /**
   * Sets the quantity of units for this stock movement.
   *
   * @param quantity a positive integer representing the number of units.
   * @return the updated {@code StockMovement} instance.
   */
  StockMovement setQuantity(Integer quantity);

  /**
   * Gets the FK reference to the order that triggered this movement. Populated only for {@link
   * StockMovementType#ISSUE} movements.
   *
   * @return the order id as a {@link Long}, or {@code null} for non-ISSUE movements.
   */
  Long getReferenceOrderId();

  /**
   * Sets the FK reference to the order that triggered this movement.
   *
   * @param referenceOrderId the order id; may be {@code null} for non-ISSUE movements.
   * @return the updated {@code StockMovement} instance.
   */
  StockMovement setReferenceOrderId(Long referenceOrderId);

  /**
   * Gets the type of this stock movement.
   *
   * @return the {@link StockMovementType} classifying this movement.
   */
  StockMovementType getType();

  /**
   * Sets the type of this stock movement.
   *
   * @param type the {@link StockMovementType} to set; must not be {@code null}.
   * @return the updated {@code StockMovement} instance.
   */
  StockMovement setType(StockMovementType type);

  /**
   * Gets the unit cost at the time of this movement. Populated only for {@link
   * StockMovementType#RECEIPT} movements; {@code null} for ISSUE and ADJUSTMENT.
   *
   * @return the unit cost as a {@link BigDecimal}, or {@code null}.
   */
  BigDecimal getUnitCost();

  /**
   * Sets the unit cost per unit for this movement.
   *
   * @param unitCost the cost per unit; may be {@code null} for non-RECEIPT movements.
   * @return the updated {@code StockMovement} instance.
   */
  StockMovement setUnitCost(BigDecimal unitCost);
}
