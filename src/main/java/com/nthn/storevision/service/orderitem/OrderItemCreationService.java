package com.nthn.storevision.service.orderitem;

import com.nthn.storevision.dto.order_item.OrderItemDTO;
import com.nthn.storevision.service.generic.Creatable;

/**
 * Service interface for creating and persisting order items.
 *
 * @see com.nthn.storevision.service.common.AbstractCreatable
 * @see Creatable
 */
public interface OrderItemCreationService extends Creatable<OrderItemDTO> {
}
