package kr.hhplus.be.server.domain.coupon.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.BaseEntity;
import kr.hhplus.be.server.domain.coupon.enums.CouponStateType;
import kr.hhplus.be.server.support.exception.CommonException;
import kr.hhplus.be.server.support.exception.coupon.CouponErrorCode;
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
@Table(name="CouponIssuance", uniqueConstraints = {
    @UniqueConstraint(
            name="user_coupon_uk",
            columnNames={"userId","couponId"}
    )})
public class CouponIssuance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issuanceId;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "couponId")
    private Long couponId;

    @Enumerated(EnumType.STRING)
    @Column(name = "couponState")
    private CouponStateType couponState;

    @Column(name = "useDate")
    private LocalDateTime useDate;

    public CouponIssuance(long userId, long couponId, CouponStateType couponStateType) {
        this.userId = userId;
        this.couponId = couponId;
        this.couponState = couponStateType;
    }

    public void checkCouponState(){
        if(this.couponState.equals(CouponStateType.USE)) {
            throw new CommonException(CouponErrorCode.ALREADY_USED_COUPON);
        }
    }
}
