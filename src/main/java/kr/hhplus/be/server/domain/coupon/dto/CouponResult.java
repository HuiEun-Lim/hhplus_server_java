package kr.hhplus.be.server.domain.coupon.dto;

import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.enums.DiscountType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CouponResult {
    private Long couponId;
    private String couponName;
    private DiscountType discountType;
    private Long discountAmount;
    private Long maxDiscountAmount;
    private Long maxIssuanceCount;
    private LocalDateTime expiryDate;

    public static CouponResult toResult(Coupon coupon) {
        return CouponResult.builder()
                .couponId(coupon.getCouponId())
                .couponName(coupon.getCouponName())
                .discountType(coupon.getDiscountType())
                .discountAmount(coupon.getDiscountAmount())
                .maxDiscountAmount(coupon.getMaxDiscountAmount())
                .maxIssuanceCount(coupon.getMaxIssuanceCount())
                .expiryDate(coupon.getExpiryDate())
                .build();
    }

    public static CouponResult create(String couponName, DiscountType discountType, Long discountAmount, Long maxDiscountAmount, LocalDateTime expiryDate) {
        return CouponResult.builder()
                .couponName(couponName)
                .discountType(discountType)
                .discountAmount(discountAmount)
                .maxDiscountAmount(maxDiscountAmount)
                .expiryDate(expiryDate)
                .build();
    }
}
