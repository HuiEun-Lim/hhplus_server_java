package kr.hhplus.be.server.interfaces.web.coupon.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.interfaces.web.coupon.model.IssuedCouponInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponIssueResponse {
    @Schema(description = "발급 쿠폰 정보")
    private IssuedCouponInfo issuedCoupon;
}
