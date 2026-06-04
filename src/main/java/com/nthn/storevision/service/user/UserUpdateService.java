/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: UserUpdateService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 16:47:26
 */

package com.nthn.storevision.service.user;

import com.nthn.storevision.dto.UserDTO;
import com.nthn.storevision.model.User;
import com.nthn.storevision.service.generic.Updatable;

/**
 * Service interface for updating {@link User} entities.
 *
 * @see Updatable
 */
@FunctionalInterface
public interface UserUpdateService extends Updatable<UserDTO, Integer> {}
