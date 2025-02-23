package kr.hhplus.be.server.infrastructure.db.order.outbox.jpa;

import kr.hhplus.be.server.domain.order.outbox.entity.OrderOutbox;
import kr.hhplus.be.server.domain.order.outbox.enums.OrderOutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderOutboxJpaRepository extends JpaRepository<OrderOutbox, Long>  {
    List<OrderOutbox> findAllByStatusAndModifiedAtBefore(OrderOutboxStatus status, LocalDateTime targetDate);

    OrderOutbox findByMessageId(String messageId);
}
