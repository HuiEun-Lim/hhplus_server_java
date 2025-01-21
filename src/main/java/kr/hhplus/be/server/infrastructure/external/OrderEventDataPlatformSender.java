package kr.hhplus.be.server.infrastructure.external;

import kr.hhplus.be.server.domain.order.dto.response.OrderResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventDataPlatformSender implements OrderEventSender {
    @Override
    public void send(OrderResult orderResult) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
