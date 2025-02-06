package kr.hhplus.be.server.interfaces.web.coupon.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.application.coupon.dto.CouponCacheFacadeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponCacheResponse {
    @Schema(description = "사용자 아이디")
    private Long userId;

    @Schema(description = "사용자 이름")
    private String userName;

    @Schema(description = "쿠폰 아이디")
    private Long couponId;

    @Schema(description = "캐싱여부")
    private Boolean isCached;

    public static CouponCacheResponse toResponse(CouponCacheFacadeResponse result) {
        return CouponCacheResponse.builder()
                .userId(result.getUserId())
                .userName(result.getUserName())
                .couponId(result.getCouponId())
                .isCached(result.getIsCached())
                .build();
    }
}
