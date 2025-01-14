package kr.hhplus.be.server.interfaces.web.coupon.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@NoArgsConstructor
public class CouponRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long couponId;

    public CouponRequest(Long userId, Long couponId) {
        this.userId = userId;
        this.couponId = couponId;
    }
}
