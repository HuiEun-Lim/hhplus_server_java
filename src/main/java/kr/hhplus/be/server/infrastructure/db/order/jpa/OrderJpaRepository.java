package kr.hhplus.be.server.infrastructure.db.order.jpa;

import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.enums.OrderStateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
    Order findByOrderId(Long orderId);
    Order save(Long orderId);

    @Modifying
    @Query("UPDATE Order o SET o.orderState = :orderState WHERE o.orderId = :orderId")
    int updateState(@Param("orderId") Long orderId, @Param("orderState") OrderStateType orderState);
}
