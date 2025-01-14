package kr.hhplus.be.server.application.coupon.dto;

import kr.hhplus.be.server.domain.coupon.dto.CouponIssuanceResult;
import kr.hhplus.be.server.domain.coupon.enums.CouponStateType;
import kr.hhplus.be.server.domain.coupon.enums.DiscountType;
import kr.hhplus.be.server.domain.user.dto.UserResult;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CouponIssuanceFacadeResponse {

    private Long issuanceId;
    private Long userId;
    private String userName;
    private Long couponId;
    private String couponName;
    private DiscountType discountType;
    private Long discountAmount;
    private Long maxDiscountAmount;
    private CouponStateType couponState;
    private LocalDateTime useDate;
    private LocalDateTime expiryDate;

    public static CouponIssuanceFacadeResponse toFacadeResponse(CouponIssuanceResult coupon, UserResult user) {
        return CouponIssuanceFacadeResponse.builder()
                .issuanceId(coupon.getIssuanceId())
                .userId(coupon.getUserId())
                .userName(user.getName())
                .couponId(coupon.getCouponId())
                .couponName(coupon.getCouponName())
                .discountType(coupon.getDiscountType())
                .discountAmount(coupon.getDiscountAmount())
                .maxDiscountAmount(coupon.getMaxDiscountAmount())
                .couponState(coupon.getCouponState())
                .useDate(coupon.getUseDate())
                .expiryDate(coupon.getExpiryDate())
                .build();
    }

    public static CouponIssuanceFacadeResponse create(Long issuanceId, Long userId, Long couponId, String couponName, CouponStateType couponState, LocalDateTime expiryDate) {
        return CouponIssuanceFacadeResponse.builder()
                .issuanceId(issuanceId)
                .userId(userId)
                .couponId(couponId)
                .couponName(couponName)
                .couponState(couponState)
                .expiryDate(expiryDate)
                .build();
    }
}
