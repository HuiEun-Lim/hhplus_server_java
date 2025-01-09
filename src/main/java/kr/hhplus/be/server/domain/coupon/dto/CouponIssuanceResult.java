package kr.hhplus.be.server.domain.coupon.dto;

import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.entity.CouponIssuance;
import kr.hhplus.be.server.domain.coupon.enums.CouponStateType;
import kr.hhplus.be.server.domain.coupon.enums.DiscountType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CouponIssuanceResult {
    private Long issuanceId;
    private Long userId;
    private Long couponId;
    private String couponName;
    private DiscountType discountType;
    private Long discountAmount;
    private Long maxDiscountAmount;
    private CouponStateType couponState;
    private LocalDateTime useDate;
    private LocalDateTime expiryDate;

    public static CouponIssuanceResult toResult(CouponIssuance issuance, Coupon coupon) {
        return CouponIssuanceResult.builder()
                .issuanceId(issuance.getIssuanceId())
                .userId(issuance.getUserId())
                .couponId(issuance.getCouponId())
                .couponName(coupon.getCouponName())
                .discountType(coupon.getDiscountType())
                .maxDiscountAmount(coupon.getMaxDiscountAmount())
                .couponState(issuance.getCouponState())
                .useDate(issuance.getUseDate())
                .expiryDate(coupon.getExpiryDate())
                .build();
    }

    public static CouponIssuanceResult create(Long userId, Long couponId, String couponName, DiscountType discountType, Long discountAmount, Long maxDiscountAmount, CouponStateType state, LocalDateTime useDate, LocalDateTime expiryDate) {
        return CouponIssuanceResult.builder()
                .userId(userId)
                .couponId(couponId)
                .couponName(couponName)
                .discountType(discountType)
                .discountAmount(discountAmount)
                .maxDiscountAmount(maxDiscountAmount)
                .couponState(state)
                .useDate(useDate)
                .expiryDate(expiryDate)
                .build();
    }
}
