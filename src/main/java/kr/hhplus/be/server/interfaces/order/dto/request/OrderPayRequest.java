package kr.hhplus.be.server.interfaces.order.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@NoArgsConstructor
public class OrderPayRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long orderId;
}
