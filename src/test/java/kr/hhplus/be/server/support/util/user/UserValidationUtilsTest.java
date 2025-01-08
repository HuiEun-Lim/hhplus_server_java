package kr.hhplus.be.server.support.util.user;

import kr.hhplus.be.server.support.exception.point.PointErrorCode;
import kr.hhplus.be.server.support.exception.point.PointException;
import kr.hhplus.be.server.support.exception.user.UserErrorCode;
import kr.hhplus.be.server.support.exception.user.UserException;
import kr.hhplus.be.server.support.util.point.PointValidationUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class UserValidationUtilsTest {

    @Test
    @DisplayName("정상적인 사용자 ID이다.")
    void validateUserId() {
        // given
        Long userId = 1L;

        // when & then
        assertDoesNotThrow(() -> UserValidationUtils.validateUserId(userId));
    }

    @Test
    @DisplayName("사용자 ID가 0이하이다.")
    void validateInvalidUserId() {
        // Given
        Long userId = 0L;

        // when & then
        assertThatThrownBy(() -> UserValidationUtils.validateUserId(userId))
                .isInstanceOf(UserException.class)
                .hasMessage(UserErrorCode.USER_ID_LESS_THAN_ZERO.getMessage());
    }

}