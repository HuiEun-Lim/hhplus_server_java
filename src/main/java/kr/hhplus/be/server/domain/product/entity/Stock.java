package kr.hhplus.be.server.domain.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kr.hhplus.be.server.domain.BaseEntity;
import kr.hhplus.be.server.support.exception.CommonException;
import kr.hhplus.be.server.support.exception.product.ProductErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    private Long productId;
    private Long quantity;

    public void decreaseQuantity(Long quantity){
        if(this.quantity < quantity){
            throw new CommonException(ProductErrorCode.STOCK_NOT_ENOUGH);
        }
        this.quantity -= quantity;
    }
}
