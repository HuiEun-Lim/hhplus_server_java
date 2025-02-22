package kr.hhplus.be.server.domain.order.outbox.service;

import kr.hhplus.be.server.domain.order.outbox.entity.OrderOutbox;
import kr.hhplus.be.server.domain.order.outbox.enums.OrderOutboxStatus;
import kr.hhplus.be.server.domain.order.outbox.repository.OrderOutboxRepository;
import kr.hhplus.be.server.infrastructure.kafka.order.OrderKafkaConstants;
import kr.hhplus.be.server.infrastructure.kafka.order.OrderKafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderOutboxBatchService {
    private final OrderOutboxRepository orderOutboxRepository;
    private final OrderKafkaProducer OrderKafkaProducer;

    @Scheduled(fixedDelay = 300000)
    public void retryFailedOrderCompletedMessages() {
        List<OrderOutbox> failedMessages = orderOutboxRepository.findByStatusAndCreatedDate(OrderOutboxStatus.SAVE, LocalDateTime.now().minusMinutes(5));

        for (OrderOutbox outbox : failedMessages) {
            try {
                OrderKafkaProducer.send(OrderKafkaConstants.ORDER_COMPLETED, outbox.getMessageId(), outbox.getPayload());
                log.info("Kafka 재발송 성공: 주문 ID = {}", outbox.getMessageId());
            } catch (Exception e) {
                log.error("Kafka 재발송 실패: 주문 ID = {}", outbox.getMessageId(), e);
            }
        }
    }
}
