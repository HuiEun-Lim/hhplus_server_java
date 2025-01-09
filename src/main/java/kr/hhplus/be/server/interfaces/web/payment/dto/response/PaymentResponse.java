package kr.hhplus.be.server.interfaces.web.payment.dto.response;

import kr.hhplus.be.server.domain.payment.entity.Payment;
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
    private Payment data;
}
