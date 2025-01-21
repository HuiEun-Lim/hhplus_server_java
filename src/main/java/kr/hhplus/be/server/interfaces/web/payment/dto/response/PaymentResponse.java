package kr.hhplus.be.server.interfaces.web.payment.dto.response;

import kr.hhplus.be.server.interfaces.web.payment.model.PaymentInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private PaymentInfo payment;
}
