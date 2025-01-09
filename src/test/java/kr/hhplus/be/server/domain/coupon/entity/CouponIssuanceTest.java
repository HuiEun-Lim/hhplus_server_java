package kr.hhplus.be.server.domain.coupon.entity;

import kr.hhplus.be.server.domain.coupon.enums.CouponStateType;
import kr.hhplus.be.server.support.exception.coupon.CouponErrorCode;
import kr.hhplus.be.server.support.exception.coupon.CouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CouponIssuanceTest {

    @Test
    @DisplayName("사용된 쿠폰의 상태를 확인한다.")
    void checkUsedCouponStatus() {
        // given
        CouponIssuance issuance = new CouponIssuance(1L, 1L, CouponStateType.USE);

        // when then
        assertThatThrownBy(issuance::checkCouponState)
                .isInstanceOf(CouponException.class)
                .hasMessage(CouponErrorCode.ALREADY_USED_COUPON.getMessage());
    }

    @Test
    @DisplayName("사용되지 않은 쿠폰의 상태를 확인한다.")
    void checkUnusedCouponStatus() {
        // given
        CouponIssuance issuance = new CouponIssuance(1L, 1L, CouponStateType.UNUSED);

        // when then
        assertDoesNotThrow(issuance::checkCouponState);
    }
}