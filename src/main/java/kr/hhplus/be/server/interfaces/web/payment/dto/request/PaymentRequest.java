package kr.hhplus.be.server.interfaces.web.payment.dto.request;

import kr.hhplus.be.server.application.payment.dto.request.PaymnetFacadeRequset;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long orderId;

    public PaymnetFacadeRequset toFacadeRequset(){
        return PaymnetFacadeRequset.builder()
                .userId(userId)
                .orderId(orderId)
                .build();
    }
}
