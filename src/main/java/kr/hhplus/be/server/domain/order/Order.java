package kr.hhplus.be.server.domain.order;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kr.hhplus.be.server.domain.BaseEntity;
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
    private Long couponId;
    private Long originPrice;
    private Long discountPrice;
    private Long salePrice;
    private OrderStateType orderState;

    public Order(Long orderId, Long userId, Long originPrice, Long salePrice) {
        this.orderId = orderId;
        this.userId = userId;
        this.originPrice = originPrice;
        this.salePrice = salePrice;
    }

}
