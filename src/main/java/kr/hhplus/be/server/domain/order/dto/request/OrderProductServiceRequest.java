package kr.hhplus.be.server.domain.order.dto.request;

import kr.hhplus.be.server.domain.order.entity.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductServiceRequest {
    private Long orderId;
    private Long productId;
    private String productName;
    private Long productPrice;
    private Long quantity;
    private Long totalPrice;

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
}
