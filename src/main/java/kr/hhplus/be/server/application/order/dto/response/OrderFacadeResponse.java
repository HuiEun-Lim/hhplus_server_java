package kr.hhplus.be.server.application.order.dto.response;

import kr.hhplus.be.server.domain.order.dto.response.OrderItem;
import kr.hhplus.be.server.domain.order.dto.response.OrderResult;
import kr.hhplus.be.server.domain.order.enums.OrderStateType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderFacadeResponse {
    private Long orderId;
    private Long userId;
    private Long issuanceId;
    private Long originPrice;
    private Long discountPrice;
    private Long salePrice;
    private OrderStateType orderState;
    private List<OrderItem> items;

    public static OrderFacadeResponse toResponse(OrderResult order) {
        return OrderFacadeResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .issuanceId(order.getIssuanceId())
                .originPrice(order.getOriginPrice())
                .discountPrice(order.getDiscountPrice())
                .salePrice(order.getSalePrice())
                .orderState(order.getOrderState())
                .items(order.getItems())
                .build();
    }
}
