package kr.hhplus.be.server.interfaces.coupon.dto.response;

import kr.hhplus.be.server.domain.coupon.CouponIssuance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponResponse {
    private String message;
    private List<CouponIssuance> data;
}
