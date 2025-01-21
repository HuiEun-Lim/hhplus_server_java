package kr.hhplus.be.server.infrastructure.external;

import kr.hhplus.be.server.domain.order.dto.response.OrderResult;

public interface OrderEventSender {
    void send(OrderResult orderResult);
}
