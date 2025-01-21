package kr.hhplus.be.server.interfaces.web.order.dto.response;

import kr.hhplus.be.server.interfaces.web.order.model.OrderInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private OrderInfo orderInfo;
}
