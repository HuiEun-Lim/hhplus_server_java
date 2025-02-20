package kr.hhplus.be.server.infrastructure.db.order.outbox.impl;

import kr.hhplus.be.server.domain.order.outbox.entity.OrderOutbox;
import kr.hhplus.be.server.domain.order.outbox.enums.OrderOutboxStatus;
import kr.hhplus.be.server.domain.order.outbox.repository.OrderOutboxRepository;
import kr.hhplus.be.server.infrastructure.db.order.outbox.jpa.OrderOutboxJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class OrderOutboxRepositoryImpl implements OrderOutboxRepository {

    private final OrderOutboxJpaRepository orderOutboxJpaRepository;

    public OrderOutboxRepositoryImpl(OrderOutboxJpaRepository orderOutboxJpaRepository) {
        this.orderOutboxJpaRepository = orderOutboxJpaRepository;
    }

    @Override
    public List<OrderOutbox> findByStatusAndCreatedDate(OrderOutboxStatus status, LocalDateTime targetDate) {
        return orderOutboxJpaRepository.findAllByStatusAndModifiedAtBefore(status, targetDate);
    }

    @Override
    public OrderOutbox save(OrderOutbox orderOutbox) {
        return orderOutboxJpaRepository.save(orderOutbox);
    }

    @Override
    public OrderOutbox findByMessageId(String messageId) {
        return orderOutboxJpaRepository.findByMessageId(messageId);
    }
}
