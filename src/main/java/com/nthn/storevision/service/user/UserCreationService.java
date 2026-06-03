/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: UserCreationService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 11:21:37
 */

package com.nthn.storevision.service.user;

import com.nthn.storevision.model.User;
import com.nthn.storevision.service.generic.Creatable;

/**
 * Service interface for creating and persisting {@link User} entities.
 *
 * @see com.nthn.storevision.service.common.AbstractCreatable
 * @see Creatable
 */
@FunctionalInterface
public interface UserCreationService extends Creatable<User, Integer> {}
