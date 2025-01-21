package kr.hhplus.be.server.interfaces.web.payment.model;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.application.payment.dto.response.PaymentFacadeResponse;
import kr.hhplus.be.server.domain.payment.enums.PaymentStatusType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentInfo {
    @Schema(description = "결제 ID")
    private Long paymentId;

    @Schema(description = "주문 ID")
    private Long orderId;

    @Schema(description = "결제 금액")
    private Long payAmt;

    @Schema(description = "결제 상태", example = "[\"PAYED\", \"CANCEL_REQUEST\", \"CANCEL\"]")
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
