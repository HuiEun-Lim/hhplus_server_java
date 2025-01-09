package kr.hhplus.be.server.domain.coupon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kr.hhplus.be.server.domain.BaseEntity;
import kr.hhplus.be.server.domain.coupon.enums.CouponStateType;
import kr.hhplus.be.server.support.exception.coupon.CouponErrorCode;
import kr.hhplus.be.server.support.exception.coupon.CouponException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponIssuance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issuanceId;

    private Long userId;
    private Long couponId;
    private CouponStateType couponState;
    private LocalDateTime useDate;

    public CouponIssuance(long userId, long couponId, CouponStateType couponStateType) {
        this.userId = userId;
        this.couponId = couponId;
        this.couponState = couponStateType;
    }

    public void checkCouponState(){
        if(this.couponState.equals(CouponStateType.USE)) {
            throw new CouponException(CouponErrorCode.ALREADY_USED_COUPON);
        }
    }
}
