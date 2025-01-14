package kr.hhplus.be.server.domain.payment.dto;

import kr.hhplus.be.server.domain.payment.entity.Payment;
import kr.hhplus.be.server.domain.payment.enums.PaymentStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentServiceRequest {
    private Long orderId;
    private Long payAmt;
    private PaymentStatusType paymentStatus;

    public Payment toEntity(){
        return Payment.builder()
                .orderId(this.orderId)
                .payAmt(this.payAmt)
                .paymentStatus(this.paymentStatus)
                .build();
    }
}
