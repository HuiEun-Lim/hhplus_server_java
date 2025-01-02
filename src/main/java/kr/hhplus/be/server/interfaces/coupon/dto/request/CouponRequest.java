package kr.hhplus.be.server.interfaces.coupon.dto.request;

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
}
