package kr.hhplus.be.server.application.coupon.dto;

import kr.hhplus.be.server.domain.user.dto.UserResult;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CouponCacheFacadeResponse {
    private Long userId;
    private String userName;
    private Long couponId;
    private Boolean isCached;

    public static CouponCacheFacadeResponse toFacadeResponse(Long couponId, boolean cacheResult, UserResult user) {
        return CouponCacheFacadeResponse.builder()
                .couponId(couponId)
                .userId(user.getUserId())
                .userName(user.getName())
                .isCached(cacheResult)
                .build();
    }
}
