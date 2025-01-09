package kr.hhplus.be.server.domain.order.repository;

import kr.hhplus.be.server.domain.order.entity.Order;

public interface OrderRepository {
    Order findByOrderId(Long orderId);
    Order save(Order order);
}
