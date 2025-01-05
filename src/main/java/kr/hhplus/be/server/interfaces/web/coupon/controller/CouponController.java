package kr.hhplus.be.server.interfaces.web.coupon.controller;

import kr.hhplus.be.server.domain.coupon.CouponIssuance;
import kr.hhplus.be.server.domain.point.CouponStateType;
import kr.hhplus.be.server.interfaces.web.ApiResponse;
import kr.hhplus.be.server.interfaces.web.coupon.dto.request.CouponRequest;
import kr.hhplus.be.server.interfaces.web.coupon.dto.response.CouponIssueResponse;
import kr.hhplus.be.server.interfaces.web.coupon.dto.response.UserCouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/coupons")
public class CouponController {

    @PostMapping
    public ApiResponse<CouponIssueResponse> couponIssue(@RequestBody CouponRequest request) {
        return ApiResponse.ok(new CouponIssueResponse("쿠폰 발급 성공", new CouponIssuance(1L, 1001L, CouponStateType.UNUSED)));
    }

    @GetMapping
    public ApiResponse<UserCouponResponse> getUserCoupons(@RequestParam Long userId) {
        CouponIssuance coupon1 = new CouponIssuance(1L, 1001L, CouponStateType.UNUSED);
        CouponIssuance coupon2 = new CouponIssuance(1L, 1002L, CouponStateType.UNUSED);
        return ApiResponse.ok(new UserCouponResponse("조회 성공", List.of(coupon1, coupon2)));
    }

}
