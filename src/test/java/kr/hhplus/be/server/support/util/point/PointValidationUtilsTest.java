package kr.hhplus.be.server.support.util.point;

import kr.hhplus.be.server.support.exception.point.PointErrorCode;
import kr.hhplus.be.server.support.exception.point.PointException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class PointValidationUtilsTest {

    @Test
    @DisplayName("정상적인 포인트 금액이다.")
    void validatePointAmount() {
        // given
        Long validAmount = 100L;

        // when & then
        assertDoesNotThrow(() -> PointValidationUtils.validatePointAmount(validAmount));
    }

    @Test
    @DisplayName("충전 할 포인트가 0이면 충전을 할 수 없다.")
    void validatePointAmountZero() {
        // given
        Long invalidAmount = 0L;

        // when & then
        assertThatThrownBy(() -> PointValidationUtils.validatePointAmount(invalidAmount))
                .isInstanceOf(PointException.class)
                .hasMessage(PointErrorCode.CHARGE_POINT_ZERO.getMessage());
    }

    @Test
    @DisplayName("충전 할 포인트가 음수면 충전을 할 수 없다.")
    void validatePointAmountNegative() {
        // given
        Long invalidAmount = -1L;

        // when & then
        assertThatThrownBy(() -> PointValidationUtils.validatePointAmount(invalidAmount))
                .isInstanceOf(PointException.class)
                .hasMessage(PointErrorCode.CHARGE_POINT_MINUS.getMessage());
    }

    @Test
    @DisplayName("포인트는 10원 미만으로 충전할 수 없다.")
    void validatePointAmount_LessThanTen() {
        // Given
        Long invalidAmount = 9L;

        // when & then
        assertThatThrownBy(() -> PointValidationUtils.validatePointAmount(invalidAmount))
                .isInstanceOf(PointException.class)
                .hasMessage(PointErrorCode.CHARGE_POINT_UNDER_TEN.getMessage());
    }

    @Test
    @DisplayName("포인트는 10원 단위가 아니면 충전할 수 없다.")
    void validatePointAmountNotUnitOfTen() {
        // Given
        Long invalidAmount = 25L;

        // when & then
        assertThatThrownBy(() -> PointValidationUtils.validatePointAmount(invalidAmount))
                .isInstanceOf(PointException.class)
                .hasMessage(PointErrorCode.CHARGE_POINT_UNIT_TEN.getMessage());
    }

}