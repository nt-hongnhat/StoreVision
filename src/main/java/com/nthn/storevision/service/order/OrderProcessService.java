/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: OrderProcessService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-09-09 16:02:27
 */

package com.nthn.storevision.service.order;

import java.util.List;
import org.flowable.task.api.Task;

/**
 * Service interface for managing the order-processing workflow. Provides methods to complete
 * packing tasks, retrieve warehouse tasks, and initiate the order processing flow.
 */
public interface OrderProcessService {

  /**
   * Marks a packing task as complete.
   *
   * @param taskId the unique identifier of the packing task to be completed; must not be null
   */
  void completePacking(String taskId);

  /**
   * Retrieves a list of tasks to be processed in the warehouse.
   *
   * @return a list of {@link Task} objects representing the tasks in the warehouse; the list may be
   *     empty but never null
   */
  List<Task> getWarehouseTasks();

  /**
   * Starts the {@code orderProcessingSystem} Flowable process instance for an existing {@code
   * PENDING} order, using the order ID as the process business key.
   *
   * @param orderId the unique identifier of the order to be processed; must not be {@code null}
   * @throws com.nthn.storevision.exception.EntityNotFoundException if the order does not exist
   */
  void startOrderProcess(Long orderId);
}
