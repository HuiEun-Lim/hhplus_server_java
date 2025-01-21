package kr.hhplus.be.server.interfaces.web.coupon.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CouponRequest {

    @Schema(description = "사용자 ID")
    private Long userId;

    @Schema(description = "쿠폰 ID")
    private Long couponId;

    public CouponRequest(Long userId, Long couponId) {
        this.userId = userId;
        this.couponId = couponId;
    }
}
