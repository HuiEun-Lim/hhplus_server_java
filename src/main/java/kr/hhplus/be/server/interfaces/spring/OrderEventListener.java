package kr.hhplus.be.server.interfaces.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.domain.order.event.OrderCreatedEvent;
import kr.hhplus.be.server.domain.order.outbox.entity.OrderOutbox;
import kr.hhplus.be.server.domain.order.outbox.enums.OrderEventType;
import kr.hhplus.be.server.domain.order.outbox.service.OrderOutboxService;
import kr.hhplus.be.server.infrastructure.kafka.order.OrderKafkaConstants;
import kr.hhplus.be.server.infrastructure.kafka.order.OrderKafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final OrderOutboxService orderOutboxService;
    private final OrderKafkaProducer orderKafkaProducer;
    private final ObjectMapper objectMapper;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void createOutbox(OrderCreatedEvent event) throws JsonProcessingException {
        log.info("createOutbox Start : 주문 ID =  {}", event.getOrderResult().getOrderId());
        String orderId = event.getOrderResult().getOrderId().toString();
        String payload = objectMapper.writeValueAsString(event.getOrderResult());
        OrderOutbox orderOutbox = OrderOutbox.createNew(orderId, OrderKafkaConstants.ORDER_COMPLETED, OrderEventType.ORDER_COMPLETED, payload);

        orderOutboxService.save(orderOutbox);
        log.info("createOutbox End : 주문 ID = {}", event.getOrderResult().getOrderId());
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishToKafka(OrderCreatedEvent event) throws JsonProcessingException {
        log.info("Kafka 발행 시작 : 주문 ID = {}", event.getOrderResult().getOrderId());

        String orderId = event.getOrderResult().getOrderId().toString();
        String payload = objectMapper.writeValueAsString(event.getOrderResult());

        orderKafkaProducer.send(OrderKafkaConstants.ORDER_COMPLETED, orderId, payload);

        log.info("Kafka 발행 종료 : 주문 ID = {}", event.getOrderResult().getOrderId());
    }
}
