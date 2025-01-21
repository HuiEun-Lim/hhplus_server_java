package kr.hhplus.be.server.domain.point.entity;

import kr.hhplus.be.server.support.exception.CommonException;
import kr.hhplus.be.server.support.exception.point.PointErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserPointTest {

    @Test
    @DisplayName("사용 포인트만큼 포인트를 차감한다.")
    void useUserPoint() {
        // given
        Long userAmount = 2000L;
        UserPoint userPoint = UserPoint.create(1L, 3000L);

        // when & then
        assertDoesNotThrow(() -> userPoint.decrease(userAmount));
        assertEquals(1000L, userPoint.getAmount());
    }

    @Test
    @DisplayName("사용자의 포인트보다 많은 포인트를 사용한다.")
    void usePointMoreThanUserPoint() {
        // given
        Long userAmount = 2000L;
        UserPoint userPoint = UserPoint.create(1L, 0L);

        // when & then
        assertThatThrownBy(() -> userPoint.decrease(userAmount))
                .isInstanceOf(CommonException.class)
                .hasMessage(PointErrorCode.USE_POINT_LACK.getMessage());
    }

    @Test
    @DisplayName("충전 포인트만큼 포인트를 충전한다.")
    void chargeUserPoint() {
        // given
        Long userAmount = 2000L;
        UserPoint userPoint = UserPoint.create(1L, 3000L);

        // when & then
        assertDoesNotThrow(() -> userPoint.increase(userAmount));
        assertEquals(5000L, userPoint.getAmount());
    }

}