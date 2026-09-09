/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: OrderProcessServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-09-09 16:26:56
 */

package com.nthn.storevision.service.order.impl;

import com.nthn.storevision.dto.order.OrderDTO;
import com.nthn.storevision.dto.order_item.OrderItemDTO;
import com.nthn.storevision.service.order.OrderProcessService;
import com.nthn.storevision.service.order.OrderRetrievalService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link OrderProcessService} interface. Provides functionality for
 * interacting with the order-processing workflow. Responsible for managing tasks, completing
 * packing operations, and initiating order processes within the system.
 *
 * <p>This class uses Flowable engine services such as {@link RuntimeService} and {@link
 * TaskService} to handle process instance creation and task management. It also employs logging
 * mechanisms for tracking workflow-related activities.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderProcessServiceImpl implements OrderProcessService {
  private static final String PROCESS_DEFINITION_KEY = "orderProcessingSystem";
  private static final String WAREHOUSE_STAFF_ASSIGNEE = "warehouse_staff";

  private final OrderRetrievalService orderRetrievalService;
  private final RuntimeService runtimeService;
  private final TaskService taskService;

  @Override
  public void completePacking(final String taskId) {
    taskService.complete(taskId);
    log.info("Packing task completed: {}", taskId);
  }

  @Override
  public List<Task> getWarehouseTasks() {
    log.debug("Retrieving warehouse tasks for assignee={}", WAREHOUSE_STAFF_ASSIGNEE);
    return taskService.createTaskQuery().taskAssignee(WAREHOUSE_STAFF_ASSIGNEE).list();
  }

  @Override
  public void startOrderProcess(final Long orderId) {
    log.info("Starting order process for order id={}", orderId);
    final OrderDTO order = orderRetrievalService.findOrderDTOById(orderId);
    final OrderItemDTO firstItem = order.getOrderItems().getFirst();
    if (hasMultipleOrderItems(order)) {
      log.warn(
          "Order id={} has {} items; taskCheckStock only evaluates the first item (single-item orders only until multi-item support is added)",
          orderId,
          order.getOrderItems().size());
    }

    final Map<String, Object> variables =
        Map.of(
            "orderId", orderId,
            "productId", firstItem.getProductId(),
            "requestedQuantity", firstItem.getQuantity());

    final ProcessInstance processInstance =
        runtimeService.startProcessInstanceByKey(
            PROCESS_DEFINITION_KEY, String.valueOf(orderId), variables);
    log.info(
        "Order process started: processInstanceId={} orderId={}", processInstance.getId(), orderId);
  }

  private static boolean hasMultipleOrderItems(final @NonNull OrderDTO order) {
    return 1 < order.getOrderItems().size();
  }
}
