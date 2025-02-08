package kr.hhplus.be.server.application.coupon.facade;

import kr.hhplus.be.server.application.coupon.dto.CouponCacheFacadeResponse;
import kr.hhplus.be.server.application.coupon.dto.CouponIssuanceFacadeResponse;
import kr.hhplus.be.server.domain.coupon.dto.CouponIssuanceResult;
import kr.hhplus.be.server.domain.coupon.service.CouponService;
import kr.hhplus.be.server.domain.user.dto.UserResult;
import kr.hhplus.be.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("couponFacade")
@RequiredArgsConstructor
public class CouponFacade {

    private final CouponService couponService;
    private final UserService userService;

    @Transactional
    public CouponIssuanceFacadeResponse issueCoupon(Long userId, Long couponId) {
        // 사용자 정보 검증
        UserResult user = userService.getUserByUserId(userId);

        // 쿠폰 발급 처리
        CouponIssuanceResult issuedCoupon = couponService.issueCoupon(userId, couponId);

        return CouponIssuanceFacadeResponse.toFacadeResponse(issuedCoupon, user);
    }

    @Transactional(readOnly = true)
    public List<CouponIssuanceFacadeResponse> userIssuedCoupons(Long userId) {
        // 사용자 정보 검증
        UserResult user = userService.getUserByUserId(userId);

        // 사용자 쿠폰 목록 조회
        List<CouponIssuanceResult> issuanceList = couponService.userIssuedCouponList(userId);

        return issuanceList.stream()
                .map(issuance -> CouponIssuanceFacadeResponse.toFacadeResponse(issuance, user))
                .collect(Collectors.toList());
    }

    @Transactional
    public CouponCacheFacadeResponse couponRequestCache(Long userId, Long couponId) {
        // 사용자 정보 검증
        UserResult user = userService.getUserByUserId(userId);

        // 쿠폰 발급 요청 캐싱
        boolean cacheResult = couponService.requestCouponCache(userId, couponId);

        return CouponCacheFacadeResponse.toFacadeResponse(couponId, cacheResult, user);
    }

}
