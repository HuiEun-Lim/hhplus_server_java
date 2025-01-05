package kr.hhplus.be.server.interfaces.payment.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@NoArgsConstructor
public class PaymentRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long orderId;
}
