package kr.hhplus.be.server.domain.order.dto.response;

import kr.hhplus.be.server.domain.order.entity.OrderProduct;
import kr.hhplus.be.server.domain.product.dto.ProductResult;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItem {
    private Long orderProductId;
    private Long orderId;
    private Long productId;
    private String productName;
    private Long productPrice;
    private Long quantity;
    private Long totalPrice;

    public static OrderItem toItem(OrderProduct orderProduct) {
        return OrderItem.builder()
                .orderProductId(orderProduct.getOrderProductId())
                .orderId(orderProduct.getOrderId())
                .productId(orderProduct.getProductId())
                .productName(orderProduct.getProductName())
                .productPrice(orderProduct.getProductPrice())
                .quantity(orderProduct.getQuantity())
                .totalPrice(orderProduct.getTotalPrice())
                .build();
    }

    public static OrderItem resultToItem(ProductResult product, Long quantity) {
        return OrderItem.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .quantity(quantity)
                .productPrice(product.getPrice())
                .totalPrice(product.getPrice() * quantity)
                .build();
    }
}