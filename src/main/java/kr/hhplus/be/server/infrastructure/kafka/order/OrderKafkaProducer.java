package kr.hhplus.be.server.infrastructure.kafka.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, String messageId, Object payload) {
        kafkaTemplate.send(topic, messageId, payload.toString())
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error("Kafka 메시지 발행 실패: topic={}, key={}, payload={}", topic, messageId, payload, exception);
                    } else {
                        log.info("Kafka 메시지 발행 성공: topic={}, key={}, payload={}", topic, messageId, payload);
                    }
                });
    }

}
