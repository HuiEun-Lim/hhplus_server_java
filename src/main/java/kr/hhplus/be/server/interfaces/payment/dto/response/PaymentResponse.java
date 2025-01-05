package kr.hhplus.be.server.interfaces.payment.dto.response;

import kr.hhplus.be.server.domain.order.OrderPay;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private String message;
    private OrderPay data;
}
