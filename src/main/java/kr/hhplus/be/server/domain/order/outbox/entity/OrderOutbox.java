package kr.hhplus.be.server.domain.order.outbox.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.BaseEntity;
import kr.hhplus.be.server.domain.order.outbox.enums.OrderEventType;
import kr.hhplus.be.server.domain.order.outbox.enums.OrderOutboxStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_outbox")
public class OrderOutbox extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long outboxId;

    private String messageId;
    private String kafkaMessageId;
    private OrderEventType eventType;
    @Lob
    private String payload; // Kafka로 전송할 데이터
    private OrderOutboxStatus status;

    public static OrderOutbox createNew(String messageID, String kafkaMessageId, OrderEventType eventType, String payload) {
        return OrderOutbox.builder()
                .messageId(messageID)
                .kafkaMessageId(kafkaMessageId)
                .eventType(eventType)
                .payload(payload)
                .status(OrderOutboxStatus.SAVE)
                .build();
    }

    public void updateStatus(OrderOutboxStatus status) {
        this.status = status;
    }

}
