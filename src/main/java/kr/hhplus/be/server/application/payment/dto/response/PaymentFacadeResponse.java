package kr.hhplus.be.server.application.payment.dto.response;

import kr.hhplus.be.server.domain.payment.dto.PaymentResult;
import kr.hhplus.be.server.domain.payment.enums.PaymentStatusType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentFacadeResponse {
    private Long paymentId;
    private Long orderId;
    private Long payAmt;
    private PaymentStatusType paymentStatus;

    public static PaymentFacadeResponse toResponse(PaymentResult paymentResult) {
        return PaymentFacadeResponse.builder()
                .paymentId(paymentResult.getPaymentId())
                .orderId(paymentResult.getOrderId())
                .payAmt(paymentResult.getPayAmt())
                .paymentStatus(paymentResult.getPaymentStatus())
                .build();
    }
}
