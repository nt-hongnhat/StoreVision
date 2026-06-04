/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerCreationService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 14:10:46
 */

package com.nthn.storevision.service.customer;

import com.nthn.storevision.dto.CustomerDTO;
import com.nthn.storevision.service.generic.Creatable;

/**
 * Service interface for creating and persisting customers.
 *
 * @see com.nthn.storevision.service.common.AbstractCreatable
 * @see Creatable
 */
@FunctionalInterface
public interface CustomerCreationService extends Creatable<CustomerDTO> {}
