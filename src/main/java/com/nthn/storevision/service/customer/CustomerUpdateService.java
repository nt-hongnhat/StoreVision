/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerUpdateService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 16:06:54
 */

package com.nthn.storevision.service.customer;

import com.nthn.storevision.dto.CustomerDTO;
import com.nthn.storevision.service.generic.Updatable;

/**
 * Service interface for updating customers.
 *
 * @see Updatable
 */
@FunctionalInterface
public interface CustomerUpdateService extends Updatable<CustomerDTO, Long> {}
