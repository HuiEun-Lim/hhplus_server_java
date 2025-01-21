package kr.hhplus.be.server.interfaces.web.order.model;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.domain.order.dto.response.OrderItem;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItemInfo {
    @Schema(description = "주문 상품 ID")
    private Long orderProductId;

    @Schema(description = "주문 ID")
    private Long orderId;

    @Schema(description = "상품 ID")
    private Long productId;

    @Schema(description = "상품명")
    private String productName;

    @Schema(description = "상품 가격")
    private Long productPrice;

    @Schema(description = "수량")
    private Long quantity;

    @Schema(description = "총 금액")
    private Long totalPrice;

    public static OrderItemInfo toInfo(OrderItem item) {
        return OrderItemInfo.builder()
                .orderProductId(item.getOrderProductId())
                .orderId(item.getOrderId())
                .productId(item.getProductId())
                .productName(item.getProductName())
                .productPrice(item.getProductPrice())
                .quantity(item.getQuantity())
                .totalPrice(item.getTotalPrice())
                .build();
    }
}
