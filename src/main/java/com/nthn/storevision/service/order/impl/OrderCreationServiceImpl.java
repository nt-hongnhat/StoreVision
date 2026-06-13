/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderCreationServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 16:20:29
 */

package com.nthn.storevision.service.order.impl;

import com.nthn.storevision.constant.DiscountType;
import com.nthn.storevision.constant.LoyaltyTier;
import com.nthn.storevision.constant.OrderStatus;
import com.nthn.storevision.constant.message.CustomerMessageCode;
import com.nthn.storevision.constant.message.ProductMessageCode;
import com.nthn.storevision.dto.OrderDTO;
import com.nthn.storevision.dto.OrderItemDTO;
import com.nthn.storevision.dto.VoucherDTO;
import com.nthn.storevision.dto.request.OrderCreationRequest;
import com.nthn.storevision.dto.request.OrderItemCreationRequest;
import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.entity.VoucherEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.exception.InsufficientStockException;
import com.nthn.storevision.mapper.EntityMapperConfig;
import com.nthn.storevision.model.Product;
import com.nthn.storevision.repository.OrderRepository;
import com.nthn.storevision.service.common.AbstractCreatable;
import com.nthn.storevision.service.customer.CustomerRetrievalService;
import com.nthn.storevision.service.order.OrderCreationService;
import com.nthn.storevision.service.orderitem.OrderItemCreationService;
import com.nthn.storevision.service.product.ProductRetrievalService;
import com.nthn.storevision.service.voucher.VoucherValidationService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link OrderCreationService} that extends {@link AbstractCreatable} to
 * persist {@link OrderEntity} instances via {@link OrderRepository}.
 *
 * <p>{@link #createOrder} implements the sales-order creation flow: input validation (customer and
 * products must exist and be active), stock availability check without deduction (BR-O01), optional
 * voucher validation (BR-V01–BR-V04), server-side total calculation — {@code totalAmount = subtotal
 * − discount + tax} (BR-O03) — and unit-price lock at creation time (BR-O04). The order and all its
 * items are persisted in a single transaction with status {@link OrderStatus#PENDING}.
 */
@Slf4j
@Service
public class OrderCreationServiceImpl
    extends AbstractCreatable<OrderDTO, OrderEntity, OrderRepository>
    implements OrderCreationService {

  private static final BigDecimal GOLD_BIRTHDAY_DISCOUNT_PERCENTAGE = BigDecimal.valueOf(10);
  private static final int MONEY_SCALE = 2;
  private static final BigDecimal PERCENT_DIVISOR = BigDecimal.valueOf(100);
  private static final BigDecimal SILVER_BIRTHDAY_DISCOUNT_PERCENTAGE = BigDecimal.valueOf(5);
  private final CustomerRetrievalService customerRetrievalService;
  private final OrderItemCreationService orderItemCreationService;
  private final ProductRetrievalService productRetrievalService;
  private final BigDecimal taxRate;
  private final VoucherValidationService voucherValidationService;

  protected OrderCreationServiceImpl(
      final EntityMapperConfig<OrderDTO, ?, OrderEntity> mapper,
      final OrderRepository repository,
      final CustomerRetrievalService customerRetrievalService,
      final OrderItemCreationService orderItemCreationService,
      final ProductRetrievalService productRetrievalService,
      final VoucherValidationService voucherValidationService,
      @Value("${app.order.tax-rate:0.10}") final BigDecimal taxRate) {
    super(mapper, repository);
    this.customerRetrievalService = customerRetrievalService;
    this.orderItemCreationService = orderItemCreationService;
    this.productRetrievalService = productRetrievalService;
    this.voucherValidationService = voucherValidationService;
    this.taxRate = taxRate;
  }

  /** {@inheritDoc} */
  @Override
  @Transactional
  public OrderDTO createOrder(final @NonNull OrderCreationRequest request) {
    log.debug(
        "Creating order: customerId={}, items={}, voucherCode={}",
        request.getCustomerId(),
        request.getItems().size(),
        request.getVoucherCode());

    final CustomerEntity customer = findActiveCustomer(request.getCustomerId());
    final Map<Long, ProductEntity> products = findActiveProducts(request.getItems());

    verifyStockAvailability(request.getItems(), products);

    final BigDecimal subtotal = calculateSubtotal(request.getItems(), products);
    final VoucherDTO voucher = validateVoucherIfPresent(request.getVoucherCode(), subtotal);

    final BigDecimal voucherDiscountAmount = calculateVoucherDiscount(voucher, subtotal);
    final BigDecimal birthdayDiscountAmount = calculateBirthdayDiscount(customer, subtotal);

    // Apply the maximum of the two discounts
    final BigDecimal finalDiscountAmount = voucherDiscountAmount.max(birthdayDiscountAmount);

    final BigDecimal taxAmount =
        subtotal.multiply(taxRate).setScale(MONEY_SCALE, RoundingMode.HALF_UP);
    final BigDecimal totalAmount = subtotal.subtract(finalDiscountAmount).add(taxAmount);

    final OrderEntity saved =
        getRepository()
            .save(
                OrderEntity.builder()
                    .customer(customer)
                    .orderDate(LocalDateTime.now())
                    .status(OrderStatus.PENDING)
                    .channel(request.getChannel())
                    .note(request.getNote())
                    .subtotal(subtotal)
                    .discountAmount(finalDiscountAmount)
                    .taxAmount(taxAmount)
                    .totalAmount(totalAmount)
                    .voucher(toVoucherReference(voucher))
                    .build());

    final List<OrderItemDTO> items = createOrderItems(saved.getId(), request.getItems(), products);

    final OrderDTO result = getMapper().toDTO(saved);
    result.setOrderItems(items);

    log.info(
        "Order created: id={}, customerId={}, items={}, subtotal={}, discount={}, tax={}, total={}",
        saved.getId(),
        customer.getId(),
        items.size(),
        subtotal,
        finalDiscountAmount,
        taxAmount,
        totalAmount);
    return result;
  }

  /**
   * Calculates birthday discount for eligible customers based on their loyalty tier. Silver members
   * get 5% off, Gold members get 10% off.
   */
  private BigDecimal calculateBirthdayDiscount(
      final @NonNull CustomerEntity customer, final BigDecimal subtotal) {
    if (null == customer.getDateOfBirth() || !isBirthday(customer.getDateOfBirth())) {
      return BigDecimal.ZERO;
    }

    BigDecimal discountPercentage = BigDecimal.ZERO;
    if (LoyaltyTier.SILVER == customer.getLoyaltyTier()) {
      discountPercentage = SILVER_BIRTHDAY_DISCOUNT_PERCENTAGE;
    } else if (LoyaltyTier.GOLD == customer.getLoyaltyTier()) {
      discountPercentage = GOLD_BIRTHDAY_DISCOUNT_PERCENTAGE;
    }

    if (0 < discountPercentage.compareTo(BigDecimal.ZERO)) {
      return subtotal
          .multiply(discountPercentage)
          .divide(PERCENT_DIVISOR, MONEY_SCALE, RoundingMode.HALF_UP)
          .min(subtotal); // Ensure discount doesn't exceed subtotal
    }
    return BigDecimal.ZERO;
  }

  /**
   * Computes the discount granted by the validated voucher: a percentage of the subtotal capped at
   * the voucher's maximum (PERCENTAGE), or a fixed amount (FIXED_AMOUNT). The result never exceeds
   * the subtotal so the total amount cannot become negative.
   */
  private BigDecimal calculateVoucherDiscount(
      final @Nullable VoucherDTO voucher, final BigDecimal subtotal) {
    if (null == voucher) {
      return BigDecimal.ZERO;
    }
    BigDecimal discount;
    if (DiscountType.PERCENTAGE == voucher.getDiscountType()) {
      discount =
          subtotal
              .multiply(voucher.getDiscountValue())
              .divide(PERCENT_DIVISOR, MONEY_SCALE, RoundingMode.HALF_UP);
      if (null != voucher.getMaxDiscountAmount()) {
        discount = discount.min(voucher.getMaxDiscountAmount());
      }
    } else {
      discount = voucher.getDiscountValue();
    }
    return discount.min(subtotal);
  }

  /** BR-O04: each item's unit price is locked from the product's price at creation time. */
  private List<OrderItemDTO> createOrderItems(
      final Long orderId,
      final List<OrderItemCreationRequest> items,
      final Map<Long, ProductEntity> products) {
    return items.stream()
        .map(
            item ->
                orderItemCreationService.create(
                    OrderItemDTO.builder()
                        .orderId(orderId)
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .unitPrice(products.get(item.getProductId()).getPrice())
                        .build()))
        .toList();
  }

  private CustomerEntity findActiveCustomer(final Long customerId) {
    final CustomerEntity customer =
        customerRetrievalService.findById(customerId, Function.identity());
    if (customer.getDeletedDate().isPresent()) {
      throw new EntityNotFoundException(CustomerMessageCode.NOT_FOUND, customerId);
    }
    return customer;
  }

  private ProductEntity findActiveProduct(final Long productId) {
    final ProductEntity product = productRetrievalService.findById(productId, Function.identity());
    if (product.getDeletedDate().isPresent()) {
      throw new EntityNotFoundException(ProductMessageCode.NOT_FOUND, productId);
    }
    return product;
  }

  private Map<Long, ProductEntity> findActiveProducts(final List<OrderItemCreationRequest> items) {
    final Map<Long, ProductEntity> products = new LinkedHashMap<>();
    items.forEach(item -> products.computeIfAbsent(item.getProductId(), this::findActiveProduct));
    return products;
  }

  /** Checks if the given date of birth matches the current month and day. */
  private boolean isBirthday(final LocalDate dateOfBirth) {
    final LocalDate today = LocalDate.now();
    return dateOfBirth.getMonth() == today.getMonth()
        && dateOfBirth.getDayOfMonth() == today.getDayOfMonth();
  }

  /** BR-V01–BR-V04: delegates voucher validation to the voucher domain when a code is supplied. */
  private @Nullable VoucherDTO validateVoucherIfPresent(
      final @Nullable String voucherCode, final BigDecimal orderAmount) {
    if (null == voucherCode || voucherCode.isBlank()) {
      return null;
    }
    return voucherValidationService.validate(voucherCode, orderAmount);
  }

  /**
   * BR-O01 at creation time: verifies stock is sufficient without deducting it — deduction only
   * happens when the order is completed. Quantities of duplicate products are summed so the check
   * covers the whole order, not each line in isolation.
   */
  private void verifyStockAvailability(
      final List<OrderItemCreationRequest> items, final Map<Long, ProductEntity> products) {
    final Map<Long, Integer> requestedPerProduct = new LinkedHashMap<>();
    items.forEach(
        item -> requestedPerProduct.merge(item.getProductId(), item.getQuantity(), Integer::sum));

    requestedPerProduct.forEach(
        (productId, requested) -> {
          final ProductEntity product = products.get(productId);
          if (product.getStockQuantity() < requested) {
            throw new InsufficientStockException(
                product.getName(), product.getStockQuantity(), requested);
          }
        });
  }

  /** BR-O03: the subtotal is always computed server-side from current product prices. */
  private static BigDecimal calculateSubtotal(
      final @NonNull List<? extends OrderItemCreationRequest> items,
      final Map<Long, ? extends Product> products) {
    return items.stream()
        .map(
            item ->
                products
                    .get(item.getProductId())
                    .getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private static @Nullable VoucherEntity toVoucherReference(final @Nullable VoucherDTO voucher) {
    if (null == voucher) {
      return null;
    }
    return new VoucherEntity().setId(voucher.getId());
  }
}
