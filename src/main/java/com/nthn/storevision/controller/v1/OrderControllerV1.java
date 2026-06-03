package com.nthn.storevision.controller.v1;

import com.nthn.storevision.constant.api.ResponseCode;
import com.nthn.storevision.constant.api.ResponseDescription;
import com.nthn.storevision.controller.OrderController;
import com.nthn.storevision.dto.OrderDTO;
import com.nthn.storevision.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Version 1 implementation of {@link OrderController}.
 */
@Tag(name = "Order", description = "Order management endpoints")
@Slf4j
@RestController
@RequestMapping(value = "/v1/orders", version = "v1")
@RequiredArgsConstructor
public class OrderControllerV1 implements OrderController {

  private static final String ID_DESC = "Order ID";

  private final OrderService orderService;

  @Operation(
      summary = "Create a new order",
      description = "Creates a new order for a customer and persists it to the database.")
  @ApiResponse(responseCode = ResponseCode.CREATED, description = ResponseDescription.CREATED)
  @ApiResponse(responseCode = ResponseCode.BAD_REQUEST, description = ResponseDescription.INVALID_INPUT)
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  @Override
  public OrderDTO createOrder(@Valid @RequestBody final OrderDTO dto) {
    log.debug("POST /orders");
    return orderService.create(dto);
  }

  @Operation(
      summary = "Soft-delete an order by ID",
      description = "Marks the order as deleted without physically removing the record."
          + " The record can be restored later.")
  @ApiResponse(responseCode = ResponseCode.NO_CONTENT, description = ResponseDescription.DELETED)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  @Override
  public void deleteOrder(
      @Parameter(description = ID_DESC) @PathVariable final Long id) {
    log.debug("DELETE /orders/{}", id);
    orderService.delete(id);
  }

  @Operation(
      summary = "Retrieve all orders",
      description = "Returns a list of all active (non-deleted) order records.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @GetMapping
  @Override
  public List<OrderDTO> getAllOrders() {
    log.debug("GET /orders");
    return orderService.findAll();
  }

  @Operation(
      summary = "Retrieve an order by ID",
      description = "Returns the order record with the specified ID.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @GetMapping("/{id}")
  @Override
  public OrderDTO getOrderById(
      @Parameter(description = ID_DESC) @PathVariable final Long id) {
    log.debug("GET /orders/{}", id);
    return orderService.findById(id);
  }

  @Operation(
      summary = "Update an order by ID",
      description = "Updates the mutable fields (orderDate, totalAmount, status) of an existing order."
          + " Customer association cannot be changed via this endpoint.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.BAD_REQUEST, description = ResponseDescription.INVALID_INPUT)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @PutMapping("/{id}")
  @Override
  public OrderDTO updateOrder(
      @Parameter(description = ID_DESC) @PathVariable final Long id,
      @Valid @RequestBody final OrderDTO dto) {
    log.debug("PUT /orders/{}", id);
    return orderService.update(id, dto);
  }
}
