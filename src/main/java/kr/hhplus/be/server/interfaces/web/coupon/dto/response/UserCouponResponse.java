package kr.hhplus.be.server.interfaces.web.coupon.dto.response;

import kr.hhplus.be.server.interfaces.web.coupon.model.IssuedCouponInfo;
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
    private List<IssuedCouponInfo> issuedCouponList;
}
