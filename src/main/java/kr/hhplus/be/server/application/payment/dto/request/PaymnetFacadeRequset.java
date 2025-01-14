package kr.hhplus.be.server.application.payment.dto.request;

import kr.hhplus.be.server.domain.payment.dto.PaymentServiceRequest;
import kr.hhplus.be.server.domain.payment.enums.PaymentStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymnetFacadeRequset {
    private Long userId;
    private Long orderId;

    public PaymentServiceRequest toServiceRequest(Long payAmt, PaymentStatusType paymentStatusType){
        return PaymentServiceRequest.builder()
                .orderId(orderId)
                .payAmt(payAmt)
                .paymentStatus(paymentStatusType)
                .build();
    }
}
