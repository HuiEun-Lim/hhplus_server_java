package kr.hhplus.be.server.domain.coupon.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.BaseEntity;
import kr.hhplus.be.server.domain.coupon.enums.DiscountType;
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
public class Coupon extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    private String couponName;
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
    private Long discountAmount;
    private Long maxDiscountAmount;
    private Long maxIssuanceCount;
    private LocalDateTime expiryDate;

    public static Coupon create(String couponName, DiscountType type, Long discountAmount, Long maxDiscountAmount, Long maxIssuanceCount, LocalDateTime expiryDate) {
        return Coupon.builder()
                .couponName(couponName)
                .discountType(type)
                .discountAmount(discountAmount)
                .maxDiscountAmount(maxDiscountAmount)
                .maxIssuanceCount(maxIssuanceCount)
                .expiryDate(expiryDate)
                .build();
    }

    public void checkExpiryDate() {
        if(this.expiryDate.isBefore(LocalDateTime.now())) {
            throw new CommonException(CouponErrorCode.EXPIRY_COUPON);
        }
    }

    public void checkIssuedCount(Long count) {
        if(this.maxIssuanceCount <= count) {
            throw new CommonException(CouponErrorCode.MAX_ISSUED_COUPON);
        }
    }
}
