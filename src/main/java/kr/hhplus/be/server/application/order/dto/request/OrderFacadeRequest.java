package kr.hhplus.be.server.application.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderFacadeRequest {
    private Long userId;
    private Long orderId;
    private Long issuedCouponId;
    private List<OrderProductDto> productList;

    public static OrderFacadeRequest create(Long userId, Long issuedCouponId, List<OrderProductDto> productList){
        return OrderFacadeRequest.builder()
                .userId(userId)
                .issuedCouponId(issuedCouponId)
                .productList(productList)
                .build();
    }

}
