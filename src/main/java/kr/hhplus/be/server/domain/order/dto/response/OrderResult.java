package kr.hhplus.be.server.domain.order.dto.response;

import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.enums.OrderStateType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
public class OrderResult {
    private Long orderId;
    private Long userId;
    private Long issuanceId;
    private Long originPrice;
    private Long discountPrice;
    private Long salePrice;
    private OrderStateType orderState;

    @Setter
    private List<OrderItem> items;

    public static OrderResult toResult(Order order) {
        return OrderResult.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .issuanceId(order.getIssuanceId())
                .originPrice(order.getOriginPrice())
                .discountPrice(order.getDiscountPrice())
                .salePrice(order.getSalePrice())
                .orderState(order.getOrderState())
                .build();
    }

 }
