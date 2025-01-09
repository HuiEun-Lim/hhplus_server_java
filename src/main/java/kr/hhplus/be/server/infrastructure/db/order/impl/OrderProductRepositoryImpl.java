package kr.hhplus.be.server.infrastructure.db.order.impl;

import kr.hhplus.be.server.domain.order.entity.OrderProduct;
import kr.hhplus.be.server.domain.order.repository.OrderProductRepository;
import kr.hhplus.be.server.infrastructure.db.order.jpa.OrderProductJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderProductRepositoryImpl implements OrderProductRepository {

    private final OrderProductJpaRepository orderProductJpaRepository;

    public OrderProductRepositoryImpl(OrderProductJpaRepository orderProductJpaRepository) {
        this.orderProductJpaRepository = orderProductJpaRepository;
    }

    @Override
    public List<OrderProduct> findByOrderId(Long orderId) {
        return orderProductJpaRepository.findByOrderId(orderId);
    }

    @Override
    public OrderProduct save(OrderProduct product) {
        return orderProductJpaRepository.save(product);
    }
}
