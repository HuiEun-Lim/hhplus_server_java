package kr.hhplus.be.server.domain.order.event;

import kr.hhplus.be.server.domain.order.dto.response.OrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public void publishOrderSuccess(OrderResult orderResult) {
        eventPublisher.publishEvent(new OrderCreatedEvent(orderResult));
    }
}
