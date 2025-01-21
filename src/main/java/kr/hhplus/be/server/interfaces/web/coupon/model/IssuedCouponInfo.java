package kr.hhplus.be.server.interfaces.web.coupon.model;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.application.coupon.dto.CouponIssuanceFacadeResponse;
import kr.hhplus.be.server.domain.coupon.enums.CouponStateType;
import kr.hhplus.be.server.domain.coupon.enums.DiscountType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class IssuedCouponInfo {
    @Schema(description = "발급 ID")
    private Long issuanceId;

    @Schema(description = "사용자 ID")
    private Long userId;

    @Schema(description = "사용자 이름")
    private String userName;

    @Schema(description = "쿠폰 ID")
    private Long couponId;

    @Schema(description = "쿠폰명")
    private String couponName;

    @Schema(description = "할인 유형", example = "[\"RATE\", \"AMOUNT\"]")
    private DiscountType discountType;

    @Schema(description = "할인 금액/할인율")
    private Long discountAmount;

    @Schema(description = "최대 할인 금액")
    private Long maxDiscountAmount;

    @Schema(description = "쿠폰 상태", example = "[\"UNUSED\", \"USE\"]")
    private CouponStateType couponState;

    @Schema(description = "사용 일자")
    private LocalDateTime useDate;

    @Schema(description = "쿠폰 유효일자")
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
