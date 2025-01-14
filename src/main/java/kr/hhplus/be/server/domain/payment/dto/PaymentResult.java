package kr.hhplus.be.server.domain.payment.dto;

import kr.hhplus.be.server.domain.payment.entity.Payment;
import kr.hhplus.be.server.domain.payment.enums.PaymentStatusType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentResult {
    private Long paymentId;
    private Long orderId;
    private Long payAmt;
    private PaymentStatusType paymentStatus;

    public static PaymentResult toResult(Payment payment) {
        return PaymentResult.builder()
                .paymentId(payment.getPaymentId())
                .orderId(payment.getOrderId())
                .payAmt(payment.getPayAmt())
                .paymentStatus(payment.getPaymentStatus())
                .build();
    }

}
