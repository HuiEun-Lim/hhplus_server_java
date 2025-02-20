package kr.hhplus.be.server.interfaces.consumer.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.domain.order.dto.response.OrderResult;
import kr.hhplus.be.server.domain.order.outbox.entity.OrderOutbox;
import kr.hhplus.be.server.domain.order.outbox.enums.OrderOutboxStatus;
import kr.hhplus.be.server.domain.order.outbox.service.OrderOutboxService;
import kr.hhplus.be.server.infrastructure.external.OrderEventDataPlatformSender;
import kr.hhplus.be.server.infrastructure.kafka.order.OrderKafkaConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderKafkaConsumer {

    private final OrderOutboxService orderOutboxService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final OrderEventDataPlatformSender platformSender;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = OrderKafkaConstants.ORDER_COMPLETED, groupId = "order-outbox-group")
    public void processOutboxMessage(@Payload String message, @Header(KafkaHeaders.RECEIVED_KEY) String messageId) {
        log.info("Outbox 셀프 컨슈머 START : 주문 ID = {}", messageId);

        OrderOutbox orderOutbox = orderOutboxService.findByMessageId(messageId);
        if (orderOutbox != null) {
            log.info("Outbox 셀프 컨슈머 orderOutbox SUCCESS : 주문 ID = {}", messageId);
            orderOutbox.updateStatus(OrderOutboxStatus.SUCCESS);
            orderOutboxService.save(orderOutbox);
        }
        log.info("Outbox 셀프 컨슈머 END : 주문 ID = {}", messageId);
    }

    @KafkaListener(topics = OrderKafkaConstants.ORDER_COMPLETED, groupId = "order-consumer-group")
    public void consumeOrderEvent(@Payload String message, @Header(KafkaHeaders.RECEIVED_KEY) String messageId) {
        log.info("주문정보 데이터플랫폼 송신 START : 주문 ID = {}", messageId);
        int retryCount = 0;
        boolean isSuccess = false;

        while (retryCount < 3 && !isSuccess) {
            try {
                platformSender.send(objectMapper.readValue(message, OrderResult.class));

                isSuccess = true;
                log.info("주문정보 데이터플랫폼 송신 성공 : 주문 ID = {}", messageId);
            } catch (Exception e) {
                retryCount++;
                log.warn("주문정보 데이터플랫폼 송신 실패 ({}번째 시도): 주문 ID = {}", retryCount, messageId);
            }
        }

        if (!isSuccess) {
            log.error("주문정보 데이터플랫폼 송신 최종 실패: 주문 ID = {} -> Dead Letter Topic(DLT)으로 이동", messageId);
            kafkaTemplate.send(OrderKafkaConstants.ORDER_COMPLETED_DLT, messageId, message);
            OrderOutbox orderOutbox = orderOutboxService.findByMessageId(messageId);
            if (orderOutbox != null) {
                log.info("Outbox consumer OrderCompleted Sending to DataPlatForm IS FAIL : 주문 ID = {}", messageId);
                orderOutbox.updateStatus(OrderOutboxStatus.FAIL);
                orderOutboxService.save(orderOutbox);
            }
        }
    }

}
