package com.nthn.storevision.service.orderitem;

import com.nthn.storevision.entity.OrderItemEntity;
import com.nthn.storevision.service.generic.HardDeletable;
import com.nthn.storevision.service.generic.SoftDeletable;

/**
 * Service interface for soft-deleting, restoring, and permanently deleting
 * {@link OrderItemEntity} entities.
 *
 * @see SoftDeletable
 * @see HardDeletable
 */
public interface OrderItemDeletionService
    extends SoftDeletable<OrderItemEntity, Long>, HardDeletable<OrderItemEntity, Long> {
}
