package com.nthn.storevision.service.orderitem;

import com.nthn.storevision.entity.OrderItemEntity;
import com.nthn.storevision.service.generic.Creatable;

/**
 * Service interface for creating and persisting {@link OrderItemEntity} entities.
 *
 * @see com.nthn.storevision.service.common.AbstractCreatable
 * @see Creatable
 */
public interface OrderItemCreationService extends Creatable<OrderItemEntity, Long> {
}
