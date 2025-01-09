package kr.hhplus.be.server.interfaces.web.order.dto.response;

import kr.hhplus.be.server.domain.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private String message;
    private Order data;
}
