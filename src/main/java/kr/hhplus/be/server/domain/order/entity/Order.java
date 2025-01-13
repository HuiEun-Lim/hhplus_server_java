package kr.hhplus.be.server.domain.order.entity;


import jakarta.persistence.*;
import kr.hhplus.be.server.domain.BaseEntity;
import kr.hhplus.be.server.domain.order.enums.OrderStateType;
import kr.hhplus.be.server.support.exception.order.OrderErrorCode;
import kr.hhplus.be.server.support.exception.order.OrderException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`order`")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long userId;
    private Long issuanceId;
    private Long originPrice;
    private Long discountPrice;
    private Long salePrice;
    @Enumerated(EnumType.STRING)
    private OrderStateType orderState;

    public Order(Long userId, Long issuanceId, Long originPrice, Long discountPrice, Long salePrice, OrderStateType orderState) {
        this.userId = userId;
        this.issuanceId = issuanceId;
        this.discountPrice = discountPrice;
        this.originPrice = originPrice;
        this.salePrice = salePrice;
        this.orderState = orderState;
    }

    public void checkOrderState(OrderStateType orderState){
        if(!orderState.equals(this.orderState)){
            throw new OrderException(OrderErrorCode.FAIL_UPDATE_STATUS);
        }
    }

}
