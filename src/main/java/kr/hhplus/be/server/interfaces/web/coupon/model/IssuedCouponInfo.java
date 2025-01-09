package kr.hhplus.be.server.interfaces.web.coupon.model;

import kr.hhplus.be.server.application.coupon.dto.CouponIssuanceFacadeResponse;
import kr.hhplus.be.server.domain.coupon.enums.CouponStateType;
import kr.hhplus.be.server.domain.coupon.enums.DiscountType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class IssuedCouponInfo {
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

    public static IssuedCouponInfo toInfo(CouponIssuanceFacadeResponse facadeResponse) {
        return IssuedCouponInfo.builder()
                .issuanceId(facadeResponse.getIssuanceId())
                .userId(facadeResponse.getUserId())
                .userName(facadeResponse.getUserName())
                .couponId(facadeResponse.getCouponId())
                .couponName(facadeResponse.getCouponName())
                .discountType(facadeResponse.getDiscountType())
                .discountAmount(facadeResponse.getDiscountAmount())
                .maxDiscountAmount(facadeResponse.getMaxDiscountAmount())
                .useDate(facadeResponse.getUseDate())
                .expiryDate(facadeResponse.getExpiryDate())
                .build();
    }
}
