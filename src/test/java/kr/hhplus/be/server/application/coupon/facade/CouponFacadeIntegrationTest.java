package kr.hhplus.be.server.application.coupon.facade;

import kr.hhplus.be.server.application.coupon.dto.CouponIssuanceFacadeResponse;
import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.enums.CouponStateType;
import kr.hhplus.be.server.domain.coupon.enums.DiscountType;
import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;
import kr.hhplus.be.server.domain.user.entity.User;
import kr.hhplus.be.server.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CouponFacadeIntegrationTest {

    @Autowired
    private CouponFacade couponFacade;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    @DisplayName("사용자에게 쿠폰을 성공적으로 발급한다.")
    void issueCoupon() {
        // Given
        // 사용자 및 쿠폰 데이터 준비 (필요 시 Mock 또는 데이터베이스 초기화로 설정)
        User user = userRepository.save(User.create("임희은"));

        Coupon coupon = couponRepository.save(Coupon.create("1000원 할인 쿠폰", DiscountType.AMOUNT, 1000L, 1000L, 50L, LocalDateTime.now().plusDays(10)));

        // When
        CouponIssuanceFacadeResponse response = couponFacade.issueCoupon(user.getUserId(), coupon.getCouponId());

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getUserId()).isEqualTo(user.getUserId());
        assertThat(response.getCouponId()).isEqualTo(coupon.getCouponId());
        assertThat(response.getCouponState()).isEqualTo(CouponStateType.UNUSED);
    }

    @Test
    @DisplayName("사용자가 발급받은 쿠폰 목록을 조회한다.")
    void userIssuedCoupons() {
        // Given
        User user = userRepository.save(User.create("알렉스"));

        Coupon coupon1 = couponRepository.save(Coupon.create("1000원 할인쿠폰", DiscountType.AMOUNT, 1000L, 1000L, 50L, LocalDateTime.now().plusDays(10)));
        Coupon coupon2 = couponRepository.save(Coupon.create("10% 할인쿠폰", DiscountType.RATE, 10L, 5000L, 50L, LocalDateTime.now().plusDays(10)));

        couponFacade.issueCoupon(user.getUserId(), coupon1.getCouponId());
        couponFacade.issueCoupon(user.getUserId(), coupon2.getCouponId());

        // When
        List<CouponIssuanceFacadeResponse> responses = couponFacade.userIssuedCoupons(user.getUserId());

        // Then
        assertThat(responses).isNotNull();
        assertThat(responses).hasSize(2);

        CouponIssuanceFacadeResponse response1 = responses.get(0);
        assertThat(response1.getCouponId()).isEqualTo(coupon1.getCouponId());
        assertThat(response1.getCouponState()).isEqualTo(CouponStateType.UNUSED);

        CouponIssuanceFacadeResponse response2 = responses.get(1);
        assertThat(response2.getCouponId()).isEqualTo(coupon2.getCouponId());
        assertThat(response2.getCouponState()).isEqualTo(CouponStateType.UNUSED);
    }


}