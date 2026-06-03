/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerCreationService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 11:21:37
 */

package com.nthn.storevision.service.customer;

import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.service.generic.Creatable;

/**
 * Service interface for creating and persisting {@link CustomerEntity} entities.
 *
 * @see com.nthn.storevision.service.common.AbstractCreatable
 * @see Creatable
 */
@FunctionalInterface
public interface CustomerCreationService extends Creatable<CustomerEntity, Long> {}
