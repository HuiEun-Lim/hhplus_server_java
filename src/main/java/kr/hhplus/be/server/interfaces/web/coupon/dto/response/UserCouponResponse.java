package kr.hhplus.be.server.interfaces.web.coupon.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "쿠폰 발급 목록")
    private List<IssuedCouponInfo> issuedCouponList;
}
