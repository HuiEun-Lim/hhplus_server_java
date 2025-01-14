package kr.hhplus.be.server.interfaces.web.order.model;

import kr.hhplus.be.server.application.order.dto.response.OrderFacadeResponse;
import kr.hhplus.be.server.domain.order.enums.OrderStateType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class OrderInfo {
    private Long orderId;
    private Long userId;
    private Long issuanceId;
    private Long originPrice;
    private Long discountPrice;
    private Long salePrice;
    private OrderStateType orderState;
    private List<OrderItemInfo> items;

    public static OrderInfo toInfo(OrderFacadeResponse order) {
        return OrderInfo.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .issuanceId(order.getIssuanceId())
                .originPrice(order.getOriginPrice())
                .discountPrice(order.getDiscountPrice())
                .salePrice(order.getSalePrice())
                .orderState(order.getOrderState())
                .items(order.getItems().stream().map(OrderItemInfo::toInfo).collect(Collectors.toList()))
                .build();
    }
}
