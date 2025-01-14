package kr.hhplus.be.server.interfaces.web.payment.model;

import kr.hhplus.be.server.application.payment.dto.response.PaymentFacadeResponse;
import kr.hhplus.be.server.domain.payment.enums.PaymentStatusType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentInfo {
    private Long paymentId;
    private Long orderId;
    private Long payAmt;
    private PaymentStatusType paymentStatus;

    public static PaymentInfo toInfo(PaymentFacadeResponse result) {
        return PaymentInfo.builder()
                .paymentId(result.getPaymentId())
                .orderId(result.getOrderId())
                .payAmt(result.getPayAmt())
                .paymentStatus(result.getPaymentStatus())
                .build();
    }
}
