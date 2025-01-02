package kr.hhplus.be.server.interfaces.coupon.dto.response;

import kr.hhplus.be.server.domain.coupon.CouponIssuance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponIssueResponse {
    private String message;
    private CouponIssuance data;
}
