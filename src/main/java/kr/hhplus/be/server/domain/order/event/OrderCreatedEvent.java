package kr.hhplus.be.server.domain.order.event;

import kr.hhplus.be.server.domain.order.dto.response.OrderResult;
import lombok.Getter;

@Getter
public class OrderCreatedEvent {
    private final OrderResult orderResult;

    public OrderCreatedEvent(OrderResult orderResult) {
        this.orderResult = orderResult;
    }
}
