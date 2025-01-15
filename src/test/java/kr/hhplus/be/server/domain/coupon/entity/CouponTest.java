package kr.hhplus.be.server.domain.coupon.entity;

import kr.hhplus.be.server.domain.coupon.enums.DiscountType;
import kr.hhplus.be.server.support.exception.CommonException;
import kr.hhplus.be.server.support.exception.coupon.CouponErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CouponTest {

    @Test
    @DisplayName("쿠폰의 유효기간이 지금보다 빠르다.")
    void checkExpiryDateExpriedCoupon() {
        // given
        Coupon coupon = new Coupon(1L, "유효기간 지난 쿠폰", DiscountType.RATE, 10L, 10000L, 50L, LocalDateTime.now().minusDays(2));

        // when & then
        assertThatThrownBy(coupon::checkExpiryDate)
                .isInstanceOf(CommonException.class)
                .hasMessage(CouponErrorCode.EXPIRY_COUPON.getMessage());
    }

    @Test
    @DisplayName("쿠폰의 유효기간이 지금보다 느리다.")
    void checkExpiryDateUsableCoupon() {
        // given
        Coupon coupon = new Coupon(1L, "유효기간이 지나지 않은 쿠폰", DiscountType.RATE, 10L, 10000L, 50L, LocalDateTime.now().plusDays(2));

        // when & then
        assertDoesNotThrow(coupon::checkExpiryDate);

    }

    @Test
    @DisplayName("쿠폰의 최대 발급수보다 발급 수가 크거나 같다.")
    void checkIssuanceCountMoreThanMax() {
        // given
        Coupon coupon = new Coupon(1L, "발급 초과 쿠폰", DiscountType.RATE, 10L, 10000L, 50L, LocalDateTime.now().plusDays(2));

        // when & then
        assertThatThrownBy(() -> coupon.checkIssuedCount(50L))
                .isInstanceOf(CommonException.class)
                .hasMessage(CouponErrorCode.MAX_ISSUED_COUPON.getMessage());
    }

    @Test
    @DisplayName("쿠폰의 최대 발급수보다 발급 수가 작다.")
    void checkIssuanceCount() {
        // given
        Coupon coupon = new Coupon(1L, "발급 가능 쿠폰", DiscountType.RATE, 10L, 10000L, 50L, LocalDateTime.now().plusDays(2));

        // when & then
        assertDoesNotThrow(() -> coupon.checkIssuedCount(49L));
    }

}