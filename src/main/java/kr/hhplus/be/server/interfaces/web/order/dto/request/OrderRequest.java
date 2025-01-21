package kr.hhplus.be.server.interfaces.web.order.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.application.order.dto.request.OrderFacadeRequest;
import kr.hhplus.be.server.application.order.dto.request.OrderProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @Schema(description = "사용자 ID")
    private Long userId;

    @Schema(description = "주문 상품 목록")
    private List<OrderProductDto> orderItems;

    @Schema(description = "발급 쿠폰 ID")
    private Long issuedCouponId;

    public OrderFacadeRequest toOrderFacadeRequest(){
        return OrderFacadeRequest.builder()
                .userId(userId)
                .issuedCouponId(issuedCouponId)
                .productList(orderItems)
                .build();
    }
}
