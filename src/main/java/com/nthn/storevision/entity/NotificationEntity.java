/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: NotificationEntity.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:51:59
 */

package com.nthn.storevision.entity;

import com.nthn.storevision.constant.notification.NotificationType;
import com.nthn.storevision.entity.mapped.BaseEntity;
import com.nthn.storevision.model.Notification;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * Represents a notification entity in the database. This class maps to the "notifications" table
 * and extends {@link BaseEntity} for auditing purposes.
 */
@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Accessors(chain = true)
public class NotificationEntity extends BaseEntity<Long> implements Notification {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 500)
  private String message;

  @Column(name = "is_read", nullable = false)
  private boolean read;

  @Column(name = "reference_id")
  private Long referenceId;

  @Column(nullable = false)
  private String title;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 30)
  private NotificationType type;
}
