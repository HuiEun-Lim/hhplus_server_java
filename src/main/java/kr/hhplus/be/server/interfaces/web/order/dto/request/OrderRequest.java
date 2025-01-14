package kr.hhplus.be.server.interfaces.web.order.dto.request;

import kr.hhplus.be.server.application.order.dto.request.OrderFacadeRequest;
import kr.hhplus.be.server.application.order.dto.request.OrderProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotNull
    private Long userId;

    @NotNull
    private List<OrderProductDto> orderItems;

    private Long couponId;

    public OrderFacadeRequest toOrderFacadeRequest(){
        return OrderFacadeRequest.builder()
                .userId(userId)
                .issuedCouponId(couponId)
                .productList(orderItems)
                .build();
    }
}
