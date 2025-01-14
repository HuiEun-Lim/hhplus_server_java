package kr.hhplus.be.server.application.order.dto.response;


import kr.hhplus.be.server.domain.order.dto.response.OrderItem;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItemDto {
    private Long orderProductId;
    private Long orderId;
    private Long productId;
    private String productName;
    private Long productPrice;
    private Long quantity;
    private Long totalPrice;

    public static OrderItemDto toDto(OrderItem orderProduct) {
        return OrderItemDto.builder()
                .orderProductId(orderProduct.getProductId())
                .orderId(orderProduct.getOrderId())
                .productId(orderProduct.getProductId())
                .productName(orderProduct.getProductName())
                .productPrice(orderProduct.getProductPrice())
                .quantity(orderProduct.getQuantity())
                .totalPrice(orderProduct.getTotalPrice())
                .build();
    }
}
