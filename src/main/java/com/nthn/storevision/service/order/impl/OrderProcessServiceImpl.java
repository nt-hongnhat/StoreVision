/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: OrderProcessServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-09-08 17:19:35
 */

package com.nthn.storevision.service.order.impl;

import com.nthn.storevision.service.order.OrderProcessService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
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
  private final RuntimeService runtimeService;
  private final TaskService taskService;

  @Override
  public void completePacking(final String taskId) {
    // TODO: Implement logic to complete packing task
    taskService.complete(taskId);
    log.info("Packing task completed: {}", taskId);
  }

  @Override
  public List<Task> getWarehouseTasks() {
    // TODO: Implement logic to retrieve warehouse tasks
    log.info("Retrieving warehouse tasks");
    return taskService.createTaskQuery().taskAssignee("warehouse_staff").list();
  }

  @Override
  public void startOrderProcess(final Long productId) {
    // TODO: Implement logic to start order processing
    log.info("Starting order processing for product ID: {}", productId);
    final Map<String, Object> variables = Map.of("productId", productId);

    final ProcessInstance processInstance =
        runtimeService.startProcessInstanceByKey("orderProcessingSystem", variables);
    log.info("Order processing started for product ID: {}", productId);
    processInstance.getId();
  }
}
