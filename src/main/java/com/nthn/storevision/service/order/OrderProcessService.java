/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: OrderProcessService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-09-08 17:18:53
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
   * Initiates the order processing workflow for a specified product.
   *
   * @param productId the unique identifier of the product to be processed; must not be null
   */
  void startOrderProcess(Long productId);
}
