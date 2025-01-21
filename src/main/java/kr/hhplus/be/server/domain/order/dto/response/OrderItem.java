package kr.hhplus.be.server.domain.order.dto.response;

import kr.hhplus.be.server.domain.order.entity.OrderProduct;
import kr.hhplus.be.server.domain.product.dto.ProductResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    public OrderProduct toEntity(){
        return OrderProduct.builder()
                .orderId(this.orderId)
                .productId(this.productId)
                .productName(this.productName)
                .productPrice(this.productPrice)
                .quantity(this.quantity)
                .totalPrice(this.totalPrice)
                .build();
    }

    public static OrderItem create(Long orderId, Long productId, String productName, Long productPrice, Long quantity, Long totalPrice) {
        return OrderItem.builder()
                .orderId(orderId)
                .productId(productId)
                .productName(productName)
                .productPrice(productPrice)
                .quantity(quantity)
                .totalPrice(totalPrice)
                .build();
    }
}
