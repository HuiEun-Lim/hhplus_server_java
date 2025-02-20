package kr.hhplus.be.server.domain.order.outbox.repository;

import kr.hhplus.be.server.domain.order.outbox.entity.OrderOutbox;
import kr.hhplus.be.server.domain.order.outbox.enums.OrderOutboxStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderOutboxRepository {
    List<OrderOutbox> findByStatusAndCreatedDate(OrderOutboxStatus status, LocalDateTime targetDate);

    OrderOutbox save(OrderOutbox orderOutbox);

    OrderOutbox findByMessageId(String messageId);
}
