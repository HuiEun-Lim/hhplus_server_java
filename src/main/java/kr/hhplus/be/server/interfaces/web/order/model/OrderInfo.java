package kr.hhplus.be.server.interfaces.web.order.model;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.application.order.dto.response.OrderFacadeResponse;
import kr.hhplus.be.server.domain.order.enums.OrderStateType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class OrderInfo {
    @Schema(description = "주문 ID")
    private Long orderId;

    @Schema(description = "사용자 ID")
    private Long userId;

    @Schema(description = "쿠폰 발급 ID")
    private Long issuanceId;

    @Schema(description = "원가")
    private Long originPrice;

    @Schema(description = "할인 금액")
    private Long discountPrice;

    @Schema(description = "판매가")
    private Long salePrice;

    @Schema(description = "주문 상태", example = "[\"ORDERED\", \"PAYED\"]")
    private OrderStateType orderState;

    @Schema(description = "주문 상품 목록")
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
