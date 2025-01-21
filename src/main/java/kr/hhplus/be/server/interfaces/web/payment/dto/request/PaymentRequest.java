package kr.hhplus.be.server.interfaces.web.payment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.application.payment.dto.request.PaymnetFacadeRequset;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    @Schema(description = "사용자 ID")
    private Long userId;

    @Schema(description = "주문 ID")
    private Long orderId;

    public PaymnetFacadeRequset toFacadeRequset(){
        return PaymnetFacadeRequset.builder()
                .userId(userId)
                .orderId(orderId)
                .build();
    }
}
