package kr.hhplus.be.server.application.coupon.facade;

import kr.hhplus.be.server.application.coupon.dto.CouponIssuanceFacadeResponse;
import kr.hhplus.be.server.domain.coupon.dto.CouponIssuanceResult;
import kr.hhplus.be.server.domain.coupon.enums.CouponStateType;
import kr.hhplus.be.server.domain.coupon.enums.DiscountType;
import kr.hhplus.be.server.domain.coupon.service.CouponService;
import kr.hhplus.be.server.domain.user.dto.UserResult;
import kr.hhplus.be.server.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponFacadeTest {

    @InjectMocks
    private CouponFacade couponFacade;

    @Mock
    private CouponService couponService;

    @Mock
    private UserService userService;

    @Test
    @DisplayName("사용자가 쿠폰을 성공적으로 발급받는다.")
    void IssueCoupon() {
        // Given
        Long userId = 1L;
        Long couponId = 100L;

        UserResult mockUser = UserResult.create(userId, "어피치");
        CouponIssuanceResult mockIssuedCoupon = CouponIssuanceResult.create(userId, couponId, "과제 면제 쿠폰", DiscountType.RATE, 100L, 0L, CouponStateType.UNUSED, null, LocalDateTime.now().plusDays(10));

        when(userService.getUserByUserId(userId)).thenReturn(mockUser);
        when(couponService.issueCoupon(userId, couponId)).thenReturn(mockIssuedCoupon);

        // When
        CouponIssuanceFacadeResponse response = couponFacade.issueCoupon(userId, couponId);

        // Then
        assertNotNull(response);
        assertEquals(userId, response.getUserId());
        assertEquals("어피치", response.getUserName());
        assertEquals(couponId, response.getCouponId());

        verify(userService, times(1)).getUserByUserId(userId);
        verify(couponService, times(1)).issueCoupon(userId, couponId);
    }

    @Test
    @DisplayName("사용자가 발급받은 쿠폰 목록을 조회한다.")
    void getUserIssuedCoupons() {
        // Given
        Long userId = 1L;

        UserResult mockUser = UserResult.create(userId, "제이지");

        CouponIssuanceResult issuance1 = CouponIssuanceResult.create(userId, 1L, "과제 면제 쿠폰", DiscountType.RATE, 100L, 0L, CouponStateType.UNUSED, null, LocalDateTime.now().plusDays(10));

        CouponIssuanceResult issuance2 = CouponIssuanceResult.create(userId, 2L, "테스트 면제 쿠폰", DiscountType.AMOUNT, 5000L, 5000L, CouponStateType.USE, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(15));

        when(userService.getUserByUserId(userId)).thenReturn(mockUser);
        when(couponService.userIssuedCouponList(userId)).thenReturn(Arrays.asList(issuance1, issuance2));

        // When
        List<CouponIssuanceFacadeResponse> responses = couponFacade.userIssuedCoupons(userId);

        // Then
        assertNotNull(responses);
        assertEquals(2, responses.size());

        CouponIssuanceFacadeResponse response1 = responses.get(0);
        assertEquals(1L, response1.getCouponId());
        assertEquals("과제 면제 쿠폰", response1.getCouponName());
        assertEquals(DiscountType.RATE, response1.getDiscountType());

        CouponIssuanceFacadeResponse response2 = responses.get(1);
        assertEquals(2L, response2.getCouponId());
        assertEquals("테스트 면제 쿠폰", response2.getCouponName());
        assertEquals(DiscountType.AMOUNT, response2.getDiscountType());

        verify(userService, times(1)).getUserByUserId(userId);
        verify(couponService, times(1)).userIssuedCouponList(userId);
    }
}