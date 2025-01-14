package kr.hhplus.be.server.domain.order.repository;

import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.enums.OrderStateType;

public interface OrderRepository {
    Order findByOrderId(Long orderId);
    Order save(Order order);
    int updateState(Long orderId, OrderStateType orderStateType);
}
