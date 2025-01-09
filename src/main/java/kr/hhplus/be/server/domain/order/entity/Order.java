package kr.hhplus.be.server.domain.order.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kr.hhplus.be.server.domain.BaseEntity;
import kr.hhplus.be.server.domain.order.enums.OrderStateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long userId;
    private Long issuanceId;
    private Long originPrice;
    private Long discountPrice;
    private Long salePrice;
    private OrderStateType orderState;

    public Order(Long userId, Long issuanceId, Long originPrice, Long discountPrice, Long salePrice, OrderStateType orderState) {
        this.userId = userId;
        this.issuanceId = issuanceId;
        this.discountPrice = discountPrice;
        this.originPrice = originPrice;
        this.salePrice = salePrice;
        this.orderState = orderState;
    }

}
