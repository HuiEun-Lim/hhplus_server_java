package kr.hhplus.be.server.domain.order.dto.request;

import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.enums.OrderStateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderServiceRequest {
    private Long userId;
    private Long issuanceId;
    private Long originPrice;
    private Long discountPrice;
    private Long salePrice;
    private OrderStateType orderState;

    public Order toEntity() {
        return Order.builder()
                .userId(this.userId)
                .issuanceId(this.issuanceId)
                .originPrice(this.originPrice)
                .discountPrice(this.discountPrice)
                .salePrice(this.salePrice)
                .orderState(this.orderState)
                .build();
    }
}
