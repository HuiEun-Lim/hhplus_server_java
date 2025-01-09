package kr.hhplus.be.server.infrastructure.db.order.jpa;

import kr.hhplus.be.server.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
    Order findByOrderId(Long orderId);
    Order save(Long orderId);
}
