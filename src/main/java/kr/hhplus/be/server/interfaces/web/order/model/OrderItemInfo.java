package kr.hhplus.be.server.interfaces.web.order.model;

import kr.hhplus.be.server.domain.order.dto.response.OrderItem;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItemInfo {
    private Long orderProductId;
    private Long orderId;
    private Long productId;
    private String productName;
    private Long productPrice;
    private Long quantity;
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
