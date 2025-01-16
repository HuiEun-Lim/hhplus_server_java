package kr.hhplus.be.server.infrastructure.db.order.impl;

import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.enums.OrderStateType;
import kr.hhplus.be.server.domain.order.repository.OrderRepository;
import kr.hhplus.be.server.infrastructure.db.order.jpa.OrderJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    public OrderRepositoryImpl(OrderJpaRepository orderJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
    }

    @Override
    public Order findByOrderId(Long orderId) {
        return orderJpaRepository.findByOrderId(orderId);
    }

    @Override
    public Order findByOrderIdWithLock(Long orderId) {
        return orderJpaRepository.findByOrderIdWithLock(orderId);
    }

    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(order);
    }

    @Override
    public int updateState(Long orderId, OrderStateType orderStateType) {
        return orderJpaRepository.updateState(orderId, orderStateType);
    }
}
