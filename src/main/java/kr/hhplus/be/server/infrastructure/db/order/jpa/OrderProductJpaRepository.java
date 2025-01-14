package kr.hhplus.be.server.infrastructure.db.order.jpa;

import kr.hhplus.be.server.domain.order.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductJpaRepository extends JpaRepository<OrderProduct, Long>  {
    List<OrderProduct> findByOrderId(Long orderId);
    OrderProduct save(OrderProduct orderProduct);
}
