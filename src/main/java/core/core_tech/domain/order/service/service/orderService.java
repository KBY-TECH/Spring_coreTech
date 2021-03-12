package core.core_tech.domain.order.service.service;

import core.core_tech.domain.order.service.Order;

public interface orderService {
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
