package kr.hhplus.be.server.interfaces.web.coupon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.application.coupon.dto.CouponIssuanceFacadeResponse;
import kr.hhplus.be.server.application.coupon.facade.CouponFacade;
import kr.hhplus.be.server.interfaces.web.common.dto.ApiResponse;
import kr.hhplus.be.server.interfaces.web.coupon.dto.request.CouponRequest;
import kr.hhplus.be.server.interfaces.web.coupon.dto.response.CouponIssueResponse;
import kr.hhplus.be.server.interfaces.web.coupon.dto.response.UserCouponResponse;
import kr.hhplus.be.server.interfaces.web.coupon.model.IssuedCouponInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "쿠폰 관리", description = "쿠폰 관리 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CouponFacade couponFacade;

    @Operation(summary = "선착순 쿠폰 발급", description = "선착순으로 사용자에게 쿠폰을 발급한다.")
    @PostMapping
    public ApiResponse<CouponIssueResponse> couponIssue(@RequestBody CouponRequest request) {
        CouponIssuanceFacadeResponse result = couponFacade.issueCoupon(request.getUserId(), request.getCouponId());
        return ApiResponse.ok(new CouponIssueResponse(IssuedCouponInfo.toInfo(result)));
    }

    @Operation(summary = "사용자 쿠폰 발급 목록 조회", description = "사용자가 발급 받은 쿠폰 목록을 조회한다.")
    @GetMapping
    public ApiResponse<UserCouponResponse> getUserCoupons(@RequestParam Long userId) {

        List<CouponIssuanceFacadeResponse> resultList = couponFacade.userIssuedCoupons(userId);

        List<IssuedCouponInfo> infoList = resultList.stream()
                .map(IssuedCouponInfo::toInfo)
                .toList();

        return ApiResponse.ok(new UserCouponResponse(infoList));
    }

}
